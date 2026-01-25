package dev.sergevas.iot.boundary.persistence;

import dev.sergevas.iot.entity.SensorDataEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class SensorDataRepository {

    @Inject
    EntityManager em;

    @Transactional
    public void save(SensorDataEntity entity) {
        em.persist(entity);
    }

    @Transactional
    public List<SensorDataEntity> findByMacAddress(String macAddress) {
        return em.createNamedQuery("SensorDataEntity.findByMacAddress", SensorDataEntity.class)
                .setParameter("macAddress", macAddress)
                .getResultStream()
                .toList();
    }
}
