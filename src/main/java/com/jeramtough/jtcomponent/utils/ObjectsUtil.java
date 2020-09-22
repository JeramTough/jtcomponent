package com.jeramtough.jtcomponent.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
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

    /**
     * 将Object转换成Map对象
     * @param obj 要被转换的Object对象
     * @return 键值对的Map集合
     * @throws IllegalAccessException 不合法异常
     */
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

    /**
     * 是不是基础数据类型，或者是基础数据类型的包装类
     * @param o 要被判断的Object对象
     * @return 是则返回true
     */
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


    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param type 要转化的类型
     * @param map  包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     */
    public static Object convertMap(Class type, Map map) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
            Object obj = type.newInstance(); // 创建 JavaBean 对象

            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                // propertyName = convertObjNameToFieldName(propertyName).toUpperCase();
                if (map.containsKey(propertyName)) {
                    try {
                        // 全部转换类型
                        Object value = null;
                        if ("".equals(map.get(propertyName)) || null == map.get(propertyName)) {
                            value = "";
                        }
                        else {
                            value = String.valueOf(value);
                        }
                        Object[] args = new Object[1];
                        args[0] = value;
                        descriptor.getWriteMethod().invoke(obj, args);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return obj;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转换对象名：把表对象名或字段对象名转成字段名，比如itemName转成item_name
     *
     * @param srcName
     * @return
     */
    public static String convertObjNameToFieldName(String srcName) {
        String result = "";
        if (srcName == null) {
            srcName = "";
        }
        if (srcName.equals("")) {
            return "";
        }

        for (int i = 0; i < srcName.length(); i++) {
            if (srcName.charAt(i) >= 65 && srcName.charAt(i) <= 90) {
                result += "_" + srcName.substring(i, i + 1).toLowerCase();
            }
            else {
                result += srcName.substring(i, i + 1);
            }
        }
        return result;
    }
}
