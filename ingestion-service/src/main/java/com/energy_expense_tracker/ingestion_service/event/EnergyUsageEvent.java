package com.energy_expense_tracker.ingestion_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyUsageEvent {
    private Long deviceId;
    private double energyConsumed;
    private Instant timestamp;
}
