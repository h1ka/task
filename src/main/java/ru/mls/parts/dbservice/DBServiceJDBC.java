package ru.mls.parts.dbservice;

import ru.mls.parts.Main;
import ru.mls.parts.criteria.Criteria;
import ru.mls.parts.executor.DbExecutor;
import ru.mls.parts.executor.DbExecutorImpl;
import ru.mls.parts.helper.ReflectionClass;
import ru.mls.parts.model.Params;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class DBServiceJDBC<T> implements DBService<T> {
    DataSource dataSource;

    public DBServiceJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
        Main main = new Main(dataSource);
        try {
            main.createTable();
            main.insertStartRecords();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<T> loadAll(String tableName, Class<T> clazz) {
        String sql = String.format("SELECT * FROM %s", tableName);
        try (Connection connection = dataSource.getConnection()) {
            DbExecutor<T> executor = new DbExecutorImpl<>(connection);
            List<T> records = executor.selectAllRecords(sql, getResultSet(clazz));
            if (!records.isEmpty()) {
                return records;
            } else return Collections.emptyList();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("load all throw SQL Exception " + e.getErrorCode());
        }
    }


    @Override
    public List<T> loadAll(String tableName, Params params, Class<T> clazz) {
        StringBuilder sqlBuilder = new StringBuilder(String.format("SELECT * FROM %s", tableName));
        final Criteria criteria = new Criteria();
        for (var param : params.getListParams()) {
            var value = params.getParam(param.toString());
            criteria.eq(param.toString(), value);
        }
        sqlBuilder.append(criteria.getWhere().getSql())
                .append(criteria.getOrderBySql());
        String sql = sqlBuilder.toString();
        return loadAllColumn(sql,criteria.getWhere().getValues(), clazz);

    }

    private List<T> loadAllColumn(String sql, List<Object> params, Class<T> clazz) {

        try (Connection connection = dataSource.getConnection()) {
            DbExecutor<T> executor = new DbExecutorImpl<>(connection);
            List<T> records = executor.selectAllRecordsWithParams(sql, params, getResultSet(clazz));
            if (!records.isEmpty()) {
                return records;
            } else return Collections.emptyList();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("load all throw SQL Exception " + e.getErrorCode());
        }
    }


    private Function<ResultSet, T> getResultSet(Class<T> clazz) {
        int countFields = clazz.getDeclaredFields().length;
        Function<ResultSet, T> functionResultSet = (resultSet) -> {
            try {
                Object[] objects = new Object[countFields];
                if (resultSet.next()) {
                    for (int i = 0; i < countFields; i++) {
                        Object resultSetObject = resultSet.getObject(i + 1);
                        objects[i] = resultSetObject;
                    }
                    Object object = ReflectionClass.instantiate(clazz, objects);
                    return (T) object;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        };
        return functionResultSet;
    }

}
