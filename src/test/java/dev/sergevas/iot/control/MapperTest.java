package dev.sergevas.iot.control;

import dev.sergevas.iot.entity.model.Reading;
import dev.sergevas.iot.entity.model.Sensor;
import dev.sergevas.iot.entity.model.SensorData;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MapperTest {

    @Test
    void toSensorDataEntities() {
        var sensorData = new SensorData(
                "00:1B:44:11:3A:B7",
                UUID.fromString("f8a3c816-4722-40e1-8cce-f20cf7bbabc5"),
                List.of(
                        new Sensor("SHT31X", List.of(new Reading("TEMP", 26.93866),
                                new Reading("HUMID", 34.47471))),
                        new Sensor("CCS811", List.of(new Reading("TVOC", 6.0),
                                new Reading("CO2", 445.0)))));
        var sensorDataEntities = Mapper.toSensorDataEntities(sensorData);
        assertNotNull(sensorDataEntities);
        assertEquals(4, sensorDataEntities.size());
    }
}
