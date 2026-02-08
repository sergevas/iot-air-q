package dev.sergevas.iot.boundary.rest.client;

import dev.sergevas.iot.entity.model.Sensor;
import dev.sergevas.iot.entity.model.SensorData;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
public interface SensorNodeClient {

    @GET
    @Path("ccs811/baseline")
    @Produces(MediaType.APPLICATION_JSON)
    Sensor getCCS811Baseline();

    @GET
    @Path("/readings")
    @Produces(MediaType.APPLICATION_JSON)
    SensorData getSensorData();
}
