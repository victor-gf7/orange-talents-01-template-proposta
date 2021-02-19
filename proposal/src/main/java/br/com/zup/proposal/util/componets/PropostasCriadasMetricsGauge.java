package br.com.zup.proposal.util.componets;

import br.com.zup.proposal.proposta.Proposta;
import br.com.zup.proposal.proposta.repository.PropostaRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class PropostasCriadasMetricsGauge implements MeterBinder {

    @Autowired
    private PropostaRepository propostaRepository;


    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        Gauge.builder("propostas_criadas", this, value -> obterQuantidadePropostasSolicitadas())
                .description("Quantidade de propostas solicitadas")
                .tags(Tags.of(Tag.of("data", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))))
                .baseUnit("propostas_key")
                .register(meterRegistry);
    }

    public Integer obterQuantidadePropostasSolicitadas() {

        return propostaRepository.findAll().size();
    }
}
