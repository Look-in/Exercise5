package by.jdbc;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Реализация {@link ConnectionPool}.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@ApplicationScoped
public class ConnectionPoolImpl implements ConnectionPool {

    private static Properties jdbcProp;

    private Queue<Connection> connections = new LinkedBlockingQueue<>();

    public ConnectionPoolImpl() {
        String driver = getProperties().getProperty("db.driver");
        String url = getProperties().getProperty("db.url");
        int countConnections = Integer.valueOf(getProperties().getProperty("db.poolsize"));
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("Can't register db.driver %s: ", driver) + e);
        }
        for (int i = 0; i < countConnections; i++) {
            try {
                connections.add(new PooledConnection(DriverManager.getConnection(url, getProperties()), this));
            } catch (SQLException e) {
                throw new RuntimeException(String.format("Can't get connection from %s. Error: ", url) + e);
            }
        }
    }

    private Properties getProperties() {
        if (jdbcProp == null) {
            jdbcProp = new Properties();
            String filename = "database.properties";
            try (InputStream in = getClass().getClassLoader().getResourceAsStream(filename)) {
                jdbcProp.load(in);
            } catch (IOException e) {
                throw new RuntimeException("Can't read database.properties: " + e);
            }
        }
        return jdbcProp;
    }

    @Override
    public Connection getConnection() {
        return connections.poll();
    }

    @Override
    public void putConnection(Connection connection) {
        connections.add(connection);
    }
}
