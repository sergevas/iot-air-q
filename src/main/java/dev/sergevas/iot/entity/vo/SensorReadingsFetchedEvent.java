package dev.sergevas.iot.entity.vo;

import dev.sergevas.iot.entity.model.SensorReadings;

public record SensorReadingsFetchedEvent(SensorReadings sensorReadings) {
}
