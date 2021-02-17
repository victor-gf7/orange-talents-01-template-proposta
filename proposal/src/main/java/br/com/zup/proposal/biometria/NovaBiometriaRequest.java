package br.com.zup.proposal.biometria;

import br.com.zup.proposal.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.Base64;

public class NovaBiometriaRequest {

    @NotBlank
    @JsonProperty
    private String fingerprint;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovaBiometriaRequest(@NotBlank String fingerprint) {
        this.fingerprint = fingerprint;
    }

    @Override
    public String toString() {
        return "NovaBiometriaRequest{" +
                "fingierprint='" + fingerprint + '\'' +
                '}';
    }

    public Biometria converteParaBiometria(Cartao cartao) {

        return new Biometria(this.fingerprint, cartao);
    }

    public boolean validaFingerprint() {
        String base64 = this.fingerprint.trim();
        return (base64.length() % 4 == 0) && base64.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");
    }
}
