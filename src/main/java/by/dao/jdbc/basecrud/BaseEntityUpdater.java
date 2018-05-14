package by.dao.jdbc.basecrud;

import by.Utils.ReflectionUtils;
import by.Utils.annotations.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.*;

@Slf4j
public class BaseEntityUpdater extends BaseConnectionKeeper {

    private PreparedStatement updatePreparedStatement(String sql, Connection connection, Object entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, getValueOfId(entity));
        return statement;
    }

    private <T> void update(String sql, Class<T> tClass, T entity) {
        if (tClass.getAnnotation(Entity.class) == null) return;
        try (Connection connection = getConnectionPool().getConnection()) {
            updateByConnection(sql, entity, connection);
        } catch (SQLException e) {
            throw new RuntimeException("Can't update entity " + entity.toString() + e);
        }
    }

    protected <T> void merge(Class<T> tClass, T entity) {
        update(sqlGeneration(tClass, entity), tClass, entity);
    }

    private <T> String sqlGeneration(Class<T> tClass, T entity) {
        Table table = tClass.getAnnotation(Table.class);
        String tableName = (table != null ? table.name() : tClass.getSimpleName());
        String sql = String.format("update %s set %s where id=?", tableName, getEntityFields(tClass, entity, false));
        log.debug("Create SQL: {}", sql);
        return sql;
    }

    private <T> void updateByConnection(String sql, T entity, Connection connection) {
        try (PreparedStatement statement = updatePreparedStatement(sql, connection, entity)) {
            statement.executeUpdate();
        } catch (Exception exc) {
            log.error("Error updating entity to DB: " + exc.getMessage());
            throw new RuntimeException(
                    "Error updating entity to DB: " + exc.getMessage());
        }
    }

    private <T> Object getValueOfId(T object) {
        for (Field field : ReflectionUtils.getAllClassFields(object.getClass())) {
            if (field.getAnnotation(Id.class) != null) {
                try {
                    field.setAccessible(true);
                    return field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> String getEntityFields(Class<T> tClass, T object, boolean getId) {
        StringBuilder fieldNameAndValue = new StringBuilder();
        for (Field field : ReflectionUtils.getAllClassFields(tClass)) {
            if ((field.getModifiers() & java.lang.reflect.Modifier.FINAL) == java.lang.reflect.Modifier.FINAL) {
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value == null) continue;
                if (!getId) {
                    if (field.getAnnotation(Id.class) == null) {
                        if (fieldNameAndValue.length() != 0) {
                            fieldNameAndValue.append(", ");
                        }
                        if (joinColumn != null) {
                            Class clazz = field.getType();
                            value = getEntityFields(clazz, value, true);
                            fieldNameAndValue.append(joinColumn.name());
                        } else {
                            fieldNameAndValue.append(column != null ? column.name() : field.getName());
                        }
                        fieldNameAndValue.append("='");
                        fieldNameAndValue.append(value);
                        fieldNameAndValue.append("'");
                    }
                } else {
                    if (field.getAnnotation(Id.class) != null) {
                        fieldNameAndValue.append(value);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return fieldNameAndValue.toString();
    }
}
