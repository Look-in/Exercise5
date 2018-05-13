package by.Utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

@Slf4j
public class HttpServletRequestReflectionUtils {

    private HttpServletRequestReflectionUtils() {
    }

    private static String getParentClassString(String parentClassType, String childClassType) {
        StringBuilder str = new StringBuilder();
        if (parentClassType != null) {
            str.append(parentClassType);
        }
        str.append(childClassType);
        str.append(".");
        return str.toString();
    }

    @SuppressWarnings("unchecked")
    private static <T> T getValueFromHttpServletRequest(String value, Class<T> clazz) {
        if (value == null || "".equals(value)) return null;
        if (clazz.isAssignableFrom(Integer.class)) {
               return (T) Integer.valueOf(value);
           }
            if (clazz.isAssignableFrom(Boolean.class)) {
                return (T) Boolean.valueOf(value);
            }
            if (clazz.isAssignableFrom(Double.class)) {
                return (T) Double.valueOf(value);
            }
        return (T) value;
    }


    public static <T> T getEntityFromHttpRequest(Class<T> clazz, HttpServletRequest request, String parentClassType) {
        T object = ReflectionUtils.getEntity(clazz);
        List<Field> fields = ReflectionUtils.getAllClassFields(clazz);
        Object fieldObject;
        for (Field field : fields) {
            if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                continue;
            }
            try {
                if (ReflectionUtils.isPrimitiveOrWrapperType(field.getType())) { ;
                    String reqParam = parentClassType == null ? field.getName() : parentClassType.concat(field.getName());
                    fieldObject = getValueFromHttpServletRequest(request.getParameter(reqParam), field.getType());
                } else {
                    fieldObject = getEntityFromHttpRequest(field.getType(), request, getParentClassString(parentClassType, field.getName()));
                }
                field.setAccessible(true);
                field.set(object, fieldObject);
            } catch (Exception e) {
                String error = String.format("Error reading entity: %s from HttpRequest. Error: %s", clazz.getSimpleName(), e);
                log.error(error);
                throw new RuntimeException(error);
            }
        }
        return object;
    }
}