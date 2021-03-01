package br.com.zup.proposal.bloqueio;

import br.com.zup.proposal.cartao.*;
import br.com.zup.proposal.cartao.repository.CartaoRespository;
import br.com.zup.proposal.proposta.Proposta;
import br.com.zup.proposal.proposta.StatusSolicitacaoCliente;
import br.com.zup.proposal.proposta.request.NovaPropostaRequest;
import br.com.zup.proposal.util.builder.EnderecoBuilder;
import br.com.zup.proposal.util.builder.PropostaBuilder;
import br.com.zup.proposal.util.builder.TesteDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.awaitility.Duration;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class BloqueioControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CartaoRespository cartaoRespository;
    @SpyBean
    private AssociarCartao associarCartao;
    @MockBean
    private BloqueiaClient bloqueiaClient;

    @Test
    void deveriaBloquearOCartao() throws Exception {
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
        proposta.setStatus(StatusSolicitacaoCliente.ELEGIVEL);
        entityManager.persist(proposta);

        Set<Bloqueio> bloqueios  = new HashSet<>();

        Cartao cartao = new Cartao("1111-1111-1111-1111", "Usuario Teste", LocalDateTime.now(), new BigDecimal("2000.0"),
                bloqueios , null, null, null, null, null, proposta);

        entityManager.persist(cartao);

        when(bloqueiaClient.bloqueia(cartao.getNumero(), TesteDataBuilder.getBloqueioRequest())).thenReturn(TesteDataBuilder.getBloqueioResponse());

        mockMvc.perform(
                post("/bloqueios/{numeroCartao}", cartao.getNumero())
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}