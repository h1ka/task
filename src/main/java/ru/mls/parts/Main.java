package ru.mls.parts;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

    DataSource dataSource;

    public Main(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createTable() throws SQLException {
        Connection connection = null;
        try  {
             connection = dataSource.getConnection();
            PreparedStatement pst = connection.prepareStatement
                    ("create table parts(name varchar(255)," +
                            "number varchar(255), vendor varchar(255)," +
                            " qty int4, snipped date ,receive date )");
            pst.executeUpdate();
        } catch (SQLException exp){
            exp.printStackTrace();
        } finally {
            connection.close();
        }
        System.out.println("table created");
    }

    public void insertStartRecords () throws SQLException {
        String sql = "INSERT INTO parts VALUES('one','ber','vendor','10','2016-06-23','2017-06-23')";
        String sql2 = "INSERT INTO parts VALUES('zero','number','enr','2','2017-06-23','2019-06-23')";
        String sql3 = "INSERT INTO parts VALUES('three','umbe','dor','31','2018-06-23','2019-06-23')";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sql3);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exp) {
            System.out.println("insert error " + exp.getSQLState());
        }
    }
}
