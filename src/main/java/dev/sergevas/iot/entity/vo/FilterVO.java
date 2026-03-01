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

    public List<? extends FilterProperty> getFilterValues(String filterKey) {
        return filters.get(filterKey);
    }

    @Override
    public String toString() {
        return "FilterVO{" +
                "filters=" + filters +
                '}';
    }
}
