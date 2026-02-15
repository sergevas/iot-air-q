package dev.sergevas.iot.entity.vo;

import java.util.Objects;

public class MacAddressVO {

    private String macAddress;

    public MacAddressVO(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MacAddressVO that = (MacAddressVO) o;
        return Objects.equals(macAddress, that.macAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(macAddress);
    }
}
