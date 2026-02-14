package dev.sergevas.iot.entity.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.util.Objects;

public class Reading {

    @JsonbProperty("type")
    private String type;
    @JsonbProperty("data")
    private Double data;

    @JsonbCreator
    public Reading(@JsonbProperty("type") String type,
                   @JsonbProperty("data") Double data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reading reading = (Reading) o;
        return Objects.equals(type, reading.type) && Objects.equals(data, reading.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, data);
    }

    @Override
    public String toString() {
        return "Reading{" +
                "type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}
