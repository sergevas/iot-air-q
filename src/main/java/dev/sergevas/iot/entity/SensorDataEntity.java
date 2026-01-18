package dev.sergevas.iot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "iot_air_q_sensor_data")
public class SensorDataEntity {

    @Id
    @SequenceGenerator(sequenceName = "iot_air_q_sd_seq", allocationSize = 1, name = "iot_air_q_seq_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iot_air_q_seq_gen")
    private Long id;
    private String macAddress;
    private UUID packageId;
    private String sensorName;
    private String readingType;
    private Double readingData;
    @CurrentTimestamp
    private Instant created;

    public SensorDataEntity() {
    }

    public SensorDataEntity(String macAddress, UUID packageId, String sensorName, String readingType, Double readingData) {
        this.macAddress = macAddress;
        this.packageId = packageId;
        this.sensorName = sensorName;
        this.readingType = readingType;
        this.readingData = readingData;
    }

    public Long getId() {
        return id;
    }

    public SensorDataEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public SensorDataEntity setMacAddress(String macAddress) {
        this.macAddress = macAddress;
        return this;
    }

    public UUID getPackageId() {
        return packageId;
    }

    public SensorDataEntity setPackageId(UUID packageId) {
        this.packageId = packageId;
        return this;
    }

    public String getSensorName() {
        return sensorName;
    }

    public SensorDataEntity setSensorName(String sensorName) {
        this.sensorName = sensorName;
        return this;
    }

    public String getReadingType() {
        return readingType;
    }

    public SensorDataEntity setReadingType(String readingType) {
        this.readingType = readingType;
        return this;
    }

    public Double getReadingData() {
        return readingData;
    }

    public SensorDataEntity setReadingData(Double readingData) {
        this.readingData = readingData;
        return this;
    }

    public Instant getCreated() {
        return created;
    }

    public SensorDataEntity setCreated(Instant created) {
        this.created = created;
        return this;
    }

    @Override
    public String toString() {
        return "IotAirQSensorDataEntity{" +
                "id=" + id +
                ", macAddress='" + macAddress + '\'' +
                ", packageId=" + packageId +
                ", sensorName='" + sensorName + '\'' +
                ", readingType=" + readingType +
                ", readingData=" + readingData +
                ", created=" + created +
                '}';
    }
}
