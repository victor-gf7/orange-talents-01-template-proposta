package br.com.zup.proposal.carteira;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NovaCarteiraRequest {

    @Email @NotBlank
    @JsonProperty
    private String email;

    @Enumerated(EnumType.STRING)
    @JsonProperty
    private CarteirasDigital carteira;

    public NovaCarteiraRequest(@Email @NotBlank String email, CarteirasDigital carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public CarteirasDigital getCarteira() {
        return carteira;
    }

    @Override
    public String toString() {
        return "NovaCarteiraRequest{" +
                "email='" + email + '\'' +
                ", carteira=" + carteira +
                '}';
    }
}
