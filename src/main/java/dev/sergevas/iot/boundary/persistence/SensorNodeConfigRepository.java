package dev.sergevas.iot.boundary.persistence;

import dev.sergevas.iot.entity.SensorNodeConfigEntity;
import dev.sergevas.iot.entity.vo.MacAddressVO;
import dev.sergevas.iot.entity.vo.PropertyValue;
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

    @Transactional
    public List<MacAddressVO> getMacAddresses() {
        var query = em.createQuery("select distinct new dev.sergevas.iot.entity.vo.MacAddressVO(c.macAddress) from SensorNodeConfigEntity c", MacAddressVO.class);
        return query.getResultList();
    }

    @Transactional
    public List<PropertyValue> getSensorPropertyValues(Class<PropertyValue> type, String fieldName) {
        switch (type) {
            case PropertyValue.MacAddress.class ->
                    em.createQuery("select distinct new dev.sergevas.iot.entity.vo.MacAddress(c.macAddress) from SensorNodeConfigEntity c", type);

        }
        var query =
        return query.getResultList();
    }
}
