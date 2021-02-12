package br.com.zup.proposal.util.builder;


import br.com.zup.proposal.proposta.request.NovoEndereçoRequest;

public class EnderecoBuilder {

    private String rua;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String complemento;


    public EnderecoBuilder comRua(String rua){
        this.rua = rua;
        return this;
    }
    public EnderecoBuilder comNumero(Integer numero){
        this.numero = numero;
        return this;
    }
    public EnderecoBuilder comBairro(String bairro){
        this.bairro = bairro;
        return this;
    }
    public EnderecoBuilder comCidade(String cidade){
        this.cidade = cidade;
        return this;
    }
    public EnderecoBuilder comEstado(String estado){
        this.estado = estado;
        return this;
    }
    public EnderecoBuilder comCep(String cep){
        this.cep = cep;
        return this;
    }
    public EnderecoBuilder comComplemento(String complemento){
        this.complemento = complemento;
        return this;
    }

    public NovoEndereçoRequest ciar(){
        return new NovoEndereçoRequest(this.rua,this.numero,this.bairro, this.cidade,this.estado,this.cep, this.complemento);
    }

}
