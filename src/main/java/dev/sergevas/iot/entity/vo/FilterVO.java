package dev.sergevas.iot.entity.vo;

import java.util.List;
import java.util.Map;

public class FilterVO {

    private Map<String, List<PropertyValue>> filters;

    public FilterVO(Map<String, List<PropertyValue>> filters) {
        this.filters = filters;
    }

    public Map<String, List<PropertyValue>> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, List<PropertyValue>> filters) {
        this.filters = filters;
    }

    public List<PropertyValue> getFilterValues(String filterKey) {
        return filters.get(filterKey);
    }

}
