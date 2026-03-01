package dev.sergevas.iot.boundary.persistence;

import dev.sergevas.iot.entity.SensorNodeConfigEntity;
import dev.sergevas.iot.entity.vo.MacAddress;
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
    public void save(final SensorNodeConfigEntity entity) {
        findByMacAddressAndProperty(entity.getMacAddress(), entity.getPropName())
                .ifPresentOrElse(existingEntity -> existingEntity.setPropValue(entity.getPropValue()),
                        () -> em.persist(entity));
    }

    @Transactional
    public List<MacAddress> getMacAddresses() {
        var query = em.createQuery("select distinct new dev.sergevas.iot.entity.vo.MacAddress(c.macAddress) from SensorNodeConfigEntity c", MacAddress.class);
        return query.getResultList();
    }

    @Transactional
    public Optional<SensorNodeConfigEntity> findByMacAddressAndProperty(final String macAddress, final String propName) {
        return em.createNamedQuery("SensorNodeConfigEntity.findByMacAddressAndPropName", SensorNodeConfigEntity.class)
                .setParameter("macAddress", macAddress)
                .setParameter("propName", propName)
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public List<SensorNodeConfigEntity> findByMacAddress(final String macAddress) {
        return em.createNamedQuery("SensorNodeConfigEntity.findByMacAddress", SensorNodeConfigEntity.class)
                .setParameter("macAddress", macAddress)
                .getResultStream()
                .toList();
    }
}
