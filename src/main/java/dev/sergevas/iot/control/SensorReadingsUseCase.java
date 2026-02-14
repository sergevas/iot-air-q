package dev.sergevas.iot.control;

import dev.sergevas.iot.boundary.persistence.SensorDataRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SensorReadingsUseCase {

    @Inject
    SensorNodeConfigUseCase sensorNodeConfigUseCase;
    @Inject
    SensorNodeRestClientProvider sensorNodeRestClientProvider;
    @Inject
    SensorDataRepository sensorDataRepository;

    public void fetchAndStoreSensorReadings(String macAddress) {
        Log.infof("Enter fetchAndStoreSensorReadings() for macAddress=%s", macAddress);
        var ip = sensorNodeConfigUseCase.getSensorNodeIp(macAddress);
        var port = sensorNodeConfigUseCase.getSensorNodePort(macAddress);
        var sensor = sensorNodeRestClientProvider.getClient(ip, port);
        var readings = sensor.getSensorData();
        SensorDataMapper.toSensorDataEntities(readings)
                .forEach(sensorDataRepository::save);
    }

   /* public List<SensorReading> getSensorReadings(String macAddress, String sensorName, String readingType) {
        Log.infof("Enter getSensorReadings() for macAddress=%s, sensorName=%s, readingType=%s",
                macAddress, sensorName, readingType);
        var sensorDataEntities = sensorDataRepository.find(macAddress, sensorName, readingType);
        return SensorDataMapper.toSensorReadings(sensorDataEntities, sensorName, readingType);
    }*/
}
