package br.com.zup.proposal.bloqueio;

import br.com.zup.proposal.cartao.AssociarCartao;
import br.com.zup.proposal.cartao.Cartao;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class BloqueioController {

    private final Logger logger = LoggerFactory.getLogger(AssociarCartao.class);

    @Autowired
    private CartaoRespository cartaoRespository;

    @Autowired
    private BloqueiaClient bloqueiaClient;

    @PostMapping("/bloqueios/{numeroCartao}")
    public ResponseEntity<?> bloqueiaCartao(@PathVariable @NotNull String numeroCartao, HttpServletRequest servletRequest){

        Optional<Cartao> cartao = cartaoRespository.findByNumero(numeroCartao);

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

        BloqueiaClient.NovoBloqueioResponse response;
        BloqueiaClient.NovoBloqueioRequest request;

        try {
            request = new BloqueiaClient.NovoBloqueioRequest("MyAPI");
            response = bloqueiaClient.bloqueia(numeroCartao, request);
            logger.info("O cartao {} foi {} pelo sistema {}", numeroCartao, response.getResultado(), request.getSistemaResponsavel());

        } catch (FeignException.UnprocessableEntity e) {
            logger.error("Erro ao Bloquear cartão devido: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }


        ClientHostResolver resolver = new ClientHostResolver(servletRequest);

        cartao.get().associaBloqueio(resolver.resolve(), servletRequest.getHeader("User-Agent"),
                request.getSistemaResponsavel(), response.getResultado(), true);

        cartaoRespository.save(cartao.get());

        return ResponseEntity.ok().build();
    }
}
