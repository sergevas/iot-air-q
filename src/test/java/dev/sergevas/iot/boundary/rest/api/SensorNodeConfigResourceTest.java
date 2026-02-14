package dev.sergevas.iot.boundary.rest.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@QuarkusTest
class SensorNodeConfigResourceTest {

    @Test
    void givenSensorNodeInfo_whenPUT_thenShouldUpdateSuccessfully() {
        given()
                .header("Content-Type", "application/json")
                .accept("application/json")
                .body("""
                        {"ip": "192.168.1.105", "macAddress": "00:1B:44:11:3A:B8", "port": 9881}
                        """)
                .when()
                .put("/config")
                .then()
                .statusCode(204)
                .body(emptyString());
    }

    @Test
    void givenExistedSensorNodeInfo_whenGET_thenShouldReturnSuccessfully() {
        assertEquals("""
                {
                    "css811Baseline": 567.0,
                    "ip": "localhost",
                    "macAddress": "00:1B:44:11:3A:B7",
                    "port": 9881
                }""", given()
                .accept("application/json")
                .pathParam("macAddress", "00:1B:44:11:3A:B7")
                .when()
                .get("/config/{macAddress}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .body().asString(), false);
    }

    @Test
    void givenNotExistedSensorNodeInfo_whenGET_thenShouldReturnNotFound() {
        given()
                .accept("application/json")
                .pathParam("macAddress", "00:00:00:00:00:00")
                .when()
                .get("/config/{macAddress}")
                .then()
                .statusCode(404)
                .body(emptyString());
    }

    @Test
    void givenDeployedSensorNode_whenRefreshBaseline_thenShouldReturnSuccessfully() {
        assertEquals("""
                {
                    "css811Baseline": 567.0,
                    "ip": "localhost",
                    "macAddress": "00:1B:44:11:3A:B7",
                    "port": 9881
                }""", given()
                .header("Content-Type", "application/json")
                .accept("application/json")
                .pathParam("macAddress", "00:1B:44:11:3A:B7")
                .body("{}")
                .when()
                .post("/config/{macAddress}/ccs811/baseline")
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .body().asString(), false);
    }
}
