package org.example.first_hometask.service;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetricService {
  private final MeterRegistry registry;

  public void recordExecution(String type, Runnable action) {
    long start = System.nanoTime();
    action.run();
    long end = System.nanoTime();
    double durationSec = (end - start) / 1_000_000.0;

    DistributionSummary
        .builder("task.execution")
        .description("Общая информация о таске")
        .baseUnit("milliseconds")
        .serviceLevelObjectives(10, 50, 100, 200, 300, 400, 500, 1000, 10000)
//        .publishPercentiles(0.5, 0.75, 0.95, 0.99)
        .tags("type", type)
        .register(registry)
        .record(durationSec);
  }
}
