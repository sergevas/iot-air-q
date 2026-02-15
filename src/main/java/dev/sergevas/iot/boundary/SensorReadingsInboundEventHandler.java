package dev.sergevas.iot.boundary;

import dev.sergevas.iot.boundary.ws.SensorReadingsWebSocket;
import dev.sergevas.iot.entity.vo.SensorReadingsFetchedEvent;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;

@ApplicationScoped
public class SensorReadingsInboundEventHandler {

    @Inject
    SensorReadingsWebSocket sensorReadingsWebSocket;

    public void onSensorReadingsFetchedEvent(@ObservesAsync SensorReadingsFetchedEvent sensorReadingsFetchedEvent) {
        Log.infof("Enter onSensorReadingsFetchedEvent %s", sensorReadingsFetchedEvent);
        sensorReadingsWebSocket.onSensorReadingsFetched(sensorReadingsFetchedEvent);
    }
}
