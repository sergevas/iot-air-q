package dev.sergevas.iot.boundary.rest.api;

import dev.sergevas.iot.boundary.persistence.NodeInfoRepository;
import dev.sergevas.iot.boundary.rest.model.Reading;
import dev.sergevas.iot.boundary.rest.model.Sensor;
import dev.sergevas.iot.boundary.rest.model.SensorNodeInfo;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

import static dev.sergevas.iot.control.SensorNodeInfoMapper.toSensorNodeInfo;
import static dev.sergevas.iot.control.SensorNodeInfoMapper.toSensorNodeInfoEntity;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.WILDCARD;

@Path("config")
public class SensorNodeConfigResource {

    @Inject
    NodeInfoRepository nodeInfoRepository;

    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response updateSensorNodeInfo(SensorNodeInfo sensorNodeInfo) {
        Log.infof("Enter updateSensorNodeInfo(): %s", sensorNodeInfo);
        nodeInfoRepository.save(toSensorNodeInfoEntity(sensorNodeInfo));
        Log.info("Sensor node info updated successfully.");
        return Response.noContent().build();
    }

    @GET
    @Path("{macAddress}")
    @Produces(APPLICATION_JSON)
    public Response getSensorNodeInfo(@PathParam("macAddress") String macAddress) {
        Log.infof("Enter getSensorNodeInfo(): macAddress=%s", macAddress);
        return nodeInfoRepository.findByMacAddress(macAddress)
                .map(entity -> Response.ok(toSensorNodeInfo(entity)).build())
                .orElseGet(() -> {
                    Log.warnf("Sensor node info not found for macAddress=%s", macAddress);
                    return Response.status(Response.Status.NOT_FOUND).build();
                });
    }

    @GET
    @Path("{macAddress}/ccs811/baseline")
    @Consumes(WILDCARD)
    @Produces(APPLICATION_JSON)
    public Sensor getCCS811Baseline(@PathParam("macAddress") String macAddress,
                                    @QueryParam("update") @DefaultValue("false") boolean update) {
        Log.infof("Enter getCCS811Baseline(): macAddress=%s, update=%b", macAddress, update);
        // TODO: implement
        var sensor = new Sensor();
        sensor.setName("CCS811");
        sensor.setReadings(List.of(new Reading("BASELINE", 567)));
        Log.infof("Exit updateCCS811Baseline(): %s", sensor);
        return sensor;
    }

    @POST
    @Path("{macAddress}/ccs811/baseline")
    public Sensor initCCS811Baseline(@PathParam("macAddress") String macAddress) {
        Log.infof("Enter initCCS811Baseline(): macAddress=%s", macAddress);
        // TODO: implement
        var sensor = new Sensor();
        sensor.setName("CCS811");
        sensor.setReadings(List.of(new Reading("BASELINE", 567)));
        Log.infof("Exit updateCCS811Baseline(): %s", sensor);
        return sensor;
    }
}
