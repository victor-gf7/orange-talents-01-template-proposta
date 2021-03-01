package br.com.zup.proposal.bloqueio;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bloqueiaCartao", url = "${cartao.host}")
public interface BloqueiaClient {

    @PostMapping("/api/cartoes/{id}/bloqueios")
    NovoBloqueioResponse bloqueia(@PathVariable String id, @RequestBody NovoBloqueioRequest request);

    class NovoBloqueioRequest {

        @JsonProperty
        private String sistemaResponsavel;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public NovoBloqueioRequest(String sistemaResponsavel) {
            this.sistemaResponsavel = sistemaResponsavel;
        }

        public String getSistemaResponsavel() {
            return sistemaResponsavel;
        }
    }

    class NovoBloqueioResponse{

        private String resultado;

        public String getResultado() {
            return resultado;
        }

        public void setResultado(String resultado) {
            this.resultado = resultado;
        }

        @Override
        public String toString() {
            return "NovoBloqueioResponse{" +
                    "resultado='" + resultado + '\'' +
                    '}';
        }
    }
}
