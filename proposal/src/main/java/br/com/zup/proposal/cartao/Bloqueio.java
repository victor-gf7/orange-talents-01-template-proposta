package br.com.zup.proposal.cartao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idAccount;

    private LocalDateTime bloqueadoEm;

    private String sistemaResponsavel;

    private boolean ativo;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(CartaoClient.BloqueiosResponse response) {
        this.idAccount = response.getId();
        this.bloqueadoEm = response.getBloqueadoEm();
        this.sistemaResponsavel = response.getSistemaResponsavel();
        this.ativo = response.isAtivo();
    }

}
