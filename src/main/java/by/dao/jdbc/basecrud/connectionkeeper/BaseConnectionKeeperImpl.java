package by.dao.jdbc.basecrud.connectionkeeper;

import by.jdbc.ConnectionPool;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.SQLException;

@RequestScoped
class BaseConnectionKeeperImpl implements BaseConnectionKeeper {

    @Inject
    private ConnectionPool connectionPool;

    private Connection connection;

    @PostConstruct
    private void onLoad() {
        connection = connectionPool.getConnection();
    }

    @PreDestroy
    private void onDestroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Can't put connection to the pool: " + e);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
