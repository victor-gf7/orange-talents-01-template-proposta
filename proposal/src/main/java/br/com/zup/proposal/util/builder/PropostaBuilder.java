package br.com.zup.proposal.util.builder;

import br.com.zup.proposal.dto.request.NovaPropostaRequest;
import br.com.zup.proposal.dto.request.NovoEndereçoRequest;

import java.math.BigDecimal;

public class PropostaBuilder {

    private String documento;
    private String email;
    private String nome;
    private NovoEndereçoRequest endereco;
    private BigDecimal salario;


    public PropostaBuilder comDocumento(String documento){
        this.documento = documento;
        return this;
    }

    public PropostaBuilder comEmail(String email){
        this.email = email;
        return this;
    }

    public PropostaBuilder comNome(String nome){
        this.nome = nome;
        return this;
    }

    public PropostaBuilder comEndereco(NovoEndereçoRequest endereco){
        this.endereco = endereco;
        return this;
    }

    public PropostaBuilder comSalario(BigDecimal salario){
        this.salario = salario;
        return this;
    }

    public NovaPropostaRequest cria(){
        return new NovaPropostaRequest(this.documento,this.email,this.nome,this.endereco,this.salario);
    }
}
