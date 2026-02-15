package dev.sergevas.iot.control;

import dev.sergevas.iot.entity.SensorDataEntity;
import dev.sergevas.iot.entity.model.SensorData;
import dev.sergevas.iot.entity.model.SensorReadings;

import java.util.List;
import java.util.UUID;

public class Mapper {

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
                            new SensorReadings(sensorName, sr.getType(), sr.getData()));
                }).map(nsr -> toSensorDataEntity(
                        macAddress,
                        packageId,
                        nsr.sensorName(),
                        nsr.readingType(),
                        nsr.readingData()
                )).toList();
    }

    public static SensorReadings toSensorReadings(SensorDataEntity entity) {
        return new SensorReadings(entity.getPackageId(), entity.getMacAddress(),
                entity.getSensorName(), entity.getReadingType(),
                entity.getReadingData(), entity.getCreated());
    }


}
