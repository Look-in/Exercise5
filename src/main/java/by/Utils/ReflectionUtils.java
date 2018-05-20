package by.Utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Утилиты на основе Reflection API.
 *
 * @author Serg Shankunas
 */
@Slf4j
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static List<Field> getAllClassFields(Class<?> clazz) {
        return recursGetFields(new ArrayList<>(), clazz);
    }

    private static List<Field> recursGetFields(List<Field> fields, Class<?> clazz) {
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            recursGetFields(fields, clazz.getSuperclass());
        }
        return fields;
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

    public static <T> T getEntity(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            String error = String.format("Error creating new object in getEntityFromHttpRequest: %s", e);
            log.error(error);
            throw new RuntimeException(error);
        }
    }
}
