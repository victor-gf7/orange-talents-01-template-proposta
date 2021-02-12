package br.com.zup.proposal.proposta.response;

import br.com.zup.proposal.proposta.Proposta;
import br.com.zup.proposal.proposta.StatusSolicitacaoCliente;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.math.BigDecimal;

public class DetalhesPropostaResponse {

    @JsonProperty
    private String documento;

    @JsonProperty
    private String email;

    @JsonProperty
    private String nome;

    @JsonProperty
    private EnderecoResponse endereco;

    @JsonProperty
    private BigDecimal salario;

    @JsonProperty
    private StatusSolicitacaoCliente status;


    public DetalhesPropostaResponse(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.endereco = new EnderecoResponse(proposta.getEndereco());
        this.salario = proposta.getSalario();
        this.status = proposta.getStatus();
    }
}
