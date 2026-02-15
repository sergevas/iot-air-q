package dev.sergevas.iot.entity.model;

import java.time.Instant;
import java.util.UUID;

public record SensorReadings(UUID packageId, String macAddress, String sensorName, String readingType,
                             Double readingData, Instant created) {
    public SensorReadings(String sensorName, String readingType, Double readingData) {
        this(null, null, sensorName, readingType, readingData, null);
    }
}
