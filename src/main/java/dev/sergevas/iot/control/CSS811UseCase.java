package dev.sergevas.iot.control;

import dev.sergevas.iot.IotAirQualityException;
import dev.sergevas.iot.boundary.persistence.SensorNodeConfigRepository;
import dev.sergevas.iot.entity.SensorNodeConfigEntity;
import dev.sergevas.iot.entity.model.Reading;
import dev.sergevas.iot.entity.model.SensorNodeInfo;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import static dev.sergevas.iot.entity.vo.SensorProperties.CCS811_BASELINE;

@ApplicationScoped
public class CSS811UseCase {

    @Inject
    SensorNodeConfigUseCase sensorNodeConfigUseCase;

    @Inject
    SensorNodeRestClientProvider sensorNodeRestClientProvider;

    @Inject
    SensorNodeConfigRepository sensorNodeConfigRepository;

    public SensorNodeInfo refreshBaseline(String macAddress) {
        Log.infof("Enter refreshBaseline() for macAddress=%s", macAddress);
        var ip = sensorNodeConfigUseCase.getSensorNodeIp(macAddress);
        var port = sensorNodeConfigUseCase.getSensorNodePort(macAddress);
        var sensor = sensorNodeRestClientProvider.getClient(ip, port).getCCS811Baseline();
        var baseline = sensor.getReadings().stream().findFirst()
                .filter(r -> "BASELINE".equals(r.getType()))
                .map(Reading::getData)
                .orElseThrow(() -> new IotAirQualityException("CCS811 baseline reading not found from sensor for macAddress=" + macAddress));
        Log.info("CCS811 baseline retrieved from sensor: " + baseline);
        sensorNodeConfigRepository.save(new SensorNodeConfigEntity(macAddress, CCS811_BASELINE, String.valueOf(baseline)));
        return sensorNodeConfigUseCase.getSensorNodeInfo(macAddress)
                .orElseThrow(() -> new IotAirQualityException("Sensor node info not found after baseline refresh for macAddress=" + macAddress));
    }

    public SensorNodeInfo getBaseline(String macAddress) {
        var ccs811PropValue = sensorNodeConfigRepository.findByMacAddressAndProperty(macAddress, CCS811_BASELINE)
                .map(SensorNodeConfigEntity::getPropValue)
                .orElseThrow(() -> new IotAirQualityException("Sensor node CCS811_BASELINE not found for macAddress=" + macAddress));
        var sensorNodeInfo = new SensorNodeInfo();
        sensorNodeInfo.setCss811Baseline(ccs811PropValue);
        return sensorNodeInfo;
    }
}
