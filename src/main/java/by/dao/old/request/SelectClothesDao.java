package by.dao.old.request;


import by.dao.jdbc.connection.ConnectionPool;
import by.dao.old.SelectDao;
import by.dao.old.SelectDefaultItemDao;
import by.entity.old.Clothes;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectClothesDao implements SelectDao<Clothes> {

    @Inject
    private ConnectionPool connectionPool;

    @Inject
    SelectDefaultItemDao selectDefaultItemDao;

    private PreparedStatement selectPreparedStatement(Connection connection, int id) throws SQLException {
        final String sql = "SELECT " +
                "SEASON" +
                " FROM CLOTHES " +
                "WHERE ID=?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public void readItem(Clothes item) {
        //Заполнить базовые свойства
        selectDefaultItemDao.readItem(item);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = selectPreparedStatement(connection, item.getItemId());
             ResultSet rs = statement.executeQuery();) {
            while (rs.next()) {
                item.setSeason(rs.getString(1));
            }
        } catch (Exception exc) {
            throw new RuntimeException(
                    "Error reading DB:" + exc.getMessage());
        }
    }
}
