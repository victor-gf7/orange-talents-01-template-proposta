package br.com.zup.proposal.proposta;


import br.com.zup.proposal.cartao.CartaoClient;
import br.com.zup.proposal.proposta.repository.PropostaRepository;
import br.com.zup.proposal.proposta.request.NovaPropostaRequest;
import br.com.zup.proposal.proposta.response.DetalhesPropostaResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class PropostaController {

    @Autowired
    private AnaliseClient analiseClient;

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private CartaoClient cartaoClient;

    @PostMapping("/propostas")
    public ResponseEntity<?> criaProposta(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder builder){

        if(propostaRepository.existsByDocumento(request.getDocumento())){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        Proposta proposta = request.converteParaProposta();

        propostaRepository.save(proposta);

        try{
            AnaliseClient.AnaliseStatusRequest analiseStatusRequest = new AnaliseClient.AnaliseStatusRequest(proposta);
            AnaliseClient.ConsultaStatusResponse response = analiseClient.analise(analiseStatusRequest);

            proposta.atualizaStatus(response.getResultadoSolicitacao());

        } catch (FeignException.UnprocessableEntity ex){
            proposta.atualizaStatus("COM_RESTRICAO");
        }
        propostaRepository.save(proposta);

        URI url = builder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(url).build();
    }

    @GetMapping("/propostas/{id}")
    public ResponseEntity<DetalhesPropostaResponse>  detalharProposta(@PathVariable Long id){
        Optional<Proposta> proposta = propostaRepository.findById(id);

        if(proposta.isPresent()){
            DetalhesPropostaResponse response = new DetalhesPropostaResponse(proposta.get());

            return ResponseEntity.ok(response);
        }

        return  ResponseEntity.notFound().build();
    }
}
