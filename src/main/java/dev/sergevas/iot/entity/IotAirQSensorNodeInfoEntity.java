package dev.sergevas.iot.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "iot_air_q_sensor_node_info")
public class IotAirQSensorNodeInfoEntity {

    @Id
    @SequenceGenerator(sequenceName = "iot_air_q_sd_seq", allocationSize = 1, name = "iot_air_q_sd_seq_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iot_air_q_sd_seq_gen")
    private Long id;
    private String ip;
    private Double macAddress;
    @CurrentTimestamp
    private Instant created;
    @UpdateTimestamp
    private Instant lastModified;

    public IotAirQSensorNodeInfoEntity() {
    }

    public IotAirQSensorNodeInfoEntity(String ip, Double macAddress) {
        this.ip = ip;
        this.macAddress = macAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Double getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(Double macAddress) {
        this.macAddress = macAddress;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }
}
