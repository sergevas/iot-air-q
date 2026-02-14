package dev.sergevas.iot.boundary.persistence;

import dev.sergevas.iot.entity.SensorDataEntity;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SensorDataRepository {

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
}
