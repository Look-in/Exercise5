package by.dao.jdbc.connection;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@ApplicationScoped
public class ConnectionPoolImpl implements ConnectionPool {

    private final String URL = "jdbc:mysql://localhost:3306/race";

    private static Properties jdbcProp;

    private BlockingQueue<Connection> connections = new ArrayBlockingQueue<>(20);

    private ConnectionPoolImpl() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        for (int i = 0; i < 20 ; i++) {
            connections.add(new PooledConnection(DriverManager.getConnection(URL, getProperties()),this));
        }
        } catch (SQLException e) {
            e.printStackTrace();
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
