package dev.sergevas.iot.boundary.persistence;

import dev.sergevas.iot.entity.SensorNodeConfigEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SensorNodeConfigRepository {

    @Inject
    EntityManager em;

    @Transactional
    public void save(SensorNodeConfigEntity entity) {
        findByMacAddressAndProperty(entity.getMacAddress(), entity.getPropValue())
                .ifPresentOrElse(existingEntity -> existingEntity.setPropValue(entity.getPropValue()),
                        () -> em.persist(entity));
    }

    @Transactional
    public Optional<SensorNodeConfigEntity> findByMacAddressAndProperty(String macAddress, String propName) {
        return em.createNamedQuery("SensorNodeConfigEntity.findByMacAddressAndPropName", SensorNodeConfigEntity.class)
                .setParameter("macAddress", macAddress)
                .setParameter("propName", propName)
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public List<SensorNodeConfigEntity> findByMacAddress(String macAddress) {
        return em.createNamedQuery("SensorNodeConfigEntity.findByMacAddress", SensorNodeConfigEntity.class)
                .setParameter("macAddress", macAddress)
                .getResultStream()
                .toList();
    }
}
