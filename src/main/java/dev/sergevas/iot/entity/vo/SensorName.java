package dev.sergevas.iot.entity.vo;

public record SensorName(String sensorName) implements FilterProperty {
    @Override
    public String value() {
        return sensorName;
    }
}
