package by.dao.jdbc.basecrud;

import by.Utils.ReflectionUtils;
import by.Utils.annotations.*;
import by.dao.jdbc.basecrud.connectionkeeper.BaseConnectionKeeper;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Базовый класс для чтения сущностей из БД, используя JDBC
 *
 * <p> Этот класс на основе рефлексии считывает поля сущности
 * и генерирует SQL запрос. Также в качестве параметра методы
 * find могут также принимать SQL запрос для дальнейшей обработки
 * </>
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Slf4j
public class BaseEntityReader {

    @Getter
    @Inject
    private BaseConnectionKeeper baseConnectionKeeper;

    /**
     * Карты для хранения сгенерированных SQL запросов
     */
    private Map<Class<?>, String> classQueriesById = new ConcurrentHashMap<>();

    private Map<Class<?>, String> classQueries = new ConcurrentHashMap<>();

    /**
     * Метод проксирования класса с переопределенным методом для поля
     * помеченным, как Lazy. Если поле null, то подгружает данные из БД.
     * Для выборки использует новый коннект.
     *
     * @param clazz Проксируемый класс
     * @return прокси класс
     */
    @SuppressWarnings("unchecked")

    private <T> T getProxyWithLazyFieldInitializationEntity(Class<T> clazz) {
        List<Field> lazyFields = ReflectionUtils.getAllClassFields(clazz)
                .stream().filter(e -> e.getAnnotation(OneToMany.class) != null &&
                        e.getAnnotation(OneToMany.class).fetch() == OneToMany.FetchType.LAZY)
                .collect(Collectors.toList());
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(clazz);
        MethodHandler mi = (self, m, proceed, args) -> {
            if (m.getName().startsWith("get")) {
                for (Field field : lazyFields) {
                    if (m.getName().equalsIgnoreCase("get" + field.getName())) {
                        field.setAccessible(true);
                        if (field.get(self) == null) {
                            log.debug(String.format("Start lazy initialization: Class [%s] Field [%s]",
                                    clazz.getSimpleName(), field.getName()));
                            setFetchField(field, clazz, (T) self);
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

    /**
     * Метод для выборки из БД элементов, связанных с таблицей сущности по принципу OneToMany
     *
     * @param sql - SQL запрос
     * @param id  - Id сущности
     * @param <T> tClass
     * @return список элементов класса-аргумента
     */
    private <T> Set<T> findInversedColumnElements(Class<T> tClass, String sql, Object id) {
        Set<T> elements = new HashSet<>();
        try (PreparedStatement statement = selectPreparedStatement(sql, id);
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

    private PreparedStatement selectPreparedStatement(String sql, Object id)
            throws SQLException {
        PreparedStatement statement = getBaseConnectionKeeper().getConnection().prepareStatement(sql);
        statement.setObject(1, id);
        return statement;
    }

    /**
     * Метод возвращает элемент класса Class<T> по идентификатору, используя параметр - SQL запрос
     *
     * @param sql строка запроса
     * @param id  идентификатор сущности
     * @return элемент класса-аргумента
     */
    @SuppressWarnings("unchecked")
    protected <T> T find(String sql, Class<T> tClass, Object id) {
        T object = null;
        try (PreparedStatement statement = selectPreparedStatement(sql, id);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                if (ReflectionUtils.isPrimitiveOrWrapperType(tClass)) {
                    object = (T) rs.getObject(1);
                } else {
                    object = getEntityResultSet(rs, tClass);
                }
            }
        } catch (Exception exc) {
            String error = String.format("Error reading %s from DB by id = %s. Error: %s", tClass.getSimpleName(), id, exc);
            log.error(error);
            throw new RuntimeException(error);
        }
        return object;
    }

    /**
     * Метод возвращает элемент класса Class<T> по идентификатору, генерируя SQL запрос
     *
     * @param id идентификатор сущности
     * @return элемент класса-аргумента
     */
    protected <T> T find(Class<T> tClass, Object id) {
        if (tClass.getAnnotation(Entity.class) == null) return null;

        return find(sqlGeneration(tClass, true), tClass, id);
    }

    /**
     * Метод возвращает спсок элементов класса Class<T> по идентификатору,
     * используя параметр - SQL запрос
     *
     * @param sql строка запроса
     * @return список элементов класса-аргумента
     */
    protected <T> List<T> findAll(String sql, Class<T> tClass) {
        if (tClass.getAnnotation(Entity.class) == null) return null;
        List<T> entities = new ArrayList<>();
        try (Statement st = getBaseConnectionKeeper().getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                entities.add(getEntityResultSet(rs, tClass));
            }
        } catch (SQLException exc) {
            String error = String.format("Error finding list of element Class: %s + message: %s", tClass.getSimpleName(), exc);
            log.error(error);
            throw new RuntimeException(error);
        }
        return entities;
    }

    protected <T> List<T> findAll(Class<T> tClass) {
        if (tClass.getAnnotation(Entity.class) == null) return null;
        return findAll(sqlGeneration(tClass, false), tClass);
    }

    /**
     * Метод генерирует строку SQL запроса из класса-сущности, используя рефлексию
     *
     * @param tClass     класс для генерации запроса
     * @param requiredId требуется Id
     * @return строку запроса
     */
    private String sqlGeneration(Class<?> tClass, boolean requiredId) {
        String sql = requiredId ? classQueriesById.get(tClass) : classQueries.get(tClass);
        if (sql != null) {
            log.debug("Get SQL from the cash: {}", sql);
            return sql;
        }
        List<Field> fields = ReflectionUtils.getAllClassFields(tClass);
        String idFieldName = null;
        StringBuilder fieldNames = new StringBuilder();
        for (Field field : fields) {
            if (field.getAnnotation(OneToMany.class) != null &&
                    field.getAnnotation(OneToMany.class).fetch() == OneToMany.FetchType.LAZY) {
                continue;
            }
            String fieldAnnotatedName = DaoReflectionUtils.getFieldAnnotatedName(field);
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

    /**
     * Метод заполняет поля класса, помеченные аннотиацией FetchType
     *
     * @param field  поле класса, аннотированное FetchType
     * @param tClass класс сущности
     * @param object элемент класса Т
     */
    private <T> void setFetchField(Field field, Class<T> tClass, T object) {
        Object fieldObject;
        JoinTable joinTable = field.getAnnotation(JoinTable.class);
        String sql = sqlGenerationOneToMany(joinTable.name(),
                joinTable.joinColumns().name(), joinTable.inverseJoinColumns().name());
        fieldObject = findInversedColumnElements(DaoReflectionUtils.getGenericParameterField(field),
                sql, DaoReflectionUtils.getIdValueFromObject(object));
        field.setAccessible(true);
        try {
            field.set(object, fieldObject);
        } catch (IllegalAccessException e) {
            String error = String.format("Error reading Eager field from ResultSet: %s. Error: %s", tClass.getSimpleName(), e);
            log.error(error);
            throw new RuntimeException(error);
        }
    }

    /**
     * Метод выполнеят поиск полей, аннотированных FetchType.Eager и заполняет поля из БД
     *
     * @param tClass - класс сущности
     * @param object - объект сущности
     */
    private <T> void setEagerFields(Class<T> tClass, T object) {
        ReflectionUtils.getAllClassFields(tClass).stream()
                .filter(e -> e.getAnnotation(OneToMany.class) != null &&
                        e.getAnnotation(OneToMany.class).fetch() == OneToMany.FetchType.EAGER)
                .collect(Collectors.toList())
                .forEach(e -> setFetchField(e, tClass, object));
    }

    /**
     * Метод получает ResultSet, класс и коннект, используя рефлексию заполняет поля из ResultSet
     *
     * @param rs         ResultSet
     * @param tClass     класс сущности
     * @param connection коннект
     * @return прокси объект сущности
     */
    private <T> T getEntityResultSet(ResultSet rs, Class<T> tClass) {
        T object = getProxyWithLazyFieldInitializationEntity(tClass);
        List<Field> fields = ReflectionUtils.getAllClassFields(tClass);
        for (Field field : fields) {
            if ((field.getModifiers() & java.lang.reflect.Modifier.FINAL) == java.lang.reflect.Modifier.FINAL) {
                continue;
            }
            Object fieldObject = null;
            try {
                String name = DaoReflectionUtils.getFieldAnnotatedName(field);
                if (ReflectionUtils.isPrimitiveOrWrapperType(field.getType())) {
                    fieldObject = DaoReflectionUtils.getValueFromResultSet(rs, name);
                } else {
                    if (field.getAnnotation(ManyToOne.class) != null) {
                        fieldObject = find(sqlGeneration(field.getType(), true), field.getType(),
                                DaoReflectionUtils.getValueFromResultSet(rs, name));
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
        setEagerFields(tClass, object);
        return object;
    }
}
