package dev.sergevas.iot.boundary.rest;

import dev.sergevas.iot.boundary.rest.model.Reading;
import dev.sergevas.iot.boundary.rest.model.Sensor;
import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

@Path("/control")
public class SensorNodeControlResource {

    @GET
    @Path("/{macAddress}/ccs811/baseline")
    public Sensor updateCCS811Baseline(@PathParam("macAddress") String macAddress) {
        Log.infof("Enter updateCCS811Baseline(): macAddress=%s", macAddress);
        // TODO: implement
        var sensor = new Sensor();
        sensor.setName("CCS811");
        sensor.setReadings(List.of(new Reading("BASELINE", 567)));
        Log.infof("Exit updateCCS811Baseline(): %s", sensor);
        return sensor;
    }
}
