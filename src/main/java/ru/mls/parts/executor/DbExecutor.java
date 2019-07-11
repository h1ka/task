package ru.mls.parts.executor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface DbExecutor<T> {
    Optional<T> selectRecord(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException;

    List<T> selectAllRecords(String sql, Function<ResultSet, T> rsHandler) throws SQLException;

    List<T> selectAllRecordsWithParams(String sql, List<Object> params, Function<ResultSet, T> rsHandler);
}
