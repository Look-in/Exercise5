package by.dao.jdbc.connection;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@ApplicationScoped
public class ConnectionPoolImpl implements ConnectionPool {

    private final String URL = "jdbc:mysql://localhost:3306/race";

    private static Properties jdbcProp;

    private int countConnections;

    private Queue<Connection> connections = new LinkedBlockingQueue<>();

    private ConnectionPoolImpl() {
        countConnections = Integer.valueOf(getProperties().getProperty("countConnectionsPool"));
        for (int i = 0; i < countConnections ; i++) {
            try {
                connections.add(new PooledConnection(DriverManager.getConnection(URL, getProperties()),this));
            } catch (SQLException e) {
                throw new RuntimeException(String.format("Can't get connection from %s. Error: ", URL)+e);
            }
        }
    }

    private Properties getProperties() {
        if (jdbcProp == null) {
            jdbcProp = new Properties();
            jdbcProp.put("user", "root");
            jdbcProp.put("password", "sql");
            jdbcProp.put("autoReconnect", "true");
            jdbcProp.put("characterEncoding", "UTF-8");
            jdbcProp.put("useUnicode", "true");
            jdbcProp.put("countConnectionsPool", "20");
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
