package dev.sergevas.iot.control;

import dev.sergevas.iot.support.TestData;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@QuarkusTest
class SensorReadingsUseCaseTest {

    @Inject
    SensorReadingsUseCase sensorReadingsUseCase;

    @Test
    void givenSensorProvidingReadings_whenGet_ThenShouldPersistAndProvideDataSuccessfully() {
        // This test is an integration test that verifies the entire flow of fetching sensor readings and persisting them.
        // It relies on the SensorNodeConfigUseCaseTest, SensorNodeClientTest, and SensorDataMapperTest to ensure that
        // the individual components are working correctly. Therefore, we can assume that if those tests pass, this test will also pass.

        var macAddress = "00:1B:44:11:3A:B7";
        sensorReadingsUseCase.fetchAndStoreSensorReadings(macAddress);
        var actualResponseBody = given()
                .accept("application/json")
                .queryParams(
                        "macAddress", macAddress
                ).when()
                .get("/readings")
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().body().asString();
        assertEquals(TestData.SENSOR_DATA_JSON_05, actualResponseBody, false);
    }
}
