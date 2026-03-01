package dev.sergevas.iot.boundary;

import dev.sergevas.iot.boundary.persistence.SensorNodeConfigRepository;
import dev.sergevas.iot.control.SensorReadingsUseCase;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TimerService implements Scheduled.SkipPredicate {

    @Inject
    SensorNodeConfigRepository sensorNodeConfigRepository;

    @Inject
    SensorReadingsUseCase sensorReadingsUseCase;

    @Scheduled(every = "${sensor.node.data.fetch.interval}", skipExecutionIf = TimerService.class)
    public void executeScheduled() {
        Log.info("Execute scheduled task...");
        sensorNodeConfigRepository.getMacAddresses().forEach(macAddress ->
                sensorReadingsUseCase.fetchAndStoreSensorReadings(macAddress.value()));
    }

    @Override
    public boolean test(ScheduledExecution execution) {
        Log.debug("Test scheduled execution skip criteria");
        var shouldSkip = sensorNodeConfigRepository.getMacAddresses().isEmpty();
        Log.debugf("shouldSkip=%b", shouldSkip);
        return shouldSkip;
    }
}
