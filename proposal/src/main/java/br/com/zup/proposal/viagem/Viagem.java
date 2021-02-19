package br.com.zup.proposal.viagem;

import br.com.zup.proposal.cartao.Cartao;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Viagem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String destinoViagem;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataTerminoViagem;

    private LocalDateTime instanteSolicitacao = LocalDateTime.now();

    private String ipOrigem;

    private String userAgent;

    @Valid
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Viagem() {
    }

    public Viagem(@NotBlank String destinoViagem, @NotNull LocalDate dataTerminoViagem,
                  String ipRequisicao, String userAgent, @Valid Cartao cartao) {
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = dataTerminoViagem;
        this.ipOrigem = ipRequisicao;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    @Override
    public String toString() {
        return "Viagem{" +
                "id=" + id +
                ", destinoViagem='" + destinoViagem + '\'' +
                ", dataTerminoViagem=" + dataTerminoViagem +
                ", instanteSolicitacao=" + instanteSolicitacao +
                ", ipOrigem='" + ipOrigem + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", cartao=" + cartao +
                '}';
    }
}
