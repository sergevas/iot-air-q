package dev.sergevas.iot.control;

import dev.sergevas.iot.IotAirQualityException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class SensorNodeConfigUseCaseTest {

    @Inject
    SensorNodeConfigUseCase sensorNodeConfigUseCase;

    @Test
    void givenPersistedSensorNodeConfigWithIp_whenGetSensorNodeIp_thenShouldReturnSuccessfully() {
        assertEquals("192.168.1.104", sensorNodeConfigUseCase.getSensorNodeIp("00:1B:44:11:3A:B7"));
    }

    @Test
    void givenNoSensorNodeConfig_whenGetSensorNodeIp_thenShouldThrowException() {
        var throwable = assertThrows(IotAirQualityException.class, () -> sensorNodeConfigUseCase.getSensorNodeIp("00:00:00:00:00:00"));
        assertEquals("Sensor node IP not found for macAddress=00:00:00:00:00:00", throwable.getMessage());
    }
}