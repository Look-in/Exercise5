package by.dao.jdbc.basecruid;

import by.Utils.MainUtils;
import by.Utils.annotations.*;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class BaseEntityReader extends BaseEntityUpdater {

    private Map<Class<?>, String> classQueriesById = new ConcurrentHashMap<>();

    private Map<Class<?>, String> classQueries = new ConcurrentHashMap<>();

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
                    if (matcher.replaceFirst("").equals(MainUtils.upperCaseFirst(field.getName()))) {
                        field.setAccessible(true);
                        if (field.get(self) == null) {
                            log.debug(String.format("Start lazy initialization: Class [%s] Field [%s]",
                                    clazz.getSimpleName(), field.getName()));
                            JoinTable joinTable = field.getAnnotation(JoinTable.class);
                            String sql = sqlGenerationOneToMany(joinTable.name(),
                                    joinTable.joinColumns().name(), joinTable.inverseJoinColumns().name());
                            field.set(self, findInversedColumnElements(ReflectionUtils.getGenericParameterField(field),
                                    sql, ReflectionUtils.getIdValueFromObject(clazz, (T) self)));
                        }
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
            String error = String.format("Error creating proxy object: %s", e);
            log.error(error);
            throw new RuntimeException(error);
        }
        return object;
    }

    private <T> List<T> findInversedColumnElements(Class<T> tClass, String sql, Object id) {
        List<T> elements = new ArrayList<>();
        try (Connection connection = getConnectionPool().getConnection();
             PreparedStatement statement = selectPreparedStatement(sql, connection, id);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                elements.add(find(tClass, rs.getString("id")));
            }
        } catch (SQLException e) {
            String error = String.format("Error finding InversedColumnElements: %s", e);
            log.error(error);
            throw new RuntimeException(error);
        }
        return elements;
    }

    private String sqlGenerationOneToMany(String joinTableName, String joinColumnName, String inverseJoinColumnName) {
        String sql = String.format("Select %s as id from %s where %s = ?;", inverseJoinColumnName,
                joinTableName, joinColumnName);
        log.debug("Create SQL: {}", sql);
        return sql;
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
            String error = String.format("Error finding element Class: %s by id = %s + message: %s", tClass.getSimpleName(), id, e);
            log.error(error);
            throw new RuntimeException(error);
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
            String error = String.format("Error finding list of element Class: %s + message: %s", tClass.getSimpleName(), exc);
            log.error(error);
            throw new RuntimeException(error);
        }
        return entities;
    }

    protected <T> List<T> findAll(Class<T> tClass) {
        return findAll(sqlGeneration(tClass, false), tClass);
    }

    private String sqlGeneration(Class<?> tClass, boolean requiredId) {
        String sql = requiredId ? classQueriesById.get(tClass) : classQueries.get(tClass);
        if (sql != null) {
            log.debug("Get SQL from the cash: {}", sql);
            return sql;
        }
        List<Field> fields = ReflectionUtils.getAllClassFields(new ArrayList<>(), tClass);
        String idFieldName = null;
        StringBuilder fieldNames = new StringBuilder();
        for (Field field : fields) {
            if (field.getAnnotation(OneToMany.class) != null &&
                    field.getAnnotation(OneToMany.class).fetch() == OneToMany.FetchType.LAZY) {
                continue;
            }
            String fieldAnnotatedName = ReflectionUtils.getFieldAnnotatedName(field);
            if (field.getAnnotation(Id.class) != null) {
                idFieldName = fieldAnnotatedName;
            }
            if (fieldNames.length() == 0) {
                fieldNames.append(fieldAnnotatedName);
            } else {
                fieldNames.append(", ");
                fieldNames.append(fieldAnnotatedName);
            }
        }
        Table table = tClass.getAnnotation(Table.class);
        String tableName = (table != null ? table.name() : tClass.getSimpleName());
        StringBuilder str = new StringBuilder();
        str.append(String.format("Select %s from %s", fieldNames.toString(), tableName));
        if (requiredId) {
            str.append(String.format(" where %s = ?;", idFieldName));
        }
        sql = str.toString();
        if (requiredId) {
            classQueriesById.put(tClass, sql);
        } else {
            classQueries.put(tClass, sql);
        }
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
            String error = String.format("Error reading %s from DB by id = %s. Error: %s", tClass.getSimpleName(), id, exc);
            log.error(error);
            throw new RuntimeException(error);
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
            Object fieldObject = null;
            try {
                String name = ReflectionUtils.getFieldAnnotatedName(field);
                if (ReflectionUtils.isPrimitiveOrWrapperType(field.getType())) {
                    fieldObject = ReflectionUtils.getValueFromResultSet(rs, name);
                } else {
                    if (field.getAnnotation(ManyToOne.class) != null) {
                        fieldObject = findByConnection(sqlGeneration(field.getType(), true), field.getType(),
                                ReflectionUtils.getValueFromResultSet(rs, name), connection);
                    }
                }
                field.setAccessible(true);
                field.set(object, fieldObject);
            } catch (Exception e) {
                String error = String.format("Error iteration of ResultSet: %s. Error: %s", tClass.getSimpleName(), e);
                log.error(error);
                throw new RuntimeException(error);
            }
        }
        return object;
    }
}
