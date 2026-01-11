package dev.sergevas.iot.boundary.rest.airq.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.util.List;

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
}
