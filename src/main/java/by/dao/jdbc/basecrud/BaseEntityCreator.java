package by.dao.jdbc.basecrud;

import by.Utils.ReflectionUtils;
import by.Utils.annotations.Column;
import by.Utils.annotations.Id;
import by.Utils.annotations.JoinColumn;
import by.Utils.annotations.Table;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Базовый класс для создания сущностей в БД, используя JDBC
 *
 * <p> Этот класс на основе рефлексии считывает поля сущности
 * и генерирует SQL запрос. Также в качестве параметра метод
 * insert может принимать SQL запрос для дальнейшей обработки.
 * </>
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Slf4j
public class BaseEntityCreator extends BaseEntityDeleter {

    private PreparedStatement createPreparedStatement(String sql) throws SQLException {
        return getBaseConnectionKeeper().getConnection().prepareStatement(sql);
    }

    /**
     * Принимает в аргументах класс сущности и сущность, по сформированному
     * SQL производит вставку в БД.
     *
     * @param tClass класс сущности
     * @param entity сущность
     */
    protected <T> void persist(Class<T> tClass, T entity) {
        try (PreparedStatement statement = createPreparedStatement(sqlGeneration(tClass, entity))) {
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                DaoReflectionUtils.setValueOfId(entity, keys.getInt(1));
            }
        } catch (Exception exc) {
            log.error("Error inserting entity to DB: " + exc.getMessage());
            throw new RuntimeException(
                    "Error inserting entity to DB: " + exc.getMessage());
        }
    }

    void insert(String sql) {
        if (sql == null) return;
        try (PreparedStatement statement = createPreparedStatement(sql)) {
            statement.executeUpdate();
        } catch (Exception exc) {
            log.error("Error inserting entity to DB: " + exc.getMessage());
            throw new RuntimeException(
                    "Error inserting entity to DB: " + exc.getMessage());
        }
    }

    /**
     * Генерирует SQL запрос вида insert into ? values ?.
     *
     * @param tClass класс сущности
     * @param entity сущность
     * @return строку SQL запроса
     */
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

    /**
     * Метод создает карту полей сущности с зависимостью
     * имя поля - значение.
     *
     * @param tClass класс сущности
     * @param object сущность
     * @param getId  флаг - требуется ли заполнять ID поле сущности
     * @return карту fields name-value
     */
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
