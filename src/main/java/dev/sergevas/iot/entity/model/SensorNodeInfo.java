package dev.sergevas.iot.entity.model;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;

import static java.util.Objects.nonNull;

public class SensorNodeInfo {

    @JsonbProperty("ip")
    private String ip;
    @JsonbProperty("port")
    private String port;
    @JsonbProperty("macAddress")
    private String macAddress;
    @JsonbProperty("css811Baseline")
    private String css811Baseline;

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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCss811Baseline() {
        return css811Baseline;
    }

    public void setCss811Baseline(String css811Baseline) {
        this.css811Baseline = css811Baseline;
    }

    @JsonbTransient
    public boolean isNew() {
        return !(nonNull(ip) || nonNull(css811Baseline));
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
