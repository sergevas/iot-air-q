package dev.sergevas.iot.boundary;

import dev.sergevas.iot.entity.vo.SensorReadingsFetchedEvent;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;

@ApplicationScoped
public class SensorReadingsInboundEventHandler {

    public void onSensorReadingsFetchedEvent(@ObservesAsync SensorReadingsFetchedEvent sensorReadingsFetchedEvent) {
        Log.infof("Enter onSensorReadingsFetchedEvent %s", sensorReadingsFetchedEvent);
    }
}
