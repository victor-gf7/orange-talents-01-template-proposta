package br.com.zup.proposal.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@FeignClient(name = "associaCartao", url = "localhost:8888/api/cartoes")
public interface CartaoClient {

    @GetMapping
    NovoCartaoResponse consulta(@RequestParam("idProposta") Long id);


    class NovoCartaoResponse{

        private String id;
        private String titular;
        private LocalDateTime emitidoEm;
        private BigDecimal limite;
        private Long idProposta;
        private Set<BloqueiosResponse> bloqueios = new HashSet<>();
        private Set<AvisosResponse> avisos = new HashSet<>();
        private Set<CarteirasResponse> carteiras = new HashSet<>();
        private Set<ParcelasResponse> parcelas = new HashSet<>();
        private RenegociacaoResponse renegociacao;
        private VencimentoResponse vencimento;

        public String getId() {
            return id;
        }

        public String getTitular() {
            return titular;
        }

        public LocalDateTime getEmitidoEm() {
            return emitidoEm;
        }

        public BigDecimal getLimite() {
            return limite;
        }

        public Long getIdProposta() {
            return idProposta;
        }

        public Set<BloqueiosResponse> getBloqueios() {
            return bloqueios;
        }

        public Set<AvisosResponse> getAvisos() {
            return avisos;
        }

        public Set<CarteirasResponse> getCarteiras() {
            return carteiras;
        }

        public Set<ParcelasResponse> getParcelas() {
            return parcelas;
        }

        public RenegociacaoResponse getRenegociacao() {
            return renegociacao;
        }

        public VencimentoResponse getVencimento() {
            return vencimento;
        }

        @Override
        public String toString() {
            return "NovoCartaoResponse{" +
                    "id='" + id + '\'' +
                    ", titular='" + titular + '\'' +
                    ", emitidoEm=" + emitidoEm +
                    ", limite=" + limite +
                    ", idProposta=" + idProposta +
                    ", bloqueios=" + bloqueios +
                    ", avisos=" + avisos +
                    ", carteiras=" + carteiras +
                    ", parcelas=" + parcelas +
                    ", renegociacao=" + renegociacao +
                    ", vencimento=" + vencimento +
                    '}';
        }
    }

    class BloqueiosResponse{

        private String id;
        private LocalDateTime bloqueadoEm;
        private String sistemaResponsavel;
        private boolean ativo;


        public String getId() {
            return id;
        }

        public LocalDateTime getBloqueadoEm() {
            return bloqueadoEm;
        }

        public String getSistemaResponsavel() {
            return sistemaResponsavel;
        }

        public boolean isAtivo() {
            return ativo;
        }
    }

    class AvisosResponse {
        private LocalDate validoAte;
        private String destino;

        public LocalDate getValidoAte() {
            return validoAte;
        }

        public String getDestino() {
            return destino;
        }
    }

    class CarteirasResponse {
        private String id;
        private String email;
        private LocalDateTime associadaEm;
        private String emissor;

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public LocalDateTime getAssociadaEm() {
            return associadaEm;
        }

        public String getEmissor() {
            return emissor;
        }
    }

    class ParcelasResponse{
        private String id;
        private Integer quantidade;
        private BigDecimal valor;


        public String getId() {
            return id;
        }

        public Integer getQuantidade() {
            return quantidade;
        }

        public BigDecimal getValor() {
            return valor;
        }
    }

    class RenegociacaoResponse{
        private String id;
        private Integer quantidade;
        private BigDecimal valor;
        private LocalDateTime dataDeCriacao;


        public String getId() {
            return id;
        }

        public Integer getQuantidade() {
            return quantidade;
        }

        public BigDecimal getValor() {
            return valor;
        }

        public LocalDateTime getDataDeCriacao() {
            return dataDeCriacao;
        }
    }

    class VencimentoResponse{
        private String id;
        private Integer dia;
        private LocalDateTime dataDeCriacao;

        public String getId() {
            return id;
        }

        public Integer getDia() {
            return dia;
        }

        public LocalDateTime getDataDeCriacao() {
            return dataDeCriacao;
        }
    }
}
