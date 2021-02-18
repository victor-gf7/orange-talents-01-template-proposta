package br.com.zup.proposal.cartao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Vencimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idAccount;

    private Integer dia;

    private LocalDateTime dataDeCriacao;

    @Deprecated
    public Vencimento() {
    }

    public Vencimento(CartaoClient.VencimentoResponse response) {
        this.idAccount = response.getId();
        this.dia = response.getDia();
        this.dataDeCriacao = response.getDataDeCriacao();
    }
}
