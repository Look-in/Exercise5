package by.dao.old.request;

import by.dao.jdbc.connection.ConnectionPool;
import by.dao.old.SelectItemType;
import by.entity.old.ItemType;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectItemTypeDao implements SelectItemType {

    @Inject
    private ConnectionPool connectionPool;

    private static PreparedStatement selectPreparedStatement(Connection connection, int id) throws SQLException {
        final String SQL = "SELECT " +
                "ITEM_TYPE " +
                "FROM ITEM_TYPE WHERE id= ?;";
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public List<ItemType> readItemTypes() {
        final String sql = "SELECT ID,ITEM_TYPE " +
                "FROM ITEM_TYPE;";
        List<ItemType> type = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                type.add(new ItemType(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException exc) {
            throw new RuntimeException(
                    "Error reading DB:" + exc.getMessage());
        }
        return type;
    }

    @Override
    public String readItemType(int itemStatusId) {
        String result = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = selectPreparedStatement(connection, itemStatusId);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                result = rs.getString(1);
            }
        } catch (SQLException exc) {
            throw new RuntimeException(
                    "Error reading ItemStatuses:" + exc.getMessage());
        }
        return result;
    }
}