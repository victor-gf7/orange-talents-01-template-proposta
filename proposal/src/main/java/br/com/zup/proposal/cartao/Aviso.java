package br.com.zup.proposal.cartao;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Aviso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate validoAte;

    private String destino;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Aviso() {
    }

    public Aviso(CartaoClient.AvisosResponse response) {
        this.validoAte = response.getValidoAte();
        this.destino = response.getDestino();
    }
}
