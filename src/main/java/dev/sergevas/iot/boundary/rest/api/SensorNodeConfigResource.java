package dev.sergevas.iot.boundary.rest.api;

import dev.sergevas.iot.control.CSS811UseCase;
import dev.sergevas.iot.control.SensorNodeConfigUseCase;
import dev.sergevas.iot.entity.model.SensorNodeInfo;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("config")
public class SensorNodeConfigResource {

    @Inject
    SensorNodeConfigUseCase sensorNodeConfigUseCase;

    @Inject
    CSS811UseCase css811UseCase;

    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response updateSensorNodeInfo(SensorNodeInfo sensorNodeInfo) {
        Log.infof("Enter updateSensorNodeInfo(): %s", sensorNodeInfo);
        sensorNodeConfigUseCase.saveSensorNodeInfo(sensorNodeInfo);
        Log.info("Sensor node info updated successfully.");
        return Response.noContent().build();
    }

    @GET
    @Path("{macAddress}")
    @Produces(APPLICATION_JSON)
    public Response getSensorNodeInfo(@PathParam("macAddress") String macAddress) {
        Log.infof("Enter getSensorNodeInfo(): macAddress=%s", macAddress);
        return sensorNodeConfigUseCase.getSensorNodeInfo(macAddress)
                .map(sni -> Response.ok(sni).build())
                .orElseGet(() -> {
                    Log.warnf("Sensor node info not found for macAddress=%s", macAddress);
                    return Response.status(Response.Status.NOT_FOUND).build();
                });
    }

    @POST
    @Path("{macAddress}/ccs811/baseline")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public SensorNodeInfo refreshBaseline(@PathParam("macAddress") String macAddress) {
        Log.infof("Enter refreshBaseline(): macAddress=%s", macAddress);
        var sensorNodeInfo = css811UseCase.refreshBaseline(macAddress);
        Log.infof("CCS811 baseline refreshed successfully for macAddress=%s", macAddress);
        return sensorNodeInfo;
    }
}
