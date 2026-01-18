package dev.sergevas.iot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "iot_air_q_sensor_node_info")
public class SensorNodeInfoEntity {

    @Id
    @SequenceGenerator(sequenceName = "iot_air_q_sni_seq", allocationSize = 1, name = "iot_air_q_seq_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iot_air_q_seq_gen")
    private Long id;
    private String ip;
    private String macAddress;
    @CurrentTimestamp
    private Instant created;
    @UpdateTimestamp
    private Instant lastModified;

    public SensorNodeInfoEntity() {
    }

    public SensorNodeInfoEntity(String ip, String macAddress) {
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

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
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
