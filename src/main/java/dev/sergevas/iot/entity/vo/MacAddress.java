package dev.sergevas.iot.entity.vo;

public record MacAddress(String macAddress) implements FilterProperty {
    @Override
    public String value() {
        return macAddress;
    }
}
