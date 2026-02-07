package dev.sergevas.iot.control;

import dev.sergevas.iot.boundary.rest.client.SensorNodeClient;
import dev.sergevas.iot.config.SensorNodeEndpoint;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@ApplicationScoped
public class SensorNodeRestClientProvider {

    private static final String SENSOR_NODE_URL_TEMPLATE = "http://%s:%s/%s/%s";

    @Inject
    SensorNodeEndpoint sensorNodeEndpoint;

    public SensorNodeClient getClient(String ip, String port) {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create(SENSOR_NODE_URL_TEMPLATE.formatted(ip, port, sensorNodeEndpoint.contextRoot(),
                        "ccs811/baseline")))
                .connectTimeout(sensorNodeEndpoint.connectTimeout(), MILLISECONDS)
                .readTimeout(sensorNodeEndpoint.readTimeout(), MILLISECONDS)
                .build(SensorNodeClient.class);
    }
}
