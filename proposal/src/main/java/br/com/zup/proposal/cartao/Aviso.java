package br.com.zup.proposal.cartao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@Entity
public class Aviso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate validoAte;

    private String destino;

    @Deprecated
    public Aviso() {
    }

    public Aviso(CartaoClient.AvisosResponse response) {
        this.validoAte = response.getValidoAte();
        this.destino = response.getDestino();
    }
}
