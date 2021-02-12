package br.com.zup.proposal.proposta.request;

import br.com.zup.proposal.proposta.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NovoEndereçoRequest {

    @NotBlank @JsonProperty
    private String rua;

    @NotNull @JsonProperty
    private Integer numero;

    @NotBlank @JsonProperty
    private String bairro;

    @NotBlank @JsonProperty
    private String cidade;

    @NotBlank @JsonProperty
    private String estado;

    @NotBlank @JsonProperty
    private String cep;

    @JsonProperty
    private String complemento;

    public NovoEndereçoRequest(@NotBlank String rua, @NotNull Integer numero,
                               @NotBlank String bairro, @NotBlank String cidade,
                               @NotBlank String estado, @NotBlank String cep, String complemento) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        return "NovoEndereçoRequest{" +
                "rua='" + rua + '\'' +
                ", numero=" + numero +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", cep='" + cep + '\'' +
                ", complemento='" + complemento + '\'' +
                '}';
    }

    public Endereco converteParaEndereco() {

        return new Endereco(this.rua, this.numero, this.bairro, this.cidade, this.estado, this.cep, this.complemento);
    }
}
