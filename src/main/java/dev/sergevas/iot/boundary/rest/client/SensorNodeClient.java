package dev.sergevas.iot.boundary.rest.client;

import dev.sergevas.iot.entity.model.Sensor;
import io.quarkus.rest.client.reactive.Url;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
public interface SensorNodeClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Sensor getCCS811Baseline(@Url String url);
}
