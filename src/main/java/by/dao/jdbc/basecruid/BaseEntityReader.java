package by.dao.jdbc.basecruid;

import by.Utils.ReflectionUtils;
import by.Utils.annotations.*;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Slf4j
public class BaseEntityReader extends BaseEntityUpdater {

    private String upperCaseFirst(String value) {
        char[] array = value.toCharArray();
        array[0] = Character.toUpperCase(array[0]);
        return new String(array);
    }

    private Class<?> getGenericParameterField(Field field) {
        ParameterizedType genericSuperclass = (ParameterizedType) field.getGenericType();
        Type type = genericSuperclass.getActualTypeArguments()[0];
        return (type instanceof Class ? (Class<?>) type : (Class<?>) ((ParameterizedType) type).getRawType());
    }

    @SuppressWarnings("unchecked")
    private <T> T getProxy(Class<T> clazz) {
        List<Field> lazyFields = ReflectionUtils.getAllClassFields(new ArrayList<>(), clazz).stream()
                .filter(e -> e.getAnnotation(OneToMany.class) != null &&
                        e.getAnnotation(OneToMany.class).fetch() == OneToMany.FetchType.LAZY)
                .collect(Collectors.toList());
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(clazz);
        MethodHandler mi = (self, m, proceed, args) -> {
            String regex = "^get+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(m.getName());
            if (matcher.find()) {
                for (Field field : lazyFields) {
                    if (matcher.replaceFirst("").equals(upperCaseFirst(field.getName()))) {
                        return findAll(getGenericParameterField(field));
                    }
                }
            }
            return proceed.invoke(self, args); // execute the original method.
        };
        T object;
        try {
            object = (T) factory.create(new Class[0], new Object[0], mi);
        } catch (NoSuchMethodException | IllegalArgumentException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            log.error("Error creating proxy object: " + e);
            throw new RuntimeException("Error creating proxy object: " + e);
        }
        return object;
    }

    private PreparedStatement selectPreparedStatement(String sql, Connection connection, Object id)
            throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, id);
        return statement;
    }

    protected <T> T find(String sql, Class<T> tClass, Object id) {
        T object;
        try (Connection connection = getConnectionPool().getConnection()) {
            object = findByConnection(sql, tClass, id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    protected <T> T find(Class<T> tClass, Object id) {
        return find(sqlGeneration(tClass, true), tClass, id);
    }

    protected <T> List<T> findAll(String sql, Class<T> tClass) {
        List<T> entities = new ArrayList<>();
        try (Connection connection = getConnectionPool().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                entities.add(getEntityResultSet(rs, tClass, connection));
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
        return entities;
    }

    protected <T> List<T> findAll(Class<T> tClass) {
        return findAll(sqlGeneration(tClass, false), tClass);
    }

    private String sqlGeneration(Class<?> tClass, boolean requiredId) {
        Table table = tClass.getAnnotation(Table.class);
        String tableName = (table != null ? table.name() : tClass.getSimpleName());
        StringBuilder str = new StringBuilder();
        str.append(String.format("Select * from %s", tableName));
        if (requiredId) {
            str.append(" where id = ?;");
        }
        String sql = str.toString();
        log.debug("Create SQL: {}", sql);
        return sql;
    }

    private <T> T findByConnection(String sql, Class<T> tClass, Object id, Connection connection) {
        T object = null;
        try (PreparedStatement statement = selectPreparedStatement(sql, connection, id);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                object = getEntityResultSet(rs, tClass, connection);
            }
        } catch (Exception exc) {
            log.error("Error reading User from DB:" + exc.getMessage());
            throw new RuntimeException("Error reading User from DB:" + exc.getMessage());
        }
        return object;
    }

    private <T> T getEntityResultSet(ResultSet rs, Class<T> tClass, Connection connection) {
        if (tClass.getAnnotation(Entity.class) == null)
            return null;
        T object = getProxy(tClass);
        List<Field> fields = ReflectionUtils.getAllClassFields(new ArrayList<>(), tClass);
        for (Field field : fields) {
            if ((field.getModifiers() & java.lang.reflect.Modifier.FINAL) == java.lang.reflect.Modifier.FINAL) {
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            Object fieldObject = null;
            try {
                String name;
                if (ReflectionUtils.isPrimitiveOrWrapperType(field.getType())) {
                    name = (column != null ? column.name() : field.getName());
                    fieldObject = ReflectionUtils.getValueFromResultSet(rs, name);
                } else {
                    if (field.getAnnotation(ManyToOne.class) != null) {
                        name = (joinColumn != null ? joinColumn.name() : field.getName());
                        fieldObject = findByConnection(sqlGeneration(field.getType(), true), field.getType(),
                                ReflectionUtils.getValueFromResultSet(rs, name), connection);
                    }
                }
                field.setAccessible(true);
                field.set(object, fieldObject);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return object;
    }
}
