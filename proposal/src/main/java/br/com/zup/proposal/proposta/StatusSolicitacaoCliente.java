package br.com.zup.proposal.proposta;

public enum StatusSolicitacaoCliente {

    ELEGIVEL, NAO_ELEGIVEL;

    public static StatusSolicitacaoCliente resultadoPara(String solicitacao) {
        if(solicitacao.equals("COM_RESTRICAO")){
            return NAO_ELEGIVEL;
        }
        return  ELEGIVEL;
    }
}
