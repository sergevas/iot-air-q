package dev.sergevas.iot.entity.vo;

public sealed interface FilterProperty permits MacAddress, SensorName, ReadingType {
    String value();

}
