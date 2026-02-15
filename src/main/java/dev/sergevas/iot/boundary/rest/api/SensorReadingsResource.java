package dev.sergevas.iot.boundary.rest.api;

import dev.sergevas.iot.boundary.persistence.SensorDataRepository;
import dev.sergevas.iot.control.Mapper;
import dev.sergevas.iot.entity.model.SensorReadings;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("readings")
public class SensorReadingsResource {

    @Inject
    SensorDataRepository sensorDataRepository;

    @GET
    @Produces(APPLICATION_JSON)
    public List<SensorReadings> getSensorNodeInfo(
            @QueryParam("macAddress") String macAddress,
            @QueryParam("sensorName") String sensorName,
            @QueryParam("readingType") String readingType,
            @QueryParam("packageId") UUID packageId) {
        Log.infof("Enter getSensorNodeInfo(): macAddress=%s, sensorName=%s, readingType=%s, packageId=%s",
                macAddress, sensorName, readingType, packageId);
        var sensorDataEntities = sensorDataRepository.find(macAddress, sensorName, readingType, packageId);
        Log.infof("Found %d sensor data records", sensorDataEntities.size());
        return sensorDataEntities.stream()
                .map(Mapper::toSensorReadings)
                .toList();
    }
}
