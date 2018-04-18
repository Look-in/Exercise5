package by.dao.old.request;


import by.dao.jdbc.connection.ConnectionPool;
import by.dao.old.SelectDao;
import by.dao.old.SelectDefaultItemDao;
import by.entity.old.Bicycle;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectBicycleDao implements SelectDao<Bicycle> {

    @Inject
    private ConnectionPool connectionPool;

    @Inject
    SelectDefaultItemDao selectDefaultItemDao;


    private PreparedStatement selectPreparedStatement(Connection connection, int id) throws SQLException {
        final String sql = "SELECT " +
                "FORK, BRAKES, FRAME" +
                " FROM BICYCLE " +
                "WHERE ID=?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public void readItem(Bicycle item) {
        //Заполнить базовые свойства
        selectDefaultItemDao.readItem(item);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = selectPreparedStatement(connection, item.getItemId());
             ResultSet rs = statement.executeQuery();) {
            while (rs.next()) {
                item.setFork(rs.getString(1));
                item.setBrakes(rs.getString(2));
                item.setFrame(rs.getString(3));
            }
        } catch (Exception exc) {
            throw new RuntimeException(
                    "Error reading DB:" + exc.getMessage());
        }
    }
}
