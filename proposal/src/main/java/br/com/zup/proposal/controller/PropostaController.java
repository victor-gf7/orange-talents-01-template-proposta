package br.com.zup.proposal.controller;


import br.com.zup.proposal.dto.request.NovaPropostaRequest;
import br.com.zup.proposal.model.Proposta;
import br.com.zup.proposal.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @PostMapping("/propostas")
    @Transactional
    public ResponseEntity<?> criaProposta(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder builder){

        if(propostaRepository.existsByDocumento(request.getDocumento())){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        Proposta proposta = request.converteParaProposta();
        propostaRepository.save(proposta);

        URI url = builder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(url).build();
    }
}
