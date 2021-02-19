package br.com.zup.proposal.viagem;

import br.com.zup.proposal.cartao.Cartao;
import br.com.zup.proposal.cartao.repository.CartaoRespository;
import br.com.zup.proposal.util.componets.ClientHostResolver;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ViagemController {

    @Autowired
    private CartaoRespository cartaoRespository;

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private ViagemClient viagemClient;

    @PostMapping("/viagem/{numeroCartao}")
    @Transactional
    public ResponseEntity<?> cadastrarAvidoDeViagem(@PathVariable String numeroCartao, @RequestBody @Valid NovaViagemRequest request, HttpServletRequest servletRequest){

        ClientHostResolver resolver = new ClientHostResolver(servletRequest);
        String userAgent = servletRequest.getHeader("User-Agent");
        String idOrigem = resolver.resolve();

        Optional<Cartao> cartao = cartaoRespository.findByNumero(numeroCartao);

        if (cartao.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Viagem viagem = request.converteParaViagem(idOrigem, userAgent, cartao.get());
        manager.persist(viagem);

        try {
            ViagemClient.AvisoRequest avisoRequest = new ViagemClient.AvisoRequest(viagem);
            ViagemClient.NovoAvisoResponse response = viagemClient.avisa(numeroCartao, avisoRequest);
            cartao.get().associaAviso(viagem, response.getResultado());
            cartaoRespository.save(cartao.get());
        } catch (FeignException.UnprocessableEntity e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
