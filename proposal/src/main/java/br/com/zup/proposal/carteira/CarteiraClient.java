package br.com.zup.proposal.carteira;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "associaCarteira", url = "${cartao.host}")
public interface CarteiraClient {

    @PostMapping("/api/cartoes/{id}/carteiras")
    CarteiraAssociadaResponse asscociaCarteira(@PathVariable String id, @RequestBody NovaCarteiraRequest request);

    class CarteiraAssociadaResponse{
        private String resultado;
        private String id;

        public String getResultado() {
            return resultado;
        }

        public String getId() {
            return id;
        }
    }
}
