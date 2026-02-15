package dev.sergevas.iot.boundary.rest.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

/**
 * Test suite for SensorReadings API and UI endpoints
 */
@QuarkusTest
public class SensorReadingsUIResourceTest {

    /**
     * Test API endpoint that returns JSON list of sensor readings with default pagination
     */
    @Test
    public void testGetSensorReadingsAPI() {
        given()
                .when()
                .get("/api/readings")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("readings", hasSize(greaterThan(0)))
                .body("totalRecords", greaterThan(0))
                .body("totalPages", greaterThan(0))
                .body("currentPage", equalTo(1))
                .body("pageSize", equalTo(10));
    }

    /**
     * Test API with custom page size parameter
     */
    @Test
    public void testGetSensorReadingsAPIWithPageSize() {
        given()
                .queryParam("pageSize", "5")
                .when()
                .get("/api/readings")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("pageSize", equalTo(5))
                .body("currentPage", equalTo(1));
    }

    /**
     * Test API with sort order parameter
     */
    @Test
    public void testGetSensorReadingsAPIWithSortOrder() {
        given()
                .queryParam("sortOrder", "ASC")
                .when()
                .get("/api/readings")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("currentPage", equalTo(1));
    }

    /**
     * Test API with filter by MAC address
     */
    @Test
    public void testGetSensorReadingsAPIWithMacAddressFilter() {
        given()
                .queryParam("macAddress", "00:1B:44:11:3A:EA")
                .when()
                .get("/api/readings")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("readings", hasSize(greaterThan(0)));
    }

    /**
     * Test API with filter by sensor name
     */
    @Test
    public void testGetSensorReadingsAPIWithSensorNameFilter() {
        given()
                .queryParam("sensorName", "CCS811")
                .when()
                .get("/api/readings")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("readings", hasSize(greaterThan(0)));
    }

    /**
     * Test API with filter by reading type
     */
    @Test
    public void testGetSensorReadingsAPIWithReadingTypeFilter() {
        given()
                .queryParam("readingType", "CO2")
                .when()
                .get("/api/readings")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("readings", hasSize(greaterThan(0)));
    }

    /**
     * Test API with multiple filter combinations
     */
    @Test
    public void testGetSensorReadingsAPIWithMultipleFilters() {
        given()
                .queryParam("macAddress", "00:1B:44:11:3A:EA")
                .queryParam("sensorName", "CCS811")
                .queryParam("readingType", "CO2")
                .when()
                .get("/api/readings")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("readings", hasSize(greaterThan(0)));
    }

    /**
     * Test API error handling with invalid page size (should accept and use it)
     */
    @Test
    public void testGetSensorReadingsAPIWithInvalidPageSize() {
        given()
                .queryParam("pageSize", "200")
                .when()
                .get("/api/readings")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    /**
     * Test API error handling with negative page number (should default to 1)
     */
    @Test
    public void testGetSensorReadingsAPIWithNegativePageNumber() {
        given()
                .queryParam("pageNumber", "-1")
                .when()
                .get("/api/readings")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("currentPage", equalTo(1));
    }

    /**
     * Test API pagination - page 2
     */
    @Test
    public void testGetSensorReadingsAPIPage2() {
        given()
                .queryParam("pageSize", "5")
                .queryParam("pageNumber", "2")
                .when()
                .get("/api/readings")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("currentPage", equalTo(2));
    }

    /**
     * Test API with descending sort order
     */
    @Test
    public void testGetSensorReadingsAPIWithDescSortOrder() {
        given()
                .queryParam("pageSize", "5")
                .queryParam("sortOrder", "DESC")
                .when()
                .get("/api/readings")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("pageSize", equalTo(5))
                .body("currentPage", equalTo(1));
    }

    /**
     * Test API returns correct total pages calculation
     */
    @Test
    public void testGetSensorReadingsAPIPagesCalculation() {
        given()
                .queryParam("pageSize", "3")
                .when()
                .get("/api/readings")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("pageSize", equalTo(3))
                .body("totalPages", greaterThan(0));
    }
}

