package com.jeramtough.jtcomponent.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Created on 2020/9/17 20:03
 * by @author WeiBoWen
 * </pre>
 */
public class ObjectsUtil {

    public static Map<String, Object> getMapFromObject(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        Map<String, Object> map = new HashMap<>(clazz.getDeclaredFields().length);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();

            String methodName = "get" + fieldName.substring(0, 1).toUpperCase();
            if (fieldName.length() > 1) {
                methodName = methodName + fieldName.substring(1, fieldName.length());
            }
            try {
                Method getMethod = clazz.getMethod(methodName, null);
                map.put(fieldName, getMethod.invoke(obj));
            }
            catch (NoSuchMethodException ignored) {
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static boolean isPrimaryType(Object o) {
        if (o.getClass().isPrimitive()) {
            return true;
        }
        try {
            //int, double, float, long, short, boolean, byte, char
            if (o.getClass() == Integer.class || o.getClass() == Double.class || o.getClass() == Float.class
                    || o.getClass() == Long.class || o.getClass() == Short.class || o.getClass() == Boolean.class ||
                    o.getClass() == Byte.class || o.getClass() == Character.class) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
    }
}
