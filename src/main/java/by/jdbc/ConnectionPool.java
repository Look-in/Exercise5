package by.jdbc;

import java.sql.Connection;

public interface ConnectionPool {

    Connection getConnection();

    void putConnection(Connection connection);
}
