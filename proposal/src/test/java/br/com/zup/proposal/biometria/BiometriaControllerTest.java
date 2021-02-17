package br.com.zup.proposal.biometria;

import br.com.zup.proposal.cartao.Cartao;
import br.com.zup.proposal.proposta.Proposta;
import br.com.zup.proposal.proposta.PropostaController;
import br.com.zup.proposal.proposta.repository.PropostaRepository;
import br.com.zup.proposal.proposta.request.NovaPropostaRequest;
import br.com.zup.proposal.util.builder.EnderecoBuilder;
import br.com.zup.proposal.util.builder.PropostaBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class BiometriaControllerTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Proposta proposta;
    private Cartao cartao;

    @BeforeEach
    private void criaPropostaECartao(){
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

        this.proposta = request.converteParaProposta();
        manager.persist(proposta);

        this.cartao = new Cartao("1234-1236-4567-8888", proposta.getNome(), LocalDateTime.now(),
                new BigDecimal(2500.00), null, null, null, null,
                null,null, proposta);

        manager.persist(cartao);
    }

    @Test
    void deveriaCadastrarUmaBiometria() throws Exception {

        NovaBiometriaRequest request = new NovaBiometriaRequest("dGVzdGUgYmlvbWV0cmlh");

        mockMvc.perform(
                post("/biometrias/{idCartao}", cartao.getNumero())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void naoDeveriaCadastrarUmaBiometriaComFingerprintInvalido() throws Exception {

        NovaBiometriaRequest request = new NovaBiometriaRequest("fingerprint invalido");

        mockMvc.perform(
                post("/biometrias/{idCartao}", cartao.getNumero())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fingerprint").value("A biometria está em formato inválido"));
    }

    @Test
    void naoDeveriaCadastrarUmaBiometriaSemExistirOCartaoInformado() throws Exception {

        NovaBiometriaRequest request = new NovaBiometriaRequest("dGVzdGUgYmlvbWV0cmlh");

        mockMvc.perform(
                post("/biometrias/{idCartao}", "1111-1111-1111-1111")//Cartao inexistente
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}