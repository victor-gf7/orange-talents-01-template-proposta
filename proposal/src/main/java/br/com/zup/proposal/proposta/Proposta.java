package br.com.zup.proposal.proposta;

import br.com.zup.proposal.cartao.*;
import br.com.zup.proposal.config.validation.CPFOuCNPJ;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Proposta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CPFOuCNPJ @NotBlank
    @Column(nullable = false)
    private String documento;

    @NotBlank @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotNull @Valid
    @OneToOne(cascade = CascadeType.PERSIST)
    private Endereco endereco;

    @NotNull @Positive
    @Column(nullable = false)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacaoCliente status;

    @OneToOne(mappedBy = "proposta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String documento, @NotBlank @Email String email,
                    @NotBlank String nome, @NotNull @Valid Endereco endereco,
                    @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public StatusSolicitacaoCliente getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "id=" + id +
                ", documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco=" + endereco +
                ", salario=" + salario +
                ", status=" + status +
                ", cartao=" + cartao +
                '}';
    }

    public void atualizaStatus(String solicitacao) {

        this.status = StatusSolicitacaoCliente.resultadoPara(solicitacao);
    }

    public void associaCartao(CartaoClient.NovoCartaoResponse response) {

        Set<Bloqueio> bloqueios = response.getBloqueios().stream().map(Bloqueio::new).collect(Collectors.toSet());
        Set<Aviso> avisos = response.getAvisos().stream().map(Aviso::new).collect(Collectors.toSet());
        Set<Carteira> carteiras = response.getCarteiras().stream().map(Carteira::new).collect(Collectors.toSet());
        Set<Parcela> parcelas = response.getParcelas().stream().map(Parcela::new).collect(Collectors.toSet());
        Renegociacao renegociacao = null;
        Vencimento vencimento = null;
        if(response.getRenegociacao() != null){
             renegociacao = new Renegociacao(response.getRenegociacao());
        }
        if(response.getVencimento() != null){
            vencimento = new Vencimento(response.getVencimento());
        }


        this.cartao = new Cartao(response.getId(), response.getTitular(), response.getEmitidoEm(),
                response.getLimite(), bloqueios, avisos, carteiras,
                parcelas, renegociacao, vencimento, this);
    }
}