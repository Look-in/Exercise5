package by.Utils;


import by.entity.event.Item;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class MainUtils {

    /**
     * Invoking methods of classes that extends Item with attributes
     * from servlet.request.getParameters
     * Can use Commons BeanUtils instead this.
     *
     * @author Serg Shankunas
     */

    private static Class[] params = {int.class, float.class, String.class};

    private static String upperCaseFirst(String value) {
        char[] array = value.toCharArray();
        array[0] = Character.toUpperCase(array[0]);
        return new String(array);
    }

    public static void setItemAttributes(Item item, Map<String, String[]> attributes) {
        String str;
        Method m;
        Object attribute;
        for (Map.Entry<String, String[]> entry : attributes.entrySet()) {
            for (Class param : params) {
                try {
                    m = item.getClass().getMethod(String.format("set%s", upperCaseFirst(entry.getKey())), param);
                    str = entry.getValue()[0];
                    if (str != null && !str.isEmpty()) {
                        if (param == int.class) attribute = Integer.valueOf(str);
                        else if (param == float.class) attribute = Float.valueOf(str);
                        else attribute = str;
                        m.invoke(item, attribute);
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                }
            }
        }
    }

}
