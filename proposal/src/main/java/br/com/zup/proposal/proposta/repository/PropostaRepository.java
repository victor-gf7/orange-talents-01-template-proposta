package br.com.zup.proposal.proposta.repository;

import br.com.zup.proposal.proposta.Proposta;
import br.com.zup.proposal.proposta.StatusSolicitacaoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    boolean existsByDocumento(String documento);

    @Query(value = "SELECT p FROM Proposta p LEFT JOIN Cartao c ON p.id = c.proposta.id WHERE p.status =:status  and c.id IS NULL")
    List<Proposta> buscaTodasAsPropostasElegiveisSemCartao(StatusSolicitacaoCliente status);
}
