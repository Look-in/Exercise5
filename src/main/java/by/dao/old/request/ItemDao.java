package by.dao.old.request;

import by.dao.jdbc.connection.ConnectionPool;
import by.entity.event.Item;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ItemDao {

    @Inject
    private ConnectionPool connectionPool;

    private PreparedStatement createPreparedStatement(Connection connection, Item entity) throws SQLException {
        final String sql = "INSERT INTO ITEM "
                + "(PRICE,NAME,DESCRIPTION,ITEM_STATUS_ID,ITEM_TYPE_ID) VALUES "
                + "(?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setFloat(1, entity.getPrice());
        statement.setString(2, entity.getName());
        statement.setString(3, entity.getDescription());
        statement.setInt(4, entity.getItemStatus().getItemStatusId());
        statement.setInt(5, entity.getItemType().getItemTypeId());
        return statement;
    }

    public void createItem(Item entity) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = createPreparedStatement(connection, entity)) {
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                entity.setItemId(keys.getInt(1));
            }
        } catch (SQLException exc) {
            throw new RuntimeException(
                    "JDBC error:" + exc.getMessage());
        }
    }

    private PreparedStatement updatePreparedStatement(Connection connection, Item entity) throws SQLException {
        final String sql = "UPDATE ITEM SET "
                + "PRICE = ? , NAME = ? , DESCRIPTION= ? , ITEM_STATUS_ID = ? "
                + "WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setFloat(1, entity.getPrice());
        statement.setString(2, entity.getName());
        statement.setString(3, entity.getDescription());
        statement.setInt(4, entity.getItemStatus().getItemStatusId());
        statement.setInt(5, entity.getItemId());
        return statement;
    }

    public void updateItem(Item entity) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = updatePreparedStatement(connection, entity)) {
            statement.executeUpdate();
        } catch (SQLException exc) {
            throw new RuntimeException(
                    "JDBC error:" + exc.getMessage());
        }

    }

    public void deleteItem(Integer id) {

    }
}
