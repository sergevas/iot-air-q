package dev.sergevas.iot.control;

import dev.sergevas.iot.entity.SensorDataEntity;
import dev.sergevas.iot.entity.model.SensorData;
import dev.sergevas.iot.entity.model.SensorReading;

import java.util.List;
import java.util.UUID;

public class SensorDataMapper {

    public static SensorDataEntity toSensorDataEntity(String macAddress,
                                                      UUID packageId,
                                                      String sensorName,
                                                      String readingType,
                                                      Double readingData) {
        return new SensorDataEntity(macAddress, packageId, sensorName, readingType, readingData);
    }

    public static List<SensorDataEntity> toSensorDataEntities(SensorData sensorData) {
        final var macAddress = sensorData.getMacAddress();
        final var packageId = sensorData.getPackageId();
        return sensorData.getSensorReadings().stream()
                .flatMap(srs -> {
                    var sensorName = srs.getName();
                    return srs.getReadings().stream().map(sr ->
                            new SensorReading(sensorName, sr.getType(), sr.getData()));
                }).map(nsr -> toSensorDataEntity(
                        macAddress,
                        packageId,
                        nsr.sensorName(),
                        nsr.readingType(),
                        nsr.readingData()
                )).toList();
    }
}
