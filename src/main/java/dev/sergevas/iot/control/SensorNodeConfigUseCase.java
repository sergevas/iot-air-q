package dev.sergevas.iot.control;

import dev.sergevas.iot.IotAirQualityException;
import dev.sergevas.iot.boundary.persistence.SensorNodeConfigRepository;
import dev.sergevas.iot.entity.SensorNodeConfigEntity;
import dev.sergevas.iot.entity.model.SensorNodeInfo;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

import static dev.sergevas.iot.entity.vo.SensorProperties.CCS811_BASELINE;
import static dev.sergevas.iot.entity.vo.SensorProperties.IP;
import static dev.sergevas.iot.entity.vo.SensorProperties.PORT;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@ApplicationScoped
public class SensorNodeConfigUseCase {

    @Inject
    SensorNodeConfigRepository sensorNodeConfigRepository;

    public void saveSensorNodeInfo(SensorNodeInfo sensorNodeInfo) {
        Log.info("Enter saveSensorNodeInfo(): " + sensorNodeInfo);
        ofNullable(sensorNodeInfo.getIp()).ifPresent(ip ->
                sensorNodeConfigRepository.save(new SensorNodeConfigEntity(sensorNodeInfo.getMacAddress(), IP, ip)));
        ofNullable(sensorNodeInfo.getPort()).ifPresent(port ->
                sensorNodeConfigRepository.save(new SensorNodeConfigEntity(sensorNodeInfo.getMacAddress(), PORT, port)));
        ofNullable(sensorNodeInfo.getCss811Baseline()).ifPresent(bl ->
                sensorNodeConfigRepository.save(new SensorNodeConfigEntity(sensorNodeInfo.getMacAddress(), CCS811_BASELINE, bl)));
        Log.info("Sensor node info saved successfully");
    }

    public Optional<SensorNodeInfo> getSensorNodeInfo(String macAddress) {
        Log.info("Enter getSensorNodeInfo(): macAddress=" + macAddress);
        final var sensorNodeInfo = new SensorNodeInfo();
        sensorNodeInfo.setMacAddress(macAddress);
        sensorNodeConfigRepository.findByMacAddress(macAddress)
                .forEach(entity -> {
                    if (IP.equals(entity.getPropName())) {
                        sensorNodeInfo.setIp(entity.getPropValue());
                    }
                    if (PORT.equals(entity.getPropName())) {
                        sensorNodeInfo.setPort(entity.getPropValue());
                    }
                    if (CCS811_BASELINE.equals(entity.getPropName())) {
                        sensorNodeInfo.setCss811Baseline(entity.getPropValue());
                    }
                });
        return of(sensorNodeInfo).filter(sni -> !sni.isNew());
    }

    public String getSensorNodeIp(String macAddress) {
        return sensorNodeConfigRepository.findByMacAddressAndProperty(macAddress, IP)
                .map(SensorNodeConfigEntity::getPropValue)
                .orElseThrow(() -> new IotAirQualityException("Sensor node IP not found for macAddress=" + macAddress));
    }

    public String getSensorNodePort(String macAddress) {
        return sensorNodeConfigRepository.findByMacAddressAndProperty(macAddress, PORT)
                .map(SensorNodeConfigEntity::getPropValue)
                .orElseThrow(() -> new IotAirQualityException("Sensor node PORT not found for macAddress=" + macAddress));
    }
}
