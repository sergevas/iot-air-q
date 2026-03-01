package dev.sergevas.iot.boundary.persistence;

import dev.sergevas.iot.entity.vo.MacAddress;
import dev.sergevas.iot.entity.vo.ReadingType;
import dev.sergevas.iot.entity.vo.SensorName;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
class SensorDataRepositoryTest {

    @Inject
    SensorDataRepository sensorDataRepository;

    @Test
    void givenPersistedSensorData_whenGetMacAddressFilterPropertyList_thenShouldReturnSuccessfully() {
        var filterProperties = sensorDataRepository.getSensorDataFilterProperties(MacAddress.class);
        assertFalse(filterProperties.isEmpty());
    }

    @Test
    void givenPersistedSensorData_whenGetSensorNameFilterPropertyList_thenShouldReturnSuccessfully() {
        var filterProperties = sensorDataRepository.getSensorDataFilterProperties(SensorName.class);
        assertFalse(filterProperties.isEmpty());
    }

    @Test
    void givenPersistedSensorData_whenGetReadingTypeFilterPropertyList_thenShouldReturnSuccessfully() {
        var filterProperties = sensorDataRepository.getSensorDataFilterProperties(ReadingType.class);
        assertFalse(filterProperties.isEmpty());
    }
}
