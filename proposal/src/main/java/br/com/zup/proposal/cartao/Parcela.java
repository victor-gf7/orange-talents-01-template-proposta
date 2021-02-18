package br.com.zup.proposal.cartao;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Parcela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idAccount;

    private Integer quantidade;

    private BigDecimal valor;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Parcela() {
    }

    public Parcela(CartaoClient.ParcelasResponse response) {
        this.idAccount = response.getId();
        this.quantidade = response.getQuantidade();
        this.valor = response.getValor();
    }
}
