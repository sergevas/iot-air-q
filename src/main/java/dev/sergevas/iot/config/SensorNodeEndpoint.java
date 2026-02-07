package dev.sergevas.iot.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "sensor.node.endpoint")
public interface SensorNodeEndpoint {

    String contextRoot();

    Long connectTimeout();

    Long readTimeout();
}
