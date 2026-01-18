package dev.sergevas.iot.boundary.rest;

import dev.sergevas.iot.boundary.rest.model.SensorNodeInfo;
import io.quarkus.logging.Log;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/gateway")
public class GatewayResource {

    @PUT
    @Path("/sensor-node")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSensorNodeInfo(SensorNodeInfo sensorNodeInfo) {
        Log.infof("Enter updateSensorNodeInfo(): %s", sensorNodeInfo);
        Log.info("Sensor node info updated successfully.");
        return Response.noContent().build();
    }
}
