package dev.sergevas.iot.entity.model;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;

import java.util.Objects;

import static java.util.Objects.nonNull;

public class SensorNodeInfo {

    @JsonbProperty("ip")
    private String ip;
    @JsonbProperty("port")
    private Integer port;
    @JsonbProperty("macAddress")
    private String macAddress;
    @JsonbProperty("css811Baseline")
    private Double css811Baseline;

    public SensorNodeInfo() {
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = Integer.valueOf(port);
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Double getCss811Baseline() {
        return css811Baseline;
    }

    public void setCss811Baseline(Double css811Baseline) {
        this.css811Baseline = css811Baseline;
    }

    public void setCss811Baseline(String css811Baseline) {
        this.css811Baseline = Double.valueOf(css811Baseline);
    }

    @JsonbTransient
    public boolean isNew() {
        return !(nonNull(ip) || nonNull(css811Baseline));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorNodeInfo that = (SensorNodeInfo) o;
        return Objects.equals(ip, that.ip) && Objects.equals(port, that.port)
                && Objects.equals(macAddress, that.macAddress)
                && Objects.equals(css811Baseline, that.css811Baseline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port, macAddress, css811Baseline);
    }

    @Override
    public String toString() {
        return "SensorNodeInfo{" +
                "macAddress='" + macAddress + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", css811Baseline'" + css811Baseline + '\'' +
                '}';
    }
}
