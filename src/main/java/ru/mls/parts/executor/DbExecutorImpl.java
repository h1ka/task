package ru.mls.parts.executor;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DbExecutorImpl<T> implements DbExecutor<T> {

    private Connection connection;

    public DbExecutorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<T> selectRecord(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        try (PreparedStatement pst = this.connection.prepareStatement(sql)) {

            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        }
    }

    @Override
    public List<T> selectAllRecords(String sql, Function<ResultSet, T> rsHandler) throws SQLException {
        List<T> listRecords = new ArrayList<>();
        try (PreparedStatement pst = this.connection.prepareStatement(sql)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (true) {
                    Optional<T> record = Optional.ofNullable(rsHandler.apply(rs));
                    if (record != Optional.empty()) {
                        listRecords.add(record.get());
                    } else {
                        break;
                    }
                }
            }
            return listRecords;
        }
    }

    @Override
    public List<T> selectAllRecordsWithParams(String sql, List<Object> params, Function<ResultSet, T> rsHandler) {
        List<T> listRecords = new ArrayList<>();
        try (PreparedStatement pst = this.connection.prepareStatement(sql)) {
            for (int idx = 0; idx < params.size(); idx++) {
                //TOdo params!
                var paramForSql = params.get(idx);

                if (paramForSql instanceof Date) {
                    pst.setDate(idx + 1, (Date) paramForSql);
                } else if (paramForSql instanceof Integer) {
                    pst.setInt(idx + 1, (Integer) paramForSql);
                } else {
                    pst.setString(idx + 1, "%" + paramForSql + "%");
                }
            }
            try (ResultSet rs = pst.executeQuery()) {
                while (true) {
                    Optional<T> record = Optional.ofNullable(rsHandler.apply(rs));
                    if (record != Optional.empty()) {
                        listRecords.add(record.get());
                    } else {
                        break;
                    }
                }
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return listRecords;
    }
}
