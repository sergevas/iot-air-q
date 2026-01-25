package dev.sergevas.iot.control;

import dev.sergevas.iot.boundary.rest.model.SensorNodeInfo;
import dev.sergevas.iot.entity.SensorNodeConfigEntity;

public class SensorNodeInfoMapper {

    public static SensorNodeConfigEntity toSensorNodeInfoEntity(SensorNodeInfo sensorNodeInfo) {
        return new SensorNodeConfigEntity(sensorNodeInfo.getIp(), sensorNodeInfo.getMacAddress());
    }

    public static SensorNodeInfo toSensorNodeInfo(SensorNodeConfigEntity sensorNodeConfigEntity) {
        return new SensorNodeInfo(sensorNodeConfigEntity.getIp(), sensorNodeConfigEntity.getMacAddress());
    }
}
