package dev.sergevas.iot.boundary.rest.client;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SensorNodeClientProvider {

    @ConfigProperty(name = "sensor.node.endpoint.context.root")
    String sensorNodeEndpointContextRoot;

    
}
