package dev.sergevas.iot.boundary.rest;

import dev.sergevas.iot.boundary.persistence.NodeInfoRepository;
import dev.sergevas.iot.boundary.rest.model.SensorNodeInfo;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static dev.sergevas.iot.control.SensorNodeInfoMapper.toSensorNodeInfo;
import static dev.sergevas.iot.control.SensorNodeInfoMapper.toSensorNodeInfoEntity;

@Path("/gateway")
public class GatewayResource {

    @Inject
    NodeInfoRepository nodeInfoRepository;

    @PUT
    @Path("/sensor-node")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSensorNodeInfo(SensorNodeInfo sensorNodeInfo) {
        Log.infof("Enter updateSensorNodeInfo(): %s", sensorNodeInfo);
        nodeInfoRepository.save(toSensorNodeInfoEntity(sensorNodeInfo));
        Log.info("Sensor node info updated successfully.");
        return Response.noContent().build();
    }

    @GET
    @Path("/sensor-node/{macAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensorNodeInfo(@PathParam("macAddress") String macAddress) {
        Log.infof("Enter getSensorNodeInfo(): macAddress=%s", macAddress);
        return nodeInfoRepository.findByMacAddress(macAddress)
                .map(entity -> Response.ok(toSensorNodeInfo(entity)).build())
                .orElseGet(() -> {
                    Log.warnf("Sensor node info not found for macAddress=%s", macAddress);
                    return Response.status(Response.Status.NOT_FOUND).build();
                });
    }
}
