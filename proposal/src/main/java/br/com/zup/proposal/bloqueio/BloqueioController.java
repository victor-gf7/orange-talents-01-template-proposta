package br.com.zup.proposal.bloqueio;

import br.com.zup.proposal.cartao.AssociarCartao;
import br.com.zup.proposal.cartao.Cartao;
import br.com.zup.proposal.cartao.StatusCartao;
import br.com.zup.proposal.cartao.repository.CartaoRespository;
import br.com.zup.proposal.util.componets.ClientHostResolver;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class BloqueioController {

    private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private CartaoRespository cartaoRespository;

    @Autowired
    private BloqueiaClient bloqueiaClient;

    @PostMapping("/bloqueios/{numeroCartao}")
    @Transactional
    public ResponseEntity<?> bloqueiaCartao(@PathVariable @NotNull String numeroCartao, HttpServletRequest servletRequest){

        Optional<Cartao> cartao = cartaoRespository.findByNumero(numeroCartao);
        ClientHostResolver resolver = new ClientHostResolver(servletRequest);
        String userAgent = servletRequest.getHeader("User-Agent");
        String idOrigem = resolver.resolve();


        if(cartao.isPresent()){
            for (Bloqueio bloqueio: cartao.get().getBloqueios())
                if (bloqueio.isAtivo() && bloqueio.getResultado().equals("BLOQUEADO")){
                    HashMap<String, Object> responseMap = new HashMap<>();
                    responseMap.put("Bloqueio", "Esta Cratão já se encontra bloqueado");
                    return ResponseEntity.unprocessableEntity().body(responseMap);
                }
        } else{
            return ResponseEntity.notFound().build();
        }


        cartao.get().associaBloqueio(idOrigem,userAgent, "MyAPI", "BLOQUEADO", true);
        cartaoRespository.save(cartao.get());
        logger.info("O cartão {} foi BLOQUEADO pelo sistema MyAPI", cartao.get().getNumero());

        BloqueiaClient.NovoBloqueioResponse response;
        BloqueiaClient.NovoBloqueioRequest request;

        try {
            request = new BloqueiaClient.NovoBloqueioRequest("MyAPI");
            bloqueiaClient.bloqueia(numeroCartao, request);
            cartao.get().setStatusCartao(StatusCartao.BLOQUEADO);

        } catch (FeignException.UnprocessableEntity e) {
            logger.error("Erro ao Bloquear cartão devido: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
