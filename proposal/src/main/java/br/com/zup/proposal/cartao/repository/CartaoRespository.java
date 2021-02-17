package br.com.zup.proposal.cartao.repository;

import br.com.zup.proposal.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRespository extends JpaRepository<Cartao, Long> {

    Optional<Cartao> findByNumero(String numeroCartao);
}
