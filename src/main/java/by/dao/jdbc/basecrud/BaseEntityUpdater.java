package by.dao.jdbc.basecrud;

import by.Utils.ReflectionUtils;
import by.Utils.annotations.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

@Slf4j
public class BaseEntityUpdater extends BaseEntityCreator {

    private PreparedStatement updatePreparedStatement(String sql, Object entity) throws SQLException {
        PreparedStatement statement = getBaseConnectionKeeper().getConnection().prepareStatement(sql);
        statement.setObject(1, DaoReflectionUtils.getIdValueFromObject(entity));
        return statement;
    }

    private <T> void update(String sql, Class<T> tClass, T entity) {
        if (tClass.getAnnotation(Entity.class) == null || sql == null) return;
        try (PreparedStatement statement = updatePreparedStatement(sql, entity)) {
            statement.executeUpdate();
        } catch (Exception exc) {
            log.error("Error updating entity to DB: " + exc.getMessage());
            throw new RuntimeException(
                    "Error updating entity to DB: " + exc.getMessage());
        }
    }

    protected <T> void merge(Class<T> tClass, T entity) {
        update(sqlGeneration(tClass, entity), tClass, entity);
    }

    private <T> String sqlGeneration(Class<T> tClass, T entity) {
        String fields = getEntityFields(tClass, entity, false);
        if ("".equals(fields)) return null;
        Table table = tClass.getAnnotation(Table.class);
        String tableName = (table != null ? table.name() : tClass.getSimpleName());
        String sql = String.format("update %s set %s where id=?", tableName, fields);
        log.debug("Create SQL: {}", sql);
        return sql;
    }

    private <T> void updateByConnection(String sql, T entity, Connection connection) {

    }

    @SuppressWarnings("unchecked")
    private void updateOneToManyField(Field field, Object id, Object value) {
        JoinTable joinTable = field.getAnnotation(JoinTable.class);
        Set values = (Set) value;
        String deleteSql = String.format("delete from %s where %s=?;", joinTable.name(), joinTable.joinColumns().name());
        delete(deleteSql, id);
        values.forEach(e -> {
            String sql = String.format("insert into %s (%s,%s) values (%s,%s);", joinTable.name(), joinTable.joinColumns().name(),
                    joinTable.inverseJoinColumns().name(), id, DaoReflectionUtils.getIdValueFromObject(e));
            insert(sql);
        });
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
            OneToMany oneToMany = field.getAnnotation(OneToMany.class);
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value == null) continue;
                if (!getId) {
                    if (field.getAnnotation(Id.class) == null) {
                        if ((column != null && !column.updatable()) || (joinColumn != null && !joinColumn.updatable()))
                            continue;
                        if (oneToMany != null && Collection.class.isAssignableFrom(field.getType())) {
                            updateOneToManyField(field, DaoReflectionUtils.getIdValueFromObject(object), value);
                            continue;
                        }
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
