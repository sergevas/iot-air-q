package dev.sergevas.iot;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;

@QuarkusTest
class GreetingResourceTest {

    @Test
    void updateSensorNodeInfo() {
        given()
                .header("Content-Type", "application/json")
                .accept("application/json")
                .body("""
                        {"ip": "192.160.1.104", "macAddress": "00:1B:44:11:3A:B7"}
                        """)
                .when()
                .put("/gateway/sensor-node")
                .then()
                .statusCode(204)
                .body(emptyString());
    }
}
