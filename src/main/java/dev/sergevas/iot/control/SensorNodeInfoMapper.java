package dev.sergevas.iot.control;

import dev.sergevas.iot.boundary.rest.model.SensorNodeInfo;
import dev.sergevas.iot.entity.SensorNodeInfoEntity;

public class SensorNodeInfoMapper {

    public static SensorNodeInfoEntity toSensorNodeInfoEntity(SensorNodeInfo sensorNodeInfo) {
        return new SensorNodeInfoEntity(sensorNodeInfo.getIp(), sensorNodeInfo.getMacAddress());
    }

    public static SensorNodeInfo toSensorNodeInfo(SensorNodeInfoEntity sensorNodeInfoEntity) {
        return new SensorNodeInfo(sensorNodeInfoEntity.getIp(), sensorNodeInfoEntity.getMacAddress());
    }
}
