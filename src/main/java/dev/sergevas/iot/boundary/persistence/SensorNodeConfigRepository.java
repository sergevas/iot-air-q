package dev.sergevas.iot.boundary.persistence;

import dev.sergevas.iot.IotAirQualityException;
import dev.sergevas.iot.entity.SensorNodeConfigEntity;
import dev.sergevas.iot.entity.vo.PropertyValue;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static dev.sergevas.iot.entity.vo.SensorProperties.MAC_ADDRESS;
import static dev.sergevas.iot.entity.vo.SensorProperties.PACKAGE_ID;
import static dev.sergevas.iot.entity.vo.SensorProperties.READING_TYPE;
import static dev.sergevas.iot.entity.vo.SensorProperties.SENSOR_NAME;

@ApplicationScoped
public class SensorNodeConfigRepository {

    private final static Map<Class<? extends PropertyValue>, String> propertyMap = Map.ofEntries(
            Map.entry(PropertyValue.SensorName.class, SENSOR_NAME),
            Map.entry(PropertyValue.MacAddress.class, MAC_ADDRESS),
            Map.entry(PropertyValue.ReadingType.class, READING_TYPE),
            Map.entry(PropertyValue.PackageId.class, PACKAGE_ID)
    );

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
    public List<? extends PropertyValue> getSensorPropertyValues(Class<? extends PropertyValue> type) {
        if (!propertyMap.containsKey(type)) {
            throw new IotAirQualityException("Unsupported property value type: " + type.getName());
        }
        return em.createQuery(
                        "select distinct new dev.sergevas.iot.entity.vo.PropertyValue."
                                + type.getSimpleName()
                                + "(c.propValue) from SensorNodeConfigEntity c where c.propName = :propName", type)
                .setParameter("propName", propertyMap.get(type))
                .getResultList();
    }
}
