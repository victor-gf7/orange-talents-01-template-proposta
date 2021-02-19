package br.com.zup.proposal.cartao;

import br.com.zup.proposal.viagem.Viagem;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Aviso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate validoAte;

    private String destino;

    private String resultado;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Aviso() {
    }

    public Aviso(Viagem viagem) {
        this.validoAte = viagem.getDataTerminoViagem();
        this.destino = viagem.getDestinoViagem();
        this.cartao = viagem.getCartao();
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
