package dev.sergevas.iot.boundary.persistence;

import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class QueryUtils {

    private static final String AND_CLAUSE = ") and (";
    private static final String WHERE_CLAUSE = " where (";

    private final Map<String, Object> params = new HashMap<>();
    private final List<String> whereConditions = new ArrayList<>();
    private final List<String> orderByAsc = new ArrayList<>();
    private String baseQuery;

    public QueryUtils(String baseQuery) {
        this.baseQuery = baseQuery;
    }

    public QueryUtils() {
    }

    /**
     * Common method to append where condition for a query.
     *
     * @param paramName           - a query parameter name.
     * @param searchCriteriaParam - a query parameter value.
     * @param whereCondition      - a query condition string.
     */
    public void appendWhereCondition(String paramName, Object searchCriteriaParam, String whereCondition) {
        params.put(paramName, searchCriteriaParam);
        whereConditions.add(whereCondition);
    }

    /**
     * Method to append where condition for Object query parameter type.
     */
    public void appendWhereConditionNotNull(String paramName, Object searchCriteriaParam, String whereCondition) {
        if (searchCriteriaParam != null) {
            appendWhereCondition(paramName, searchCriteriaParam, whereCondition);
        }
    }

    /**
     * Method to append where condition for String query parameter type.
     */
    public void appendWhereConditionNotBlank(String paramName, String searchCriteriaParam, String whereCondition) {
        if (!Objects.isNull(searchCriteriaParam) && !searchCriteriaParam.isBlank()) {
            appendWhereCondition(paramName, searchCriteriaParam.trim(), whereCondition);
        }
    }

    /**
     * Method to append where condition for Collection query parameter type.
     */
    public <T> void appendWhereConditionNotEmpty(String paramName, Collection<T> searchCriteriaParam, String whereCondition) {
        if (searchCriteriaParam != null && !searchCriteriaParam.isEmpty()) {
            appendWhereCondition(paramName, searchCriteriaParam, whereCondition);
        }
    }

    public <T> void appendCollectionWhereConditionJpa1NotEmpty(String paramName, Collection<T> searchCriteriaParams) {
        if (searchCriteriaParams != null && !searchCriteriaParams.isEmpty()) {
            Iterator<T> parIt = searchCriteriaParams.iterator();
            StringBuilder sb = new StringBuilder(paramName);
            sb.append(" (");
            while (parIt.hasNext()) {
                T p = parIt.next();
                if (p instanceof String) {
                    sb.append("'").append(p).append("'");
                } else if (p instanceof Number) {
                    sb.append(p);
                }
                if (parIt.hasNext()) {
                    sb.append(",");
                }
            }
            sb.append(")");
            this.whereConditions.add(sb.toString());
        }
    }

    public void addWhereCondition(String whereCondition) {
        this.whereConditions.add(whereCondition);
    }

    public void addOrderByAscCondition(String orderByAsc) {
        this.orderByAsc.add(orderByAsc);
    }

    /**
     * Sets a query parameters.
     */
    public void setQueryParameters(Query query) {
        for (String paramName : params.keySet()) {
            query.setParameter(paramName, params.get(paramName));
        }
    }

    /**
     * Gets where part of a query.
     */
    public String getWhereQueryPart() {
        if (this.whereConditions.isEmpty()) {
            return "";
        }
        return WHERE_CLAUSE.concat(String.join(AND_CLAUSE, this.whereConditions)).concat(") ");
    }

    public String getOrderByAscQueryPart() {
        return orderByAsc.isEmpty() ? "" : orderByAsc.stream().collect(Collectors.joining(" asc,", " order by ", " asc"));
    }

    public String getConditionalPart() {
        return String.join(" ", getWhereQueryPart(), getOrderByAscQueryPart());
    }

    public String buildQuery() {
        return baseQuery + getConditionalPart();
    }
}
