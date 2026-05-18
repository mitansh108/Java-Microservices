package com.energy_expense_tracker.ingestion_service.controller;

import com.energy_expense_tracker.ingestion_service.dto.EnergyUsageDto;
import com.energy_expense_tracker.ingestion_service.service.IngestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ingestion/")
@Slf4j
public class IngestionController {

    private final IngestionService ingestionService;

    public IngestionController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping("/energy-usage")
    public ResponseEntity<String> ingestEnergyUsage(@RequestBody EnergyUsageDto input) {
        ingestionService.ingestEnergyUsage(input);
        return ResponseEntity.ok("Energy usage data ingested successfully");
    }
}
