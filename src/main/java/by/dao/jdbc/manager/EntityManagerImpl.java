package by.dao.jdbc.manager;

import by.Utils.MainUtils;
import by.dao.jdbc.connection.ConnectionPool;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManagerImpl<T> implements EntityManager<T>{

    @Inject
    private ConnectionPool connectionPool;

    private PreparedStatement selectPreparedStatement(String sql, Connection connection, Object id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, id);
        return statement;
    }

    public T find(String sql, Class<T> tClass, Object id) {
        T object = null;
        tClass.cast(object);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = selectPreparedStatement(sql, connection, id);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                object = MainUtils.getEntityResultSet(rs, tClass);
            }
        } catch (Exception exc) {
            //getLogger().error("Error reading User from DB:" + exc.getMessage());
            throw new RuntimeException(
                    "Error reading User from DB:" + exc.getMessage());
        }
        return object;
    }


    public List<T> findAll(String sql, Class<T> tClass) {
        List<T> entities = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                entities.add(MainUtils.getEntityResultSet(rs, tClass));
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
        return entities;
    }
}
