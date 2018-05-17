package by.dao.jdbc.basecrud;

import by.Utils.annotations.Entity;
import by.Utils.annotations.Table;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class BaseEntityDeleter extends BaseEntityReader {

    private PreparedStatement deletePreparedStatement(String sql, Connection connection, Object id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, id);
        return statement;
    }

    void delete(String sql, Object id) {
        try (PreparedStatement statement = deletePreparedStatement(sql, getBaseConnectionKeeper().getConnection(), id)) {
            statement.executeUpdate();
        } catch (Exception exc) {
            log.error("Error deleting entity from DB: " + exc.getMessage());
            throw new RuntimeException(
                    "Error deleting entity from DB: " + exc.getMessage());
        }
    }

    protected <T> void remove(Class<T> tClass, Object id) {
        if (tClass.getAnnotation(Entity.class) == null) return;
        delete(sqlGeneration(tClass), id);
    }

    private <T> String sqlGeneration(Class<T> tClass) {
        Table table = tClass.getAnnotation(Table.class);
        String tableName = (table != null ? table.name() : tClass.getSimpleName());
        String sql = String.format("delete from %s where id=?", tableName);
        log.debug("Create SQL: {}", sql);
        return sql;
    }
}