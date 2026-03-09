package dev.sergevas.iot.boundary.rest.client;

import dev.sergevas.iot.entity.model.CCS811Baseline;
import dev.sergevas.iot.entity.model.Sensor;
import dev.sergevas.iot.entity.model.SensorData;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
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

    @PUT
    @Path("ccs811/baseline")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    CCS811Baseline updateCCS811Baseline(CCS811Baseline ccs811Baseline);

    @GET
    @Path("/readings")
    @Produces(MediaType.APPLICATION_JSON)
    SensorData getSensorData();
}
