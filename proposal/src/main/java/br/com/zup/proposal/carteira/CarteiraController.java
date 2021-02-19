package br.com.zup.proposal.carteira;

import br.com.zup.proposal.cartao.Cartao;
import br.com.zup.proposal.cartao.Carteira;
import br.com.zup.proposal.cartao.repository.CartaoRespository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class CarteiraController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private CartaoRespository cartaoRespository;

    @Autowired
    private CarteiraClient carteiraClient;

    @PostMapping("/carteiras/{numeroCartao}")
    @Transactional
    public ResponseEntity<?> associaCarteira(@PathVariable @NotNull String numeroCartao, @RequestBody @Valid NovaCarteiraRequest request,
                                             UriComponentsBuilder builder){

        Optional<Cartao> cartao = cartaoRespository.findByNumero(numeroCartao);

        if(cartao.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        try {

            CarteiraClient.CarteiraAssociadaResponse response = carteiraClient.asscociaCarteira(numeroCartao, request);
            Carteira carteira = new Carteira(response.getId(), request.getEmail(), response.getResultado(), null, cartao.get());
            manager.persist(carteira);
            URI location = builder.path("/carteiras/{id}").buildAndExpand(carteira.getId()).toUri();

            return ResponseEntity.created(location).build();

        } catch (FeignException.UnprocessableEntity e) {
            HashMap<String, Object> responseMap = new HashMap<>();
            responseMap.put("Carteira", "Esta Cartão já foi associado a carteira " + request.getCarteira());
            return ResponseEntity.unprocessableEntity().body(responseMap);
        }
    }
}
