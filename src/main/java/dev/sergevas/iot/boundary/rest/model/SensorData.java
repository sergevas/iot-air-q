package dev.sergevas.iot.boundary.rest.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.util.List;

public class SensorData {

    @JsonbProperty("sensorReadings")
    private List<Sensor> sensorReadings;

    public SensorData() {
    }

    @JsonbCreator
    public SensorData(@JsonbProperty("sensorReadings") List<Sensor> sensorReadings) {
        this.sensorReadings = sensorReadings;
    }

    public List<Sensor> getSensorReadings() {
        return sensorReadings;
    }

    public void setSensorReadings(List<Sensor> sensorReadings) {
        this.sensorReadings = sensorReadings;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "sensorReadings=" + sensorReadings +
                '}';
    }
}
