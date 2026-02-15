package dev.sergevas.iot.entity.vo;

import java.util.List;
import java.util.Map;

public class FilterVO {

    private Map<String, List<String>> filters;

    public FilterVO(Map<String, List<String>> filters) {
        this.filters = filters;
    }

    public Map<String, List<String>> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, List<String>> filters) {
        this.filters = filters;
    }

    public List<String> getFilterValues(String filterKey) {
        return filters.get(filterKey);
    }

}
