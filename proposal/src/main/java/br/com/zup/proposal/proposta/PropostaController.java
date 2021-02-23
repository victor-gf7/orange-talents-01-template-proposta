package br.com.zup.proposal.proposta;


import br.com.zup.proposal.cartao.CartaoClient;
import br.com.zup.proposal.proposta.repository.PropostaRepository;
import br.com.zup.proposal.proposta.request.NovaPropostaRequest;
import br.com.zup.proposal.proposta.response.DetalhesPropostaResponse;
import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
public class PropostaController {

    private final Tracer tracer;

    @Autowired
    private AnaliseClient analiseClient;

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private CartaoClient cartaoClient;

    public PropostaController(Tracer tracer) {
        this.tracer = tracer;
    }

    @PostMapping("/propostas")
    public ResponseEntity<?> criaProposta(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder builder){

        Span spanAtivo = tracer.activeSpan();

        if(propostaRepository.existsByDocumento(DigestUtils.sha256Hex(request.getDocumento()))){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        Proposta proposta = request.converteParaProposta();

        propostaRepository.save(proposta);

        spanAtivo.setTag("user.email", proposta.getEmail());
        spanAtivo.setBaggageItem("user.email", proposta.getEmail());

        try{
            AnaliseClient.AnaliseStatusRequest analiseStatusRequest = new AnaliseClient.AnaliseStatusRequest(proposta);
            AnaliseClient.ConsultaStatusResponse response = analiseClient.analise(analiseStatusRequest);

            proposta.atualizaStatus(response.getResultadoSolicitacao());

        } catch (FeignException.UnprocessableEntity ex){
            proposta.atualizaStatus("COM_RESTRICAO");
        }

        proposta.setDocumento(DigestUtils.sha256Hex(proposta.getDocumento()));

        propostaRepository.save(proposta);

        URI url = builder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(url).build();
    }

    @GetMapping("/propostas/{id}")
    public ResponseEntity<DetalhesPropostaResponse> detalharProposta(@PathVariable Long id){

        Span spanAtivo = tracer.activeSpan();
        Optional<Proposta> proposta = propostaRepository.findById(id);

        if(proposta.isPresent()){
            spanAtivo.setTag("user.proposal.email", proposta.get().getEmail());
            spanAtivo.setBaggageItem("user.proposal.email", proposta.get().getEmail());

            DetalhesPropostaResponse response = new DetalhesPropostaResponse(proposta.get());

            return ResponseEntity.ok(response);
        }

        return  ResponseEntity.notFound().build();
    }

}


