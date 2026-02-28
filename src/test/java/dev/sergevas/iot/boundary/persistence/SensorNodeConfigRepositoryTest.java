package dev.sergevas.iot.boundary.persistence;

import dev.sergevas.iot.entity.vo.PropertyValue;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class SensorNodeConfigRepositoryTest {

    @Inject
    SensorNodeConfigRepository sensorNodeConfigRepository;

    @Test
    void givenPersistedPropertyConfig_whenGet_thenShouldReturnListOfPropertyValues() {
        var propertyValues = sensorNodeConfigRepository.getSensorPropertyValues(PropertyValue.MacAddress.class);
        System.out.printf("Property values: %s%n", propertyValues);
        assertFalse(propertyValues.isEmpty());
    }
}