package com.energy_expense_tracker.ingestion_service.simulation;

import com.energy_expense_tracker.ingestion_service.dto.EnergyUsageDto;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

@Slf4j

public class ContinuousDataSimulator implements CommandLineRunner {
    private final RestTemplate restTemplate = new RestTemplate();
    private final Random random = new Random();

    @Value("${simulation.requests-per-interval}")
    private int requestPerInterval;

    @Value("${simulation.endpoint}")
    private String ingestionEndpoint;
    private long intervalMs;
    @Override
    public void run(String... args) throws Exception {
        log.info("ContinuousDataSimulator started...");
        while (true){
            //sendMockData();
            Thread.sleep(intervalMs);
        }
    }

    public void sendMockData() {
        for (int i = 0; i < requestPerInterval; i++) {
            EnergyUsageDto dto = new EnergyUsageDto(
                    random.nextLong(1, 6),
                    Math.round(random.nextDouble(0, 2) * 100) / 100.0,
                    LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()
            );

            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<EnergyUsageDto> request = new HttpEntity<>(dto, headers);
                restTemplate.postForEntity(ingestionEndpoint, request, Void.class);
                log.info("Sent Data to DB: {}", dto);
            } catch (Exception e) {
                log.error("Failed to send data : {}", e.getMessage());
            }
        }
    }
}
