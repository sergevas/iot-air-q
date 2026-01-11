package dev.sergevas.iot;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "iot-air-q", mixinStandardHelpOptions = true)
public class IotAirQCommand implements Runnable {

    @Parameters(paramLabel = "period", defaultValue = "5",
        description = "Sensor polling period in seconds")
    String period;

    @Override
    public void run() {
        System.out.printf("Hello %s, go go commando!%n", period);
    }

}
