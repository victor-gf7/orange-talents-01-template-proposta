package br.com.zup.proposal.biometria;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NovaBiometriaRequestTest {

    @Test
    void deveriaValidarSeOFIngerprintEstaEmBase64() {
        NovaBiometriaRequest bimoetriaBase64 = new NovaBiometriaRequest("dGVzdGUgYmlvbWV0cmlh");
        NovaBiometriaRequest bimoetriaIncorreta= new NovaBiometriaRequest("Valor Aleatorio");

        assertTrue(bimoetriaBase64.validaFingerprint());
        assertFalse(bimoetriaIncorreta.validaFingerprint());
    }
}