package br.com.zup.proposal.cartao;

import br.com.zup.proposal.proposta.Proposta;
import br.com.zup.proposal.proposta.StatusSolicitacaoCliente;
import br.com.zup.proposal.proposta.repository.PropostaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class AssociarCartao {


    private final Logger logger = LoggerFactory.getLogger(AssociarCartao.class);

    @Autowired
    private CartaoClient cartaoClient;

    @Autowired
    private PropostaRepository propostaRepository;


    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void AssociarCartao(){

        List<Proposta> propostasElegiveis = propostaRepository
                .buscaTodasAsPropostasElegiveisSemCartao(StatusSolicitacaoCliente.ELEGIVEL);

        for (Proposta proposta: propostasElegiveis){

            try {
                CartaoClient.NovoCartaoResponse response = cartaoClient.consulta(proposta.getId());
                System.out.println(response.toString());
                proposta.associaCartao(response);
                logger.info("Cartão numero={} com limite de ={} associado ao cliente documento={} com sucesso!",
                        proposta.getCartao().getNumero(), proposta.getCartao().getLimite(), proposta.getDocumento());
            } catch (FeignException e) {
                logger.error("Falha ao associar o cartão devidp: " + e.getMessage());
            }

        }

    }
}
