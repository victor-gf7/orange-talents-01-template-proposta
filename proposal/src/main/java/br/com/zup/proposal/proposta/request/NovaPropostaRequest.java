package br.com.zup.proposal.proposta.request;

import br.com.zup.proposal.config.validation.CPFOuCNPJ;
import br.com.zup.proposal.proposta.Proposta;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @CPFOuCNPJ @NotBlank
    @JsonProperty
    private String documento;

    @NotBlank @Email
    @JsonProperty
    private String email;

    @NotBlank
    @JsonProperty
    private String nome;

    @NotNull @Valid
    @JsonProperty
    private NovoEndereçoRequest endereco;

    @NotNull @Positive
    @JsonProperty
    private BigDecimal salario;

    public NovaPropostaRequest(@NotBlank String documento, @NotBlank @Email String email,
                               @NotBlank String nome, @NotNull @Valid NovoEndereçoRequest endereco,
                               @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getDocumento() {
        return documento;
    }

    @Override
    public String toString() {
        return "NovaPropostaRequest{" +
                "documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco=" + endereco +
                ", salario=" + salario +
                '}';
    }

    public Proposta converteParaProposta() {
        return new Proposta(this.documento, this.email, this.nome, this.endereco.converteParaEndereco(),this.salario);
    }
}
