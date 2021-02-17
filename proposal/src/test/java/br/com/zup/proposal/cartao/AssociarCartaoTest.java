package br.com.zup.proposal.cartao;

import org.awaitility.Duration;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;


@SpringBootTest
@ActiveProfiles("test")
class AssociarCartaoTest {

    @SpyBean
    private AssociarCartao associarCartao;

    @Test
    @Transactional
    void deveriaAssociarUmCartaoParaUmCLienteElegivel(){
        await()
                .atMost(Duration.TEN_SECONDS)
                .untilAsserted(() -> verify(associarCartao, atLeast(1)).AssociarCartao());
    }
}