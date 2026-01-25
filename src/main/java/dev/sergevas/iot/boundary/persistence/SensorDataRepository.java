package dev.sergevas.iot.boundary.persistence;

import dev.sergevas.iot.entity.SensorDataEntity;
import dev.sergevas.iot.entity.SensorNodeConfigEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class SensorDataRepository {

    @Inject
    EntityManager em;

    @Transactional
    public void save(SensorDataEntity entity) {
        em.persist(entity);
    }

    @Transactional
    public Optional<SensorNodeConfigEntity> findByMacAddress(String macAddress) {
        return em.createNamedQuery("SensorNodeInfoEntity.findByMacAddress", SensorNodeConfigEntity.class)
                .setParameter("macAddress", macAddress)
                .getResultStream()
                .findFirst();
    }
}
