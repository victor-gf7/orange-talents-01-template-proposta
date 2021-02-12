package br.com.zup.proposal.proposta;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Endereco {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String rua;

    @NotNull
    @Column(nullable = false)
    private Integer numero;

    @NotBlank
    @Column(nullable = false)
    private String bairro;

    @NotBlank
    @Column(nullable = false)
    private String cidade;

    @NotBlank
    @Column(nullable = false)
    private String estado;

    @NotBlank
    @Column(nullable = false)
    private String cep;

    private String complemento;

    @Deprecated
    public Endereco() {
    }

    public Endereco(@NotBlank String rua, @NotNull Integer numero,
                    @NotBlank String bairro, @NotBlank String cidade,
                    @NotBlank String estado, @NotBlank String cep,
                    String complemento) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.complemento = complemento;
    }

    public String getRua() {
        return rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }

    public String getComplemento() {
        return complemento;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", rua='" + rua + '\'' +
                ", numero=" + numero +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", cep='" + cep + '\'' +
                ", complemento='" + complemento + '\'' +
                '}';
    }
}
