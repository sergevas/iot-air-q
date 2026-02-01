package dev.sergevas.iot;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class SensorNodeConfigResourceTest {

    @Test
    void givenSensorNodeInfo_whenPUT_thenShouldUpdateSuccessfully() {
        given()
                .header("Content-Type", "application/json")
                .accept("application/json")
                .body("""
                        {"ip": "192.168.1.105", "macAddress": "00:1B:44:11:3A:B8"}
                        """)
                .when()
                .put("/config")
                .then()
                .statusCode(204)
                .body(emptyString());
    }

    @Test
    void givenExistedSensorNodeInfo_whenGET_thenShouldReturnSuccessfully() {
        given()
                .accept("application/json")
                .pathParam("macAddress", "00:1B:44:11:3A:B7")
                .when()
                .get("/config/{macAddress}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("macAddress", equalTo("00:1B:44:11:3A:B7"),
                        "ip", equalTo("localhost"),
                        "port", equalTo("9881"),
                        "css811Baseline", equalTo("567"));
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
        given()
                .accept("application/json")
                .pathParam("macAddress", "00:1B:44:11:3A:B7")
                .when()
                .get("/config/{macAddress}/ccs811/baseline")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("macAddress", equalTo("00:1B:44:11:3A:B7"),
                        "ip", equalTo("localhost"),
                        "css811Baseline", equalTo("567"));
    }
}
