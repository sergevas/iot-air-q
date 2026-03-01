package dev.sergevas.iot.control;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class FilterServiceTest {

    @Inject
    FilterService filterService;

    @Test
    void givenPersistedSensorData_whenGetFilterOptions_thenShouldReturnSuccessfully() {
        var filterOptions = filterService.getFilter();
        System.out.println(filterOptions);
        assertNotNull(filterOptions);
    }
}
