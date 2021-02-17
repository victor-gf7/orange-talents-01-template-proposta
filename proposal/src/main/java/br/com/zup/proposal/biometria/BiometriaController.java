package br.com.zup.proposal.biometria;

import br.com.zup.proposal.cartao.Cartao;
import br.com.zup.proposal.cartao.repository.CartaoRespository;
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
import java.net.URI;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class BiometriaController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private CartaoRespository cartaoRespository;

    @PostMapping("/biometrias/{idCartao}")
    @Transactional
    public ResponseEntity<?> cadastraBiometria(@PathVariable String idCartao,
                                               @RequestBody @Valid NovaBiometriaRequest request, UriComponentsBuilder builder){

        Optional<Cartao> cartao = cartaoRespository.findByNumero(idCartao);

        if(!request.validaFingerprint()){
            HashMap<String, Object> response = new HashMap<>();
            response.put("fingerprint", "A biometria está em formato inválido");
            return ResponseEntity.badRequest().body(response);
        }

        if(cartao.isPresent()){
            Biometria biometria = request.converteParaBiometria(cartao.get());
            manager.persist(biometria);
            URI url = builder.path("biometrias/{id}").buildAndExpand(biometria.getId()).toUri();
            return ResponseEntity.created(url).build();
        }

        return ResponseEntity.notFound().build();
    }
}
