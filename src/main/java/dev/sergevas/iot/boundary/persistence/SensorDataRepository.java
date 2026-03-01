package dev.sergevas.iot.boundary.persistence;

import dev.sergevas.iot.IotAirQualityException;
import dev.sergevas.iot.entity.SensorDataEntity;
import dev.sergevas.iot.entity.vo.FilterProperty;
import dev.sergevas.iot.entity.vo.MacAddress;
import dev.sergevas.iot.entity.vo.ReadingType;
import dev.sergevas.iot.entity.vo.SensorName;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static dev.sergevas.iot.entity.vo.SensorProperties.MAC_ADDRESS;
import static dev.sergevas.iot.entity.vo.SensorProperties.READING_TYPE;
import static dev.sergevas.iot.entity.vo.SensorProperties.SENSOR_NAME;

@ApplicationScoped
public class SensorDataRepository {

    private final static Map<Class<? extends FilterProperty>, String> filterPropertyMap = Map.ofEntries(
            Map.entry(SensorName.class, SENSOR_NAME),
            Map.entry(MacAddress.class, MAC_ADDRESS),
            Map.entry(ReadingType.class, READING_TYPE)
    );

    private static final String SEARCH_QUERY_BASE = "select s from SensorDataEntity s ";

    @Inject
    EntityManager em;

    @Transactional
    public void save(SensorDataEntity entity) {
        Log.infof("Saving sensor data: %s", entity);
        em.persist(entity);
    }

    @Transactional
    public List<SensorDataEntity> find(String macAddress, String sensorName, String readingType, UUID packageId) {
        var queryUtils = new QueryUtils(SEARCH_QUERY_BASE);
        queryUtils.appendWhereConditionNotNull("macAddress", macAddress, "s.macAddress = :macAddress");
        queryUtils.appendWhereConditionNotNull("sensorName", sensorName, "s.sensorName = :sensorName");
        queryUtils.appendWhereConditionNotNull("readingType", readingType, "s.readingType = :readingType");
        queryUtils.appendWhereConditionNotNull("packageId", packageId, "s.packageId = :packageId");
        var query = em.createQuery(queryUtils.buildQuery(), SensorDataEntity.class);
        queryUtils.setQueryParameters(query);
        return query.getResultList();
    }

    @Transactional
    public List<? extends FilterProperty> getSensorDataFilterProperties(Class<? extends FilterProperty> type) {
        if (!filterPropertyMap.containsKey(type)) {
            throw new IotAirQualityException("Unsupported filter property: " + type.getName());
        }
        return em.createQuery("select distinct new dev.sergevas.iot.entity.vo."
                + type.getSimpleName()
                + "(d." + filterPropertyMap.get(type) + ") from SensorDataEntity d", type).getResultList();
    }
}
