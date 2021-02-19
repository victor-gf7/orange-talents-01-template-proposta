package br.com.zup.proposal.bloqueio;

import br.com.zup.proposal.cartao.Cartao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipOrigem;

    private String userAgent;

    private String resultado;

    private LocalDateTime bloqueadoEm = LocalDateTime.now();

    private String sistemaResponsavel;

    private boolean ativo;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Bloqueio() {
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    public String getResultado() {
        return resultado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Bloqueio(String ipOrigem, String userAgent, String resultado, String sistemaResponsavel, boolean ativo, Cartao cartao) {
        this.ipOrigem = ipOrigem;
        this.userAgent = userAgent;
        this.resultado = resultado;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
        this.cartao = cartao;
    }
}
