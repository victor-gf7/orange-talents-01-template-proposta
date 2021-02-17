package br.com.zup.proposal.proposta;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "consultaCliente", url = "${analise.host}")
public interface AnaliseClient {

    @PostMapping("/api/solicitacao")
    ConsultaStatusResponse analise(@RequestBody AnaliseStatusRequest analiseStatusRequest);

    class AnaliseStatusRequest{

        private String documento;
        private String nome;
        private Long idProposta;

        public AnaliseStatusRequest(Proposta proposta) {
            this.documento = proposta.getDocumento();
            this.nome = proposta.getNome();
            this.idProposta = proposta.getId();
        }

        public String getDocumento() {
            return documento;
        }

        public String getNome() {
            return nome;
        }

        public Long getIdProposta() {
            return idProposta;
        }
    }

    class ConsultaStatusResponse {
        private String documento;
        private String nome;
        private Long idProposta;
        private String resultadoSolicitacao;

        public String getDocumento() {
            return documento;
        }

        public String getNome() {
            return nome;
        }

        public Long getIdProposta() {
            return idProposta;
        }

        public String getResultadoSolicitacao() {
            return resultadoSolicitacao;
        }
    }
}
