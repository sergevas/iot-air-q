package dev.sergevas.iot.boundary.rest.client;

import dev.sergevas.iot.control.SensorNodeRestClientProvider;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class SensorNodeClientTest {

    @Inject
    SensorNodeRestClientProvider sensorNodeRestClientProvider;

    @ConfigProperty(name = "quarkus.wiremock.devservices.port")
    int wiremockPort;

    @Test
    void givenSensorProvidingReadings_whenGet_ThenShouldReturnSuccessfully() {
        var sensorData = sensorNodeRestClientProvider.getClient("localhost", String.valueOf(wiremockPort))
                .getSensorData();
        var sensorReadings = sensorData.getSensorReadings();
        assertNotNull(sensorReadings);
        assertEquals("00:1B:44:11:3A:B7", sensorData.getMacAddress());
        assertEquals(UUID.fromString("f8a3c816-4722-40e1-8cce-f20cf7bbabc5"), sensorData.getPackageId());
        assertEquals(2, sensorReadings.size());
        var sht31x = sensorReadings.getFirst();
        assertEquals("SHT31X", sht31x.getName());
        var sht31xReadings = sht31x.getReadings();
        assertEquals(2, sht31xReadings.size());
        assertEquals("TEMP", sht31xReadings.getFirst().getType());
        assertEquals(26.93866, sht31xReadings.getFirst().getData().doubleValue());
        assertEquals("HUMID", sht31xReadings.getLast().getType());
        assertEquals(34.47471, sht31xReadings.getLast().getData().doubleValue());
        var ccs811 = sensorReadings.getLast();
        assertEquals("CCS811", ccs811.getName());
        var ccs811Readings = ccs811.getReadings();
        assertEquals(2, ccs811Readings.size());
        assertEquals("TVOC", ccs811Readings.getFirst().getType());
        assertEquals(6, ccs811Readings.getFirst().getData().doubleValue());
        assertEquals("CO2", ccs811Readings.getLast().getType());
        assertEquals(445, ccs811Readings.getLast().getData().doubleValue());
    }
}
