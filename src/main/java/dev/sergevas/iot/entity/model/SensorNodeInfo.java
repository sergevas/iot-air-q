package dev.sergevas.iot.entity.model;

import jakarta.json.bind.annotation.JsonbProperty;

public class SensorNodeInfo {

    @JsonbProperty("ip")
    private String ip;
    @JsonbProperty("macAddress")
    private String macAddress;
    @JsonbProperty("css811Baseline")
    private String css811Baseline;

    public SensorNodeInfo() {
    }

    public SensorNodeInfo(String macAddress, String ip, String css811Baseline) {
        this.macAddress = macAddress;
        this.ip = ip;
        this.css811Baseline = css811Baseline;
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

    public String getCss811Baseline() {
        return css811Baseline;
    }

    public void setCss811Baseline(String css811Baseline) {
        this.css811Baseline = css811Baseline;
    }

    public boolean isNew() {
        return css811Baseline != null && !css811Baseline.isEmpty();
    }

    @Override
    public String toString() {
        return "SensorNodeInfo{" +
                "macAddress='" + macAddress + '\'' +
                ", ip='" + ip + '\'' +
                ", css811Baseline'" + css811Baseline + '\'' +
                '}';
    }
}
