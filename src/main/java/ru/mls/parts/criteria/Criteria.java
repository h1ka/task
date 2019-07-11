package ru.mls.parts.criteria;


import ru.mls.parts.criteria.util.CollectionUtils;
import ru.mls.parts.criteria.util.NameValuePair;
import ru.mls.parts.model.BetweenDates;

import java.util.ArrayList;
import java.util.List;

public class Criteria {

    private List<NameValuePair<String>> eqs = null;
    private List<NameValuePair<Order>> orderBys = null;
    private List<NameValuePair<Integer>> intWhere = null;
    private List<NameValuePair<BetweenDates>> betweenWhere = null;

    /**
     * Add an "ORDER BY" clause for the field with an ascending order.
     *
     * @param field The field to sort by.
     * @return Criteria instance the method was invoked on (for chaining).
     */
    public Criteria asc(final String field) {
        return orderBy(field, Order.ASC);
    }

    /**
     * Add an "ORDER BY" clause for the field with an ascending order.
     *
     * @param field The field to sort by.
     * @return Criteria instance the method was invoked on (for chaining).
     */
    public Criteria desc(final String field) {
        return orderBy(field, Order.DESC);
    }

    /**
     * A field match.
     *
     * @param field The field to match
     * @param value The value the field must be.
     * @return Criteria instance the method was invoked on (for chaining).
     */
    public Criteria eq(final String field, Integer value) {
        if (this.intWhere == null) {
            this.intWhere = new ArrayList<>();
        }
        this.intWhere.add(new NameValuePair<>(field, value));
        return this;
    }

    public Criteria eq(final String field, BetweenDates value) {
        if (this.betweenWhere == null) {
            this.betweenWhere = new ArrayList<>();
        }
        this.betweenWhere.add(new NameValuePair<>(field, value));
        return this;
    }


    public Criteria eq(final String field, String value) {
        if (this.eqs == null) {
            this.eqs = new ArrayList<>();
        }
        this.eqs.add(new NameValuePair<>(field, value));
        return this;
    }

    public Criteria eq(final String field, final Object value) {
        if (value instanceof BetweenDates) {
            return eq(field, (BetweenDates) value);
        }
        if (value instanceof Integer) {
            return eq(field, (Integer) value);
        }
        if (value == null) {
            return eq(field, (String) null);
        }
        return eq(field, value.toString());
    }


    public String getOrderBySql() {
        if (CollectionUtils.isEmpty(this.orderBys)) {
            return " ";
        }
        final StringBuilder sql = new StringBuilder();
        boolean first = true;
        for (final NameValuePair<Order> orderBy : this.orderBys) {
            if (first) {
                sql.append(" ORDER BY ");
                first = false;
            } else {
                sql.append(",");
            }
            sql.append(orderBy.getName());
            if (orderBy.getValue() != Order.ASC) {
                sql.append(" ");
                sql.append(orderBy.getValue().name());
            }
        }
        return sql.toString();
    }

    /**
     * Constructs a {@link Where} object from this instance, suitable for
     * creating a prepared SQL statement.
     *
     * @return A where clause that is equivalent to this criteria.
     */
    public Where getWhere() {
        final List<Object> values = new ArrayList<>();
        final StringBuilder where = new StringBuilder();
        boolean first = true;
        if (!CollectionUtils.isEmpty(this.eqs)) {
            for (final NameValuePair<String> eq : this.eqs) {
                if (first) {
                    where.append(" WHERE ");
                    first = false;
                } else {
                    where.append(" AND ");
                }
                where.append(eq.getName());
                where.append(" ILIKE ?");
                values.add(eq.getValue());

            }
        }
        if (!CollectionUtils.isEmpty(this.intWhere)) {
            for (final NameValuePair<Integer> num : this.intWhere) {
                if (first) {
                    where.append(" WHERE ");
                    first = false;
                } else {
                    where.append(" AND ");
                }
                where.append(num.getName());
                where.append(" <= ?");
                values.add(num.getValue());
            }
        }

        if (!CollectionUtils.isEmpty(this.betweenWhere)) {
            for (final NameValuePair<BetweenDates> num : this.betweenWhere) {
                if (first) {
                    where.append(" WHERE ");
                    first = false;
                } else {
                    where.append(" AND ");
                }
                where.append(num.getName());
                where.append(" BETWEEN ?");
                where.append(" AND ?");
                values.add(num.getValue().getFrom());
                values.add(num.getValue().getTo());
            }
        }
        return new Where(where.toString(), values);
    }


    public Criteria orderBy(final String field, final Order order) {
        if (this.orderBys == null) {
            this.orderBys = new ArrayList<>();
        }
        this.orderBys.add(new NameValuePair<>(field, order));
        return this;
    }


}
