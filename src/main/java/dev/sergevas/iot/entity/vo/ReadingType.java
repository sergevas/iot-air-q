package dev.sergevas.iot.entity.vo;

public record ReadingType(String readingType) implements FilterProperty {
    @Override
    public String value() {
        return readingType;
    }
}
