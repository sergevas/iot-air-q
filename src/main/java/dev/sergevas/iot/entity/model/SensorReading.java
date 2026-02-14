package dev.sergevas.iot.entity.model;

import java.util.UUID;

public record SensorReading(UUID packageId, String macAddress, String sensorName, String readingType,
                            Double readingData) {
    public SensorReading(String sensorName, String readingType, Double readingData) {
        this(null, null, sensorName, readingType, readingData);
    }
}
