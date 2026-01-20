package dev.sergevas.iot.boundary.persistence;

import dev.sergevas.iot.entity.SensorNodeInfoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class NodeInfoRepository {

    @Inject
    EntityManager em;

    @Transactional
    public void save(SensorNodeInfoEntity entity) {
        findByMacAddress(entity.getMacAddress())
                .ifPresentOrElse(existingEntity -> existingEntity.setIp(entity.getIp()),
                        () -> em.persist(entity));
    }

    @Transactional
    public Optional<SensorNodeInfoEntity> findByMacAddress(String macAddress) {
        return em.createNamedQuery("SensorNodeInfoEntity.findByMacAddress", SensorNodeInfoEntity.class)
                .setParameter("macAddress", macAddress)
                .getResultStream()
                .findFirst();
    }
}
