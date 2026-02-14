package dev.sergevas.iot.entity.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SensorData {

    @JsonbProperty("macAddress")
    private String macAddress;

    @JsonbProperty("packageId")
    private UUID packageId;

    @JsonbProperty("sensorReadings")
    private List<Sensor> sensorReadings;

    @JsonbCreator
    public SensorData(@JsonbProperty("macAddress") String macAddress,
                      @JsonbProperty("packageId") UUID packageId,
                      @JsonbProperty("sensorReadings") List<Sensor> sensorReadings) {
        this.macAddress = macAddress;
        this.packageId = packageId;
        this.sensorReadings = sensorReadings;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public SensorData setMacAddress(String macAddress) {
        this.macAddress = macAddress;
        return this;
    }

    public UUID getPackageId() {
        return packageId;
    }

    public SensorData setPackageId(UUID packageId) {
        this.packageId = packageId;
        return this;
    }

    public List<Sensor> getSensorReadings() {
        return sensorReadings;
    }

    public void setSensorReadings(List<Sensor> sensorReadings) {
        this.sensorReadings = sensorReadings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorData that = (SensorData) o;
        return Objects.equals(macAddress, that.macAddress) && Objects.equals(packageId, that.packageId)
                && Objects.equals(sensorReadings, that.sensorReadings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(macAddress, packageId, sensorReadings);
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "macAddress='" + macAddress + '\'' +
                ", packageId='" + packageId + '\'' +
                ", sensorReadings=" + sensorReadings +
                '}';
    }
}
