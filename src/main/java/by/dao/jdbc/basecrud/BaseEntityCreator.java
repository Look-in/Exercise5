package by.dao.jdbc.basecrud;

import by.Utils.ReflectionUtils;
import by.Utils.annotations.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BaseEntityCreator extends BaseEntityUpdater {

    private PreparedStatement createPreparedStatement(String sql, Connection connection, Object entity) throws SQLException {
        return connection.prepareStatement(sql);
    }

    private  <T> void persist(String sql, Class<T> tClass, T entity) {
        if (tClass.getAnnotation(Entity.class) == null) return;
        try (Connection connection = getConnectionPool().getConnection()) {
            createByConnection(sql, entity, connection);
        } catch (SQLException e) {
            String error = String.format("Can't insert entity %s . Error %s", entity.toString(), e);
            log.error(error);
            throw new RuntimeException(error);
        }
    }

    protected <T> void persist(Class<T> tClass, T entity) {
        persist(sqlGeneration(tClass, entity), tClass, entity);
    }

    private <T> String sqlGeneration(Class<T> tClass, T entity) {
        Table table = tClass.getAnnotation(Table.class);
        String tableName = (table != null ? table.name() : tClass.getSimpleName());
        StringBuilder fieldNames = new StringBuilder();
        StringBuilder values = new StringBuilder();
        getEntityFields(tClass, entity, false).forEach((k, v) -> {
            if (fieldNames.length() != 0) fieldNames.append(",");
            if (values.length() != 0) values.append(",");
            fieldNames.append(k);
            values.append("'");
            values.append(v);
            values.append("'");
        });
        String sql = String.format("insert into %s (%s) values (%s);", tableName, fieldNames, values);
        System.out.println(sql);
        log.debug("Create SQL: {}", sql);
        return sql;
    }

    private <T> void createByConnection(String sql, T entity, Connection connection) {
        try (PreparedStatement statement = createPreparedStatement(sql, connection, entity)) {
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                //**!!!!!!! Обратить внимание!!!!!!!
                setValueOfId(entity, keys.getInt(1));
            }
        } catch (Exception exc) {
            log.error("Error inserting entity to DB: " + exc.getMessage());
            throw new RuntimeException(
                    "Error inserting entity to DB: " + exc.getMessage());
        }
    }

    private <T> void setValueOfId(T object, Object value) {
        for (Field field : ReflectionUtils.getAllClassFields(object.getClass())) {
            if (field.getAnnotation(Id.class) != null) {
                try {
                    field.setAccessible(true);
                    field.set(object, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Map<String, Object> getEntityFields(Class<T> tClass, T object, boolean getId) {
        Map<String, Object> fieldsAndValues = new HashMap<>();
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
                        if (joinColumn != null) {
                            Class clazz = field.getType();
                            fieldsAndValues.put(joinColumn.name(), getEntityFields(clazz, value, true).get("id"));
                        } else {
                            fieldsAndValues.put(column != null ? column.name() : field.getName(), value);
                        }
                    }
                } else {
                    if (field.getAnnotation(Id.class) != null) {
                        fieldsAndValues.put("id", value);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return fieldsAndValues;
    }
}
