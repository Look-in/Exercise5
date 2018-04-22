package by.Utils;


import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Invoking methods of classes that extends Item with attributes
 * from servlet.request.getParameters
 * Can use Commons BeanUtils instead this.
 *
 * @author Serg Shankunas
 */
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static boolean isPrimitiveOrWrapperType(Class<?> clazz) {
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

    public static List<Field> getAllClassFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            getAllClassFields(fields, type.getSuperclass());
        }
        return fields;
    }


    public static  <T> T getEntity(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValueFromResultSet(ResultSet rs, String columnName) {
        try {
            return (T) rs.getObject(columnName);
        } catch (SQLException e) {
            return null;
        }
    }


}
