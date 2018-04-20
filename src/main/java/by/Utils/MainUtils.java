package by.Utils;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Invoking methods of classes that extends Item with attributes
 * from servlet.request.getParameters
 * Can use Commons BeanUtils instead this.
 *
 * @author Serg Shankunas
 */
public class MainUtils {

    private static <T> T convertInstanceOfObject(Object o) {
        try {
            return (T) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
        return fields;
    }

    public static <T> T getEntityResultSet(ResultSet rs, Class<T> tClass) {
        T object;
        try {
            object = tClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        for (Field field : getAllFields(new ArrayList<>(), tClass)) {
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
                    fieldObject = convertInstanceOfObject(rs.getObject(name));
                } else {
                    fieldObject = field.getType().newInstance();
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
