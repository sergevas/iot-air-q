package dev.sergevas.iot.boundary.rest.api;

import dev.sergevas.iot.support.TestData;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static dev.sergevas.iot.support.TestData.SENSOR_DATA_JSON_03;
import static dev.sergevas.iot.support.TestData.SENSOR_DATA_JSON_04;
import static io.restassured.RestAssured.given;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@QuarkusTest
class SensorReadingsResourceTest {

    @Test
    void givenPersistedSensorReadings_whenGETbyMacAddress_thenShouldReturnSuccessfully() {
        var actualResponseBody = given()
                .accept("application/json")
                .queryParams(
                        "macAddress", "00:1B:44:11:3A:EA"
                ).when()
                .get("/readings")
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().body().asString();
        assertEquals(TestData.SENSOR_DATA_JSON_01, actualResponseBody, true);
    }

    @Test
    void givenPersistedSensorReadings_whenGETbyMacAddressAndSensorName_thenShouldReturnSuccessfully() {
        var actualResponseBody = given()
                .accept("application/json")
                .queryParams(
                        "macAddress", "00:1B:44:11:3A:EA"
                        , "sensorName", "CCS811"
                ).when()
                .get("/readings")
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().body().asString();
        assertEquals(TestData.SENSOR_DATA_JSON_02, actualResponseBody, false);
    }

    @Test
    void givenPersistedSensorReadings_whenGETbyMacAddressAndSensorNameReadingType_thenShouldReturnSuccessfully() {
        var actualResponseBody = given()
                .accept("application/json")
                .queryParams(
                        "macAddress", "00:1B:44:11:3A:EA"
                        , "sensorName", "CCS811"
                        , "readingType", "TVOC"
                ).when()
                .get("/readings")
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().body().asString();
        assertEquals(SENSOR_DATA_JSON_03, actualResponseBody, false);
    }

    @Test
    void givenPersistedSensorReadings_whenGETbyPackageId_thenShouldReturnSuccessfully() {
        var actualResponseBody = given()
                .accept("application/json")
                .queryParams(
                        "packageId", "123e4567-e89b-12d3-a456-426614174003"
                ).when()
                .get("/readings")
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().body().asString();
        assertEquals(SENSOR_DATA_JSON_04, actualResponseBody, false);
    }
}
