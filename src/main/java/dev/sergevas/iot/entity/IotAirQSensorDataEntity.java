package dev.sergevas.iot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "iot_air_q_sensor_data")
public class IotAirQSensorDataEntity {

    @Id
    @SequenceGenerator(sequenceName = "iot_air_q_sd_seq", allocationSize = 1, name = "iot_air_q_sd_seq_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iot_air_q_sd_seq_gen")
    private Long id;
    private String sensorName;
    private Double readingType;
    private Double readingData;
    private OffsetDateTime created;

    public IotAirQSensorDataEntity() {
    }
    
    public IotAirQSensorDataEntity(String sensorName, Double readingType, Double readingData) {
        this.sensorName = sensorName;
        this.readingType = readingType;
        this.readingData = readingData;
    }

    public Long getId() {
        return id;
    }

    public IotAirQSensorDataEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSensorName() {
        return sensorName;
    }

    public IotAirQSensorDataEntity setSensorName(String sensorName) {
        this.sensorName = sensorName;
        return this;
    }

    public Double getReadingType() {
        return readingType;
    }

    public IotAirQSensorDataEntity setReadingType(Double readingType) {
        this.readingType = readingType;
        return this;
    }

    public Double getReadingData() {
        return readingData;
    }

    public IotAirQSensorDataEntity setReadingData(Double readingData) {
        this.readingData = readingData;
        return this;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public IotAirQSensorDataEntity setCreated(OffsetDateTime created) {
        this.created = created;
        return this;
    }
}
