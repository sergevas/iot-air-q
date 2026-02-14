package dev.sergevas.iot.boundary.init;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StartupService {

    @Startup
    public void startup() {
        Log.info("Application started successfully. Performing startup tasks...");

        Log.info("Startup tasks completed.");
    }
}
