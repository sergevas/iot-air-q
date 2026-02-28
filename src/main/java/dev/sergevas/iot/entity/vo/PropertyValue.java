package dev.sergevas.iot.entity.vo;

import static dev.sergevas.iot.entity.vo.PropertyValue.MacAddress;
import static dev.sergevas.iot.entity.vo.PropertyValue.PackageId;
import static dev.sergevas.iot.entity.vo.PropertyValue.ReadingType;
import static dev.sergevas.iot.entity.vo.PropertyValue.SensorName;

public sealed interface PropertyValue permits MacAddress, SensorName, ReadingType, PackageId {
    String value();

    record MacAddress(String macAddress) implements PropertyValue {
        @Override
        public String value() {
            return macAddress;
        }
    }

    record SensorName(String sensorName) implements PropertyValue {
        @Override
        public String value() {
            return sensorName;
        }
    }

    record ReadingType(String readingType) implements PropertyValue {
        @Override
        public String value() {
            return readingType;
        }
    }

    record PackageId(String readingType) implements PropertyValue {
        @Override
        public String value() {
            return readingType;
        }
    }
}
