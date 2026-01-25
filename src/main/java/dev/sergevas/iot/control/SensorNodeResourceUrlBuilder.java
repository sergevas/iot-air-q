package dev.sergevas.iot.control;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SensorNodeResourceUrlBuilder {

    private static final String SENSOR_NODE_URL_TEMPLATE = "http://%s/%s/%s";

    @ConfigProperty(name = "sensor.node.endpoint.context.root")
    String contextRoot;

    public String buildCCS811BaselineUrl(String ip) {
        return SENSOR_NODE_URL_TEMPLATE.formatted(ip, contextRoot, "ccs811/baseline");
    }
}
