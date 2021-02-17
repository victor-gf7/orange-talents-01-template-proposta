package br.com.zup.proposal.proposta.repository;

import br.com.zup.proposal.proposta.request.NovaPropostaRequest;
import br.com.zup.proposal.proposta.Proposta;
import br.com.zup.proposal.util.builder.EnderecoBuilder;
import br.com.zup.proposal.util.builder.PropostaBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PropostaRepositoryTest {

    @Autowired
    private PropostaRepository repository;

    @Autowired
    private TestEntityManager manager;

    @Test
    void deveriaVerificarSeExisteUmaPopostaParaUmDocumento() {
        NovaPropostaRequest request = new PropostaBuilder()
                .comDocumento("137.881.460-60")
                .comEmail("user@email.com")
                .comNome("Usuario Teste")
                .comSalario(new BigDecimal(2500.0))
                .comEndereco(new EnderecoBuilder()
                        .comRua("Rua 1")
                        .comNumero(222)
                        .comBairro("Bairro novo")
                        .comCidade("Salvador")
                        .comEstado("Bahia")
                        .comCep("41.222-000")
                        .comComplemento("Ap 0001")
                        .ciar()).cria();

        Proposta proposta = request.converteParaProposta();
        manager.persist(proposta);

        assertTrue(repository.existsByDocumento(proposta.getDocumento()));
        assertFalse(repository.existsByDocumento("54651318465"));//valor aleatorio
    }
}