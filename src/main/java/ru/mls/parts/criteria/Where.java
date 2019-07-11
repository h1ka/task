package ru.mls.parts.criteria;

import java.util.List;

public class Where {

    private final String sql;
    private final List<Object> values;

    public Where(String sql, List<Object> values) {
        this.sql = sql;
        this.values = values;
    }

    public String getSql() {
        return sql;
    }

    public List<Object> getValues() {
        return values;
    }

}
