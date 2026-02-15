package dev.sergevas.iot.boundary.ws;

import dev.sergevas.iot.entity.vo.SensorReadingsFetchedEvent;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ApplicationScoped
@ServerEndpoint("/ws/sensor-readings")
public class SensorReadingsWebSocket {

    static Set<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        Log.infof("WebSocket session opened: %s", session.getId());
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        Log.infof("WebSocket session closed: %s", session.getId());
        sessions.remove(session);
    }

    public void onSensorReadingsFetched(@ObservesAsync SensorReadingsFetchedEvent event) {
        Log.infof("Broadcasting sensor reading fetched event to %d clients", sessions.size());
        String message = "REFRESH";
        sessions.forEach(session -> {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    Log.errorf("Error sending WebSocket message: %s", e.getMessage());
                    sessions.remove(session);
                }
            }
        });
    }
}

