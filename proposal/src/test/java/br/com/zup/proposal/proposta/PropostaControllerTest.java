package br.com.zup.proposal.proposta;

import br.com.zup.proposal.proposta.repository.PropostaRepository;
import br.com.zup.proposal.proposta.request.NovaPropostaRequest;
import br.com.zup.proposal.util.builder.EnderecoBuilder;
import br.com.zup.proposal.util.builder.PropostaBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropostaRepository propostaRepository;

    @Test
    void deveriaCriarPropostaElegivel() throws Exception {
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

        mockMvc.perform(
                post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isCreated());

        assertEquals(StatusSolicitacaoCliente.ELEGIVEL, propostaRepository.findById(1L).get().getStatus());
    }

    @Test
    void deveriaCriarPropostaNaoElegivel() throws Exception {
        NovaPropostaRequest request = new PropostaBuilder()
                .comDocumento("304.728.780-50")
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

        mockMvc.perform(
                post("/propostas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isCreated());

        assertEquals(StatusSolicitacaoCliente.NAO_ELEGIVEL, propostaRepository.findById(1L).get().getStatus());

    }

    @Test
    void naoDeveriaCriarOutraPropostaParaOMesmoDocumento() throws Exception {
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

        mockMvc.perform(
                post("/propostas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(
                post("/propostas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void naoDeveriaPropostaParaODocumentoInvalido() throws Exception {
        NovaPropostaRequest request = new PropostaBuilder()
                .comDocumento("111.111.111-11")
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

        mockMvc.perform(
                post("/propostas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.field == 'documento')].error").isArray());
    }

    @Test
    void naoDeveriaPropostaComEmailInvalido() throws Exception {
        NovaPropostaRequest request = new PropostaBuilder()
                .comDocumento("137.881.460-60")
                .comEmail("useremail.com")
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

        mockMvc.perform(
                post("/propostas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.field == 'email')].error").value("must be a well-formed email address"));
    }

    @Test
    void naoDeveriaPropostaSemNome() throws Exception {
        NovaPropostaRequest request = new PropostaBuilder()
                .comDocumento("137.881.460-60")
                .comEmail("user@email.com")
                .comNome("")
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

        mockMvc.perform(
                post("/propostas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.field == 'nome')].error").value("must not be blank"));
    }

    @Test
    void naoDeveriaPropostaSemSalario() throws Exception {
        NovaPropostaRequest request = new PropostaBuilder()
                .comDocumento("137.881.460-60")
                .comEmail("user@email.com")
                .comNome("")
                .comEndereco(new EnderecoBuilder()
                        .comRua("Rua 1")
                        .comNumero(222)
                        .comBairro("Bairro novo")
                        .comCidade("Salvador")
                        .comEstado("Bahia")
                        .comCep("41.222-000")
                        .comComplemento("Ap 0001")
                        .ciar()).cria();

        mockMvc.perform(
                post("/propostas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.field == 'salario')].error").value("must not be null"));
    }

    @Test
    void deveriaDetalharUmaPropostaExistente() throws Exception {

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

        propostaRepository.save(proposta);

        mockMvc.perform(
                get("/propostas/{id}", proposta.getId())
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documento").value(proposta.getDocumento()))
                .andExpect(jsonPath("$.email").value(proposta.getEmail()))
                .andExpect(jsonPath("$.nome").value(proposta.getNome()))
                .andExpect(jsonPath("$.endereco.rua").value(proposta.getEndereco().getRua()))
                .andExpect(jsonPath("$.endereco.numero").value(proposta.getEndereco().getNumero()))
                .andExpect(jsonPath("$.endereco.bairro").value(proposta.getEndereco().getBairro()))
                .andExpect(jsonPath("$.endereco.cidade").value(proposta.getEndereco().getCidade()))
                .andExpect(jsonPath("$.endereco.estado").value(proposta.getEndereco().getEstado()))
                .andExpect(jsonPath("$.endereco.cep").value(proposta.getEndereco().getCep()))
                .andExpect(jsonPath("$.endereco.complemento").value(proposta.getEndereco().getComplemento()))
                .andExpect(jsonPath("$.salario").value(proposta.getSalario()))
                .andExpect(jsonPath("$.status").value(proposta.getStatus()));
    }
}