package br.com.zup.proposal.cartao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idAccount;

    private String email;

    private LocalDateTime associadaEm = LocalDateTime.now();

    private String resultado;

    private String emissor;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Carteira() {
    }

    public Carteira(String idAccount, String email, String resultado, String emissor, Cartao cartao) {
        this.idAccount = idAccount;
        this.email = email;
        this.resultado = resultado;
        this.emissor = emissor;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }
}
