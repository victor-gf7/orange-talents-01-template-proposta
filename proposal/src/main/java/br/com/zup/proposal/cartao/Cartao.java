package br.com.zup.proposal.cartao;

import br.com.zup.proposal.biometria.Biometria;
import br.com.zup.proposal.biometria.NovaBiometriaRequest;
import br.com.zup.proposal.proposta.Proposta;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Bloqueio> bloqueios = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Aviso> avisos = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Carteira> carteiras = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST)
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

    public String getNumero() {
        return numero;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public Set<Biometria> getBiometrias() {
        return biometrias;
    }

    //    public void associaBloqueios(Set<CartaoClient.BloqueiosResponse> response){
//        this.bloqueios = response.stream().map(Bloqueio::new).collect(Collectors.toSet());
//    }

}
