package br.com.zup.proposal.cartao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idAccount;

    private String email;

    private LocalDateTime associadaEm;

    private String emissor;

    @Deprecated
    public Carteira() {
    }

    public Carteira(CartaoClient.CarteirasResponse response) {
        this.idAccount = response.getId();
        this.email = response.getEmail();
        this.associadaEm = response.getAssociadaEm();
        this.emissor = response.getEmissor();
    }
}
