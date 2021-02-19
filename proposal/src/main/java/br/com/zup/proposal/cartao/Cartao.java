package br.com.zup.proposal.cartao;

import br.com.zup.proposal.biometria.Biometria;
import br.com.zup.proposal.bloqueio.Bloqueio;
import br.com.zup.proposal.carteira.CarteiraClient;
import br.com.zup.proposal.carteira.NovaCarteiraRequest;
import br.com.zup.proposal.proposta.Proposta;
import br.com.zup.proposal.viagem.Viagem;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cartao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String numero;

    @NotBlank
    private String titular;

    private LocalDateTime emitidoEm;

    private BigDecimal limite;

    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao = StatusCartao.ATIVO;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<Bloqueio> bloqueios = new HashSet<>();

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<Aviso> avisos = new HashSet<>();

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<Carteira> carteiras = new HashSet<>();

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<Parcela> parcelas = new HashSet<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    private Renegociacao renegociacao;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Vencimento vencimento;

    @OneToOne @Valid
    private Proposta proposta;

    @OneToMany(mappedBy = "cartao")
    private Set<Biometria> biometrias = new HashSet<>();

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotBlank String numero, @NotBlank String titular,
                  LocalDateTime emitidoEm, BigDecimal limite, Set<Bloqueio> bloqueios,
                  Set<Aviso> avisos, Set<Carteira> carteiras, Set<Parcela> parcelas,
                  Renegociacao renegociacao, Vencimento vencimento, @Valid Proposta proposta) {
        this.numero = numero;
        this.titular = titular;
        this.emitidoEm = emitidoEm;
        this.limite = limite;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
        this.proposta = proposta;
    }

    public void setStatusCartao(StatusCartao statusCartao) {
        this.statusCartao = statusCartao;
    }

    public String getNumero() {
        return numero;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public Set<Biometria> getBiometrias() {
        return biometrias;
    }

    public Set<Bloqueio> getBloqueios() {
        return bloqueios;
    }

    public void associaBloqueio(String ipOrigem, String userAgent, String sistemaResponsavel, String resultado, boolean ativo) {
        Bloqueio bloqueio = new Bloqueio(ipOrigem, userAgent, resultado, sistemaResponsavel, ativo, this);
        this.bloqueios.add(bloqueio);
    }

    public void associaAviso(Viagem viagem, String resultado) {
        Aviso aviso = new Aviso(viagem);
        aviso.setResultado(resultado);
        this.avisos.add(aviso);
    }

    public void associaCarteira(CarteiraClient.CarteiraAssociadaResponse response, NovaCarteiraRequest request) {

        this.carteiras.add(new Carteira(response.getId(), request.getEmail(), response.getResultado(), null, this));
    }
}
