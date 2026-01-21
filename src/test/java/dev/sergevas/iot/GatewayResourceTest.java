package dev.sergevas.iot;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class GatewayResourceTest {

    @Test
    void updateSensorNodeInfo() {
        given()
                .header("Content-Type", "application/json")
                .accept("application/json")
                .body("""
                        {"ip": "192.168.1.104", "macAddress": "00:1B:44:11:3A:B7"}
                        """)
                .when()
                .put("/gateway/sensor-node")
                .then()
                .statusCode(204)
                .body(emptyString());
    }

    @Test
    void getSensorNodeInfo() {
        given()
                .accept("application/json")
                .pathParam("macAddress", "00:1B:44:11:3A:B7")
                .when()
                .get("/gateway/sensor-node/{macAddress}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("ip", equalTo("192.168.1.104"),
                        "macAddress", equalTo("00:1B:44:11:3A:B7"));
    }
}
