package br.com.zup.proposal.viagem;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@FeignClient(name = "notificaViagem", url = "${cartao.host}")
public interface ViagemClient {

    @PostMapping("/api/cartoes/{id}/avisos")
    NovoAvisoResponse avisa(@PathVariable String id, @RequestBody AvisoRequest request);

    class AvisoRequest{

        private String destino;

        private LocalDate validoAte;

        public AvisoRequest(Viagem viagem) {
            this.destino = viagem.getDestinoViagem();
            this.validoAte = viagem.getDataTerminoViagem();
        }

        public String getDestino() {
            return destino;
        }

        public LocalDate getValidoAte() {
            return validoAte;
        }
    }

    class NovoAvisoResponse{

        private String resultado;

        public String getResultado() {
            return resultado;
        }
    }
}
