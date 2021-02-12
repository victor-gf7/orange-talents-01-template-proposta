package br.com.zup.proposal.config.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProposalHealthCheck implements HealthIndicator {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Override
    public Health health() {
        Map<String, Object> details = new HashMap<>();
        details.put("versão", "1.0");
        details.put("descrição", "Projeto para atender as funcionalidades da Proposta");
        details.put("endereço", "127.0.0.1");


        try {
            Connection connection = DriverManager.getConnection(url, username, "");
            connection.close();
            return Health.status(Status.UP).withDetails(details).build();
        } catch (SQLException ex) {
            return Health.status(Status.DOWN).withDetails(details).build();
        }
    }
}
