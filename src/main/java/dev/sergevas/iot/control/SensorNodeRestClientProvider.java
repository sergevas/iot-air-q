package dev.sergevas.iot.control;

import dev.sergevas.iot.boundary.rest.client.SensorNodeClient;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;

@ApplicationScoped
public class SensorNodeRestClientProvider {

    private static final String SENSOR_NODE_URL_TEMPLATE = "http://%s:%s/%s/%s";

    @ConfigProperty(name = "sensor.node.endpoint.context.root")
    String contextRoot;

    public SensorNodeClient getClient(String ip, String port) {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create(SENSOR_NODE_URL_TEMPLATE.formatted(ip, port, contextRoot, "ccs811/baseline")))
                .build(SensorNodeClient.class);
    }
}
