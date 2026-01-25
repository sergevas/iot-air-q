package dev.sergevas.iot.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "iot_air_q_sensor_node_config")
@NamedQuery(name = "SensorNodeConfigEntity.findByMacAddress",
        query = "select s from SensorNodeConfigEntity s where s.macAddress = :macAddress")
@NamedQuery(name = "SensorNodeConfigEntity.findByMacAddressAndPropName",
        query = "select s from SensorNodeConfigEntity s where s.macAddress = :macAddress and s.propName = :propName")
public class SensorNodeConfigEntity {

    @Id
    @SequenceGenerator(sequenceName = "iot_air_q_sni_seq", allocationSize = 1, name = "iot_air_q_seq_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iot_air_q_seq_gen")
    private Long id;
    private String macAddress;
    private String propName;
    private String propValue;
    @CurrentTimestamp
    private Instant created;
    @UpdateTimestamp
    private Instant lastModified;

    public SensorNodeConfigEntity() {
    }

    public SensorNodeConfigEntity(String macAddress, String propName, String propValue) {
        this.macAddress = macAddress;
        this.propName = propName;
        this.propValue = propValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String macAddress) {
        this.propValue = macAddress;
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

    @Override
    public String toString() {
        return "SensorNodeInfoEntity{" +
                "id=" + id +
                ", macAddress='" + macAddress + '\'' +
                ", propName='" + propName + '\'' +
                ", propValue='" + propValue + '\'' +
                ", created=" + created +
                ", lastModified=" + lastModified +
                '}';
    }
}
