package dev.sergevas.iot.boundary.rest.client;

import dev.sergevas.iot.control.SensorNodeRestClientProvider;
import dev.sergevas.iot.entity.model.Reading;
import dev.sergevas.iot.entity.model.Sensor;
import dev.sergevas.iot.entity.model.SensorData;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class SensorNodeClientTest {

    @Inject
    SensorNodeRestClientProvider sensorNodeRestClientProvider;

    @ConfigProperty(name = "quarkus.wiremock.devservices.port")
    int wiremockPort;

    @Test
    void givenSensorProvidingReadings_whenGet_ThenShouldReturnSuccessfully() {
        var expected = new SensorData(
                "00:1B:44:11:3A:B7",
                UUID.fromString("f8a3c816-4722-40e1-8cce-f20cf7bbabc5"),
                List.of(
                        new Sensor("SHT31X", List.of(
                                new Reading("TEMP", 26.93866),
                                new Reading("HUMID", 34.47471))),
                        new Sensor("CCS811",
                                List.of(new Reading("TVOC", 6),
                                        new Reading("CO2", 445)))));
        var sensorData = sensorNodeRestClientProvider.getClient("localhost", String.valueOf(wiremockPort))
                .getSensorData();
        assertEquals(expected, sensorData);
    }
}
