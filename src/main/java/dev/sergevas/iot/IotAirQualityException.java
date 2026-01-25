package dev.sergevas.iot;

public class IotAirQualityException extends RuntimeException {

    public IotAirQualityException(String message) {
        super(message);
    }

    public IotAirQualityException(String message, Throwable cause) {
        super(message, cause);
    }
}
