package by.dao.old.request;


import by.dao.jdbc.connection.ConnectionPool;
import by.dao.old.SelectItemStatusDao;
import by.entity.old.ItemStatus;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectItemStatusDaoImpl implements SelectItemStatusDao {

    @Inject
    private ConnectionPool connectionPool;

    @Override
    public List<ItemStatus> readItemStatuses() {
        final String SQL = "SELECT " +
                "ID,STATUS " +
                "FROM ITEM_STATUS order by id;";
        List<ItemStatus> status = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SQL)) {
            while (rs.next()) {
                status.add(new ItemStatus(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException exc) {
            throw new RuntimeException(
                    "Error reading ItemStatuses:" + exc.getMessage());
        }
        return status;
    }

    private PreparedStatement selectPreparedStatement(Connection connection, int id) throws SQLException {
        final String SQL = "SELECT " +
                "STATUS " +
                "FROM ITEM_STATUS WHERE id= ?;";
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public String readItemStatus(int itemStatusId) {
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
