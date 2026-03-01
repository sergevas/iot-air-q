package dev.sergevas.iot.control;

import dev.sergevas.iot.boundary.persistence.SensorDataRepository;
import dev.sergevas.iot.boundary.persistence.SensorNodeConfigRepository;
import dev.sergevas.iot.entity.vo.FilterVO;
import dev.sergevas.iot.entity.vo.ReadingType;
import dev.sergevas.iot.entity.vo.SensorName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Map;

import static dev.sergevas.iot.entity.vo.SensorProperties.MAC_ADDRESS;
import static dev.sergevas.iot.entity.vo.SensorProperties.READING_TYPE;
import static dev.sergevas.iot.entity.vo.SensorProperties.SENSOR_NAME;

@ApplicationScoped
public class FilterService {

    @Inject
    SensorNodeConfigRepository sensorNodeConfigRepository;

    @Inject
    SensorDataRepository sensorDataRepository;

    public FilterVO getFilter() {
        return new FilterVO(
                Map.of(MAC_ADDRESS, sensorNodeConfigRepository.getMacAddresses(),
                        SENSOR_NAME, sensorDataRepository.getSensorDataFilterProperties(SensorName.class),
                        READING_TYPE, sensorDataRepository.getSensorDataFilterProperties(ReadingType.class)
                ));
    }
}
