package dev.sergevas.iot.boundary.rest.client;

import dev.sergevas.iot.boundary.rest.model.Sensor;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public interface SensorNodeConfigClient {

    @Produces(MediaType.APPLICATION_JSON)
    Sensor getCCS811Baseline();
}
