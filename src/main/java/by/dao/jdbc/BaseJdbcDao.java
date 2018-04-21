package by.dao.jdbc;

import by.dao.jdbc.connection.ConnectionPool;
import by.entity.Role;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BaseJdbcDao {

    @Getter
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private ConnectionPool connectionPool;

    private PreparedStatement selectPreparedStatement(String sql, Connection connection, Object id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, id);
        return statement;
    }

    protected  <T> T find(String sql, Class<T> tClass, Object id) {
        T object = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = selectPreparedStatement(sql, connection, id);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                object = getEntityResultSet(rs, tClass);
            }
        } catch (Exception exc) {
            //getLogger().error("Error reading User from DB:" + exc.getMessage());
            throw new RuntimeException(
                    "Error reading User from DB:" + exc.getMessage());
        }
        return object;
    }


    protected <T> List<T> findAll(String sql, Class<T> tClass) {
        List<T> entities = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                entities.add(getEntityResultSet(rs, tClass));
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
        return entities;
    }



    private List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
        return fields;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getValueFromResultSet(final ResultSet rs,
                                               String columnLabel) {
        try {
            return (T) rs.getObject(columnLabel);
        } catch (SQLException e) {
            return null;
        }
    }

    private  <T> T getEntityResultSet(ResultSet rs, Class<T> tClass) {
        T object;
        try {
            object = tClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        for (Field field : getAllFields(new ArrayList<>(), tClass)) {
            if((field.getModifiers() & java.lang.reflect.Modifier.FINAL) == java.lang.reflect.Modifier.FINAL)
            {
                continue;
            }
            Column col = field.getAnnotation(Column.class);
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            Object fieldObject;
            try {
                if (joinColumn == null) {
                    String name;
                    if (col != null) {
                        name = col.name();
                    } else {
                        name = field.getName();
                    }
                  //  System.out.println(rs.findColumn(name));
                        fieldObject = getValueFromResultSet(rs, name);
                } else {
                    fieldObject = find(Role.SQL, field.getType(), getValueFromResultSet(rs,joinColumn.name()));
                }
                field.setAccessible(true);
                field.set(object, fieldObject);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return object;
    }

  /*  private <T> T convertInstanceOfObject(Object o) {
        try {
            return (T) o;
        } catch (ClassCastException e) {
            return null;
        }
    }*/
}
