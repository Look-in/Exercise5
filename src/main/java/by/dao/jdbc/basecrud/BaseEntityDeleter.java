package by.dao.jdbc.basecrud;

import by.Utils.annotations.Entity;
import by.Utils.annotations.Table;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Базовый класс для удаления сущностей из БД, используя JDBC
 *
 * <p> Этот класс на основе рефлексии считывает поле Table сущности
 * и генерирует SQL запрос. Также в качестве параметра метод
 * delete может принимать SQL запрос для дальнейшей обработки.
 * </>
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Slf4j
public class BaseEntityDeleter extends BaseEntityReader {

    private PreparedStatement deletePreparedStatement(String sql, Connection connection, Object id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, id);
        return statement;
    }

    /**
     * Метод удаляет сущность из БД.
     *
     * @param sql
     * @param id  идентификатор сущности
     */
    void delete(String sql, Object id) {
        try (PreparedStatement statement = deletePreparedStatement(sql, getDataSource().getConnection(), id)) {
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

    /**
     * Метод генерирует SQL запрос.
     *
     * @param tClass - класс сущности
     * @return строку SQL запроса
     */
    private <T> String sqlGeneration(Class<T> tClass) {
        Table table = tClass.getAnnotation(Table.class);
        String tableName = (table != null ? table.name() : tClass.getSimpleName());
        String sql = String.format("delete from %s where id=?", tableName);
        log.debug("Create SQL: {}", sql);
        return sql;
    }
}