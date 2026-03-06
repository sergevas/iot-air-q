package dev.sergevas.iot.entity.vo;

import java.util.List;
import java.util.Map;

public class FilterVO {

    private Map<String, List<? extends FilterProperty>> filters;

    public FilterVO(Map<String, List<? extends FilterProperty>> filters) {
        this.filters = filters;
    }

    public Map<String, List<? extends FilterProperty>> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, List<? extends FilterProperty>> filters) {
        this.filters = filters;
    }

    public List<? extends FilterProperty> getFilterProperties(String filterKey) {
        return filters.get(filterKey);
    }

    public List<String> getFilterPropertyValues(String filterKey) {
        List<? extends FilterProperty> properties = filters.get(filterKey);
        if (properties == null) {
            return List.of();
        }
        return properties.stream()
                .map(FilterProperty::value)
                .toList();
    }

    @Override
    public String toString() {
        return "FilterVO{" +
                "filters=" + filters +
                '}';
    }
}
