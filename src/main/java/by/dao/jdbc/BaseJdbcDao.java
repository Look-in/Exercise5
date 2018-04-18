package by.dao.jdbc;

import by.dao.jdbc.connection.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class BaseJdbcDao {

    @Inject
    private ConnectionPool connectionPool;

   private Logger logger = LoggerFactory.getLogger(getClass());

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

   public Logger getLogger() {
        return logger;
    }
}
