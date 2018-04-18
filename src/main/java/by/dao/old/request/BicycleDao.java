package by.dao.old.request;

import by.dao.jdbc.connection.ConnectionPool;
import by.dao.old.ChangeInstance;
import by.entity.old.Bicycle;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BicycleDao extends ItemDao implements ChangeInstance<Bicycle> {

    @Inject
    private ConnectionPool connectionPool;

    private PreparedStatement createPreparedStatement(Connection connection, Bicycle entity) throws SQLException {
        final String sql = "INSERT INTO BICYCLE "
                + "(ID, FORK, BRAKES, FRAME) VALUES "
                + "(?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, entity.getItemId());
        statement.setString(2, entity.getFork());
        statement.setString(3, entity.getBrakes());
        statement.setString(4, entity.getFrame());
        return statement;
    }

    private PreparedStatement updatePreparedStatement(Connection connection, Bicycle entity) throws SQLException {
        final String sql = "UPDATE BICYCLE SET "
                + "FORK = ?, BRAKES=?, FRAME=? "
                + "WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, entity.getFork());
        statement.setString(2, entity.getBrakes());
        statement.setString(3, entity.getFrame());
        statement.setInt(4, entity.getItemId());
        return statement;
    }


    @Override
    public void create(Bicycle entity) {
        /*Try с ресурсами помещает коннект в пул
        после заверш обработки запроса.*/
        createItem(entity);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = createPreparedStatement(connection, entity)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Bicycle entity) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = updatePreparedStatement(connection, entity)) {
            updateItem(entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            return;
        }
        final String sql = "DELETE FROM ITEM "
                + "WHERE id = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception exc) {
            throw new RuntimeException(
                    "Error reading DB:" + exc.getMessage());
        }
    }

}
