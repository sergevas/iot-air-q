package dev.sergevas.iot.entity.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.util.List;
import java.util.Objects;

public class Sensor {

    @JsonbProperty("name")
    private String name;
    @JsonbProperty("readings")
    private List<Reading> readings;

    public Sensor() {
    }

    @JsonbCreator
    public Sensor(@JsonbProperty("name") String name,
                  @JsonbProperty("readings") List<Reading> readings) {
        this.name = name;
        this.readings = readings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Reading> getReadings() {
        return readings;
    }

    public void setReadings(List<Reading> readings) {
        this.readings = readings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return Objects.equals(name, sensor.name) && Objects.equals(readings, sensor.readings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, readings);
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "name='" + name + '\'' +
                ", readings=" + readings +
                '}';
    }
}
