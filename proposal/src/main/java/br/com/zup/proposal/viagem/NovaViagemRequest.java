package br.com.zup.proposal.viagem;

import br.com.zup.proposal.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.EntityManager;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NovaViagemRequest {

    @NotBlank
    @JsonProperty
    private String destinoViagem;

    @NotNull
    @Future
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @JsonProperty
    private LocalDate dataTerminoViagem;


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovaViagemRequest(@NotBlank String destinoViagem, @NotNull @Future LocalDate dataTerminoViagem) {
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = dataTerminoViagem;
    }

    @Override
    public String toString() {
        return "NovaViagemRequest{" +
                "destinoViagem='" + destinoViagem + '\'' +
                ", dataTerminoViagem=" + dataTerminoViagem +
                '}';
    }

    public Viagem converteParaViagem(String ipOrigem, String userAgent, Cartao cartao) {

        return new Viagem(this.destinoViagem, this.dataTerminoViagem, ipOrigem, userAgent, cartao);
    }
}
