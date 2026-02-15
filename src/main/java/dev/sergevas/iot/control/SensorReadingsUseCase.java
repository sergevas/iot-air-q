package dev.sergevas.iot.control;

import dev.sergevas.iot.boundary.persistence.SensorDataRepository;
import dev.sergevas.iot.entity.vo.SensorReadingsFetchedEvent;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
public class SensorReadingsUseCase {

    @Inject
    SensorNodeConfigUseCase sensorNodeConfigUseCase;

    @Inject
    SensorNodeRestClientProvider sensorNodeRestClientProvider;

    @Inject
    SensorDataRepository sensorDataRepository;

    @Inject
    Event<SensorReadingsFetchedEvent> sensorReadingsFetchedEvent;

    public void fetchAndStoreSensorReadings(String macAddress) {
        Log.infof("Enter fetchAndStoreSensorReadings() for macAddress=%s", macAddress);
        try {
            var ip = sensorNodeConfigUseCase.getSensorNodeIp(macAddress);
            var port = sensorNodeConfigUseCase.getSensorNodePort(macAddress);
            var sensor = sensorNodeRestClientProvider.getClient(ip, port);
            var sensorData = sensor.getSensorData();
            Mapper.toSensorDataEntities(sensorData)
                    .forEach(sd -> {
                        sensorDataRepository.save(sd);
                        sensorReadingsFetchedEvent.fireAsync(new SensorReadingsFetchedEvent(Mapper.toSensorReadings(sd)));
                    });
        } catch (Exception e) {
            Log.errorf(e, "Error fetching and storing sensor readings for macAddress=%s", macAddress);
        }
    }
}
