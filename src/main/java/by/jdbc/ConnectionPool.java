package by.jdbc;

import java.sql.Connection;

/**
 * Интерфейс для работы с пулом соединений.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public interface ConnectionPool {

    Connection getConnection();

    void putConnection(Connection connection);
}
