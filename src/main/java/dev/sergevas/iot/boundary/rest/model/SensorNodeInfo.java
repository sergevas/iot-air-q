package dev.sergevas.iot.boundary.rest.model;

import jakarta.json.bind.annotation.JsonbProperty;

public class SensorNodeInfo {

    @JsonbProperty("ip")
    private String ip;
    @JsonbProperty("macAddress")
    private String macAddress;

    public SensorNodeInfo() {
    }

    public SensorNodeInfo(String ip, String macAddress) {
        this.ip = ip;
        this.macAddress = macAddress;
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

    @Override
    public String toString() {
        return "SensorNodeInfo{" +
                "ip='" + ip + '\'' +
                ", macAddress='" + macAddress + '\'' +
                '}';
    }
}
