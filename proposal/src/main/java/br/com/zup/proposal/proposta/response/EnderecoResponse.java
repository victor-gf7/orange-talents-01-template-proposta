package br.com.zup.proposal.proposta.response;


import br.com.zup.proposal.proposta.Endereco;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnderecoResponse {

    @JsonProperty
    private String rua;

    @JsonProperty
    private Integer numero;

    @JsonProperty
    private String bairro;

    @JsonProperty
    private String cidade;

    @JsonProperty
    private String estado;

    @JsonProperty
    private String cep;

    @JsonProperty
    private String complemento;

    public EnderecoResponse(Endereco endereco) {
        this.rua = endereco.getRua();
        this.numero = endereco.getNumero();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
        this.cep = endereco.getCep();
        this.complemento = endereco.getComplemento();
    }
}
