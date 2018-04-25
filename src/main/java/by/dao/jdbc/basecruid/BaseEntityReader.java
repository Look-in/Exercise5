package by.dao.jdbc.basecruid;

import by.Utils.ReflectionUtils;
import by.Utils.annotations.*;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BaseEntityReader extends BaseEntityUpdater {

    protected <T> T getProxy(Class<T> clazz) {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(clazz);
        factory.setFilter(m -> {
            // ignore finalize()
            return !m.getName().equals("finalize");
        });
        MethodHandler mi = (self, m, proceed, args) -> {
            return proceed.invoke(self, args);  // execute the original method.
        };
        T object = null;
        try {
            object = (T) factory.create(new Class[0], new Object[0], mi);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static class LoggingInvocationHandler<T>
            implements InvocationHandler {
        final T underlying;

        public LoggingInvocationHandler(T underlying) {
            this.underlying = underlying;
        }

        @Override
        public Object invoke(Object proxy, Method method,
                             Object[] args) throws Throwable {
            StringBuffer sb = new StringBuffer();
            sb.append(method.getName()); sb.append("(");
            for (int i=0; args != null && i<args.length; i++) {
                if (i != 0)
                    sb.append(", ");
                sb.append(args[i]);
            }
            sb.append(")");
            Object ret = method.invoke(underlying, args);
            if (ret != null) {
                sb.append(" -> "); sb.append(ret);
            }
            System.out.println(sb);
            return ret;
        }
    }

    private PreparedStatement selectPreparedStatement(String sql, Connection connection, Object id) throws SQLException {
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
            //getLogger().error("Error reading User from DB:" + exc.getMessage());
            throw new RuntimeException(
                    "Error reading User from DB:" + exc.getMessage());
        }
        return object;
    }

    private <T> T getEntityResultSet(ResultSet rs, Class<T> tClass, Connection connection) {
        if (tClass.getAnnotation(Entity.class) == null) return null;
        T object = ReflectionUtils.getEntity(tClass);
        for (Field field : ReflectionUtils.getAllClassFields(new ArrayList<>(), tClass)) {
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
