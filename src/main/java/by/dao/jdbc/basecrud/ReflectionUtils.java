package by.dao.jdbc.basecrud;

import by.Utils.annotations.Column;
import by.Utils.annotations.Id;
import by.Utils.annotations.JoinColumn;
import by.Utils.annotations.ManyToOne;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Invoking methods of classes that extends Item with attributes
 * from controller.request.getParameters
 * Can use Commons BeanUtils instead this.
 *
 * @author Serg Shankunas
 */
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    static String getFieldAnnotatedName(Field field) {
        Column column = field.getAnnotation(Column.class);
        JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
        if (ReflectionUtils.isPrimitiveOrWrapperType(field.getType())) {
            return (column != null ? column.name() : field.getName());
        } else {
            if (field.getAnnotation(ManyToOne.class) != null) {
                return (joinColumn != null ? joinColumn.name() : field.getName());
            }
        }
        return field.getName();
    }

    static boolean isPrimitiveOrWrapperType(Class<?> clazz) {
        return clazz.isPrimitive() || clazz.equals(Boolean.class) ||
                clazz.equals(Integer.class) ||
                clazz.equals(Character.class) ||
                clazz.equals(Byte.class) ||
                clazz.equals(Short.class) ||
                clazz.equals(Double.class) ||
                clazz.equals(Long.class) ||
                clazz.equals(Float.class) ||
                clazz.equals(String.class);
    }

    static List<Field> getAllClassFields(Class<?> clazz) {
        return recursGetFields(new ArrayList<>(), clazz);
    }

    private static List<Field> recursGetFields(List<Field> fields, Class<?> clazz) {
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            recursGetFields(fields, clazz.getSuperclass());
        }
        return fields;
    }

    static <T> T getEntity(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    static <T> T getValueFromResultSet(ResultSet rs, String columnName) {
        try {
            return (T) rs.getObject(columnName);
        } catch (SQLException e) {
            return null;
        }
    }

    static Class<?> getGenericParameterField(Field field) {
        ParameterizedType genericSuperclass = (ParameterizedType) field.getGenericType();
        Type type = genericSuperclass.getActualTypeArguments()[0];
        return (type instanceof Class ? (Class<?>) type : (Class<?>) ((ParameterizedType) type).getRawType());
    }

    static  <T> Object getIdValueFromObject(Class<T> tClass, T object) {
        List<Field> fields = ReflectionUtils.getAllClassFields(tClass);
        for (Field field : fields) {
            if (field.getAnnotation(Id.class) != null) {
                field.setAccessible(true);
                try {
                    return field.get(object);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error getting Value from object: "+e);
                }
            }
        }
        return null;
    }

}
