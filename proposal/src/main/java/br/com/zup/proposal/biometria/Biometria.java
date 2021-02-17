package br.com.zup.proposal.biometria;

import br.com.zup.proposal.cartao.Cartao;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String fingerprint;

    private LocalDate dataDeAssociacao = LocalDate.now();

    @ManyToOne @Valid
    private Cartao cartao;

    public Long getId() {
        return id;
    }

    @Deprecated
    public Biometria() {
    }

    public Biometria(@NotBlank String fingerprint, @Valid Cartao cartao) {
        this.fingerprint = fingerprint;
        this.cartao = cartao;
    }
}
