package com.jeramtough.jtcomponent.utils;


import javax.annotation.*;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <pre>
 * Created on 2020/9/17 20:03
 * by @author WeiBoWen
 * </pre>
 */
public class ObjectsUtil {

    /**
     * 将Object转换成Map对象
     *
     * @param obj 要被转换的Object对象
     * @return 键值对的Map集合
     * @throws IllegalAccessException 不合法异常
     */
    public static Map<String, Object> getMapFromObject(Object obj) throws
            IllegalAccessException {
        Class<?> clazz = obj.getClass();

        if (obj instanceof Map) {
            try {
                Map<String, Object> map = (Map<String, Object>) obj;
                return map;
            }
            catch (Exception e) {
                throw new IllegalAccessException("不支持value的转换类型" + clazz.getName());
            }
        }

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
     *
     * @param o 要被判断的Object对象
     * @return 是则返回true
     */
    public static boolean isPrimaryType(Object o) {
        return isPrimaryType(o.getClass());
    }

    public static boolean isPrimaryType(Type type) {
        return isPrimaryType((Class<?>) type);
    }

    /**
     * 是不是基础数据类型，或者是基础数据类型的包装类
     *
     * @param clazz class
     * @return 是则返回true
     */
    public static boolean isPrimaryType(Class clazz) {
        if (clazz.isPrimitive()) {
            return true;
        }
        try {
            //int, double, float, long, short, boolean, byte, char
            if (clazz == Integer.class || clazz == Double.class || clazz == Float.class
                    || clazz == Long.class || clazz == Short.class || clazz == Boolean.class ||
                    clazz == Byte.class || clazz == Character.class || clazz == String.class) {
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


    public static boolean isBasicType(Object o) {
        return isBasicType(o.getClass());
    }

    public static boolean isBasicType(Type type) {
        return isBasicType((Class<?>) type);
    }

    /**
     * 是不是基础数据类型，或者是基础数据类型的包装类，或者是日期类
     *
     * @param clazz class
     * @return 是则返回true
     */
    public static boolean isBasicType(Class clazz) {
        if (clazz.isPrimitive()) {
            return true;
        }
        try {
            //int, double, float, long, short, boolean, byte, char,date,String,localDate,localDateTime
            if (clazz == Integer.class || clazz == Double.class || clazz == Float.class
                    || clazz == Long.class || clazz == Short.class || clazz == Boolean.class ||
                    clazz == Byte.class || clazz == Character.class || clazz == String.class
                    || clazz == Date.class || clazz == LocalDate.class || clazz == LocalDateTime.class) {
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
                        if ("".equals(map.get(propertyName)) || null == map.get(
                                propertyName)) {
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
     * 从lass中解析出包含get和set的field，并且这个field是基础数据类型
     * ，并且这个field的访问修饰符是private。
     *
     * @param clazz 被解析的class
     * @return 这个class包含的基础Field
     */
    public static List<Field> getFieldsIfIncludeGetSet(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();

            //如果修饰符不是private
            if ((Modifier.PRIVATE != field.getModifiers())) {
                continue;
            }

            //如果不是包括日期类在内的基础数据类型
            if (!isBasicType(field.getGenericType())) {
                continue;
            }

            String methodName1 = "get" + fieldName;
            String methodName2 = "set" + fieldName;
            String methodName3 = "";
            String methodName4 = "";
            if (fieldName.contains("is")) {
                methodName3 = fieldName;
                methodName4 = "set" + fieldName.replace("is", "");
            }


            int methodCount = 0;
            for (Method method : clazz.getDeclaredMethods()) {
                if (methodName1.equalsIgnoreCase(method.getName())) {
                    methodCount++;
                }
                if (methodName2.equalsIgnoreCase(method.getName())) {
                    methodCount++;
                }
                if (methodName3.equalsIgnoreCase(method.getName())) {
                    methodCount++;
                }
                if (methodName4.equalsIgnoreCase(method.getName())) {
                    methodCount++;
                }
            }

            //如果匹配上的方法数小于2就跳出
            if (methodCount < 2) {
                continue;
            }

            fieldList.add(field);
        }
        return fieldList;
    }

    /**
     * 得到变量的get方法和set方法
     *
     * @param clazz     反射类
     * @param fieldName 变量名
     * @return 返回get和set方法，可能为空
     */
    public static Method[] getGetSetMethodsOfField(Class<?> clazz, String fieldName) {
        Objects.requireNonNull(fieldName);


        String methodName1 = "get" + fieldName;
        String methodName2 = "set" + fieldName;
        //isXXX
        String methodName3 = "";
        //setXXX
        String methodName4 = "";

        if (fieldName.contains("is")) {
            methodName3 = fieldName;
            fieldName = fieldName.replace("is", "");
            methodName4 = "set" + fieldName.replace("is", "");
        }

        Method[] methods = new Method[2];
        for (Method method : clazz.getDeclaredMethods()) {

            if (methodName1.equalsIgnoreCase(method.getName())) {
                methods[0] = method;
            }
            if (methodName2.equalsIgnoreCase(method.getName())) {
                methods[1] = method;
            }
            if (methodName3.equalsIgnoreCase(method.getName())) {
                methods[0] = method;
            }
            if (methodName4.equalsIgnoreCase(method.getName())) {
                methods[1] = method;
            }
        }
        return methods;
    }

    /**
     * @param entity    entity
     * @param fieldName fieldName
     */
    public static void setTime(Object entity, String fieldName) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            Method[] getSetMethod =
                    ObjectsUtil.getGetSetMethodsOfField(entity.getClass(), fieldName);
            Method setMethod = null;
            if (getSetMethod[1] != null) {
                setMethod = getSetMethod[1];
                setMethod.setAccessible(true);
//                setMethod.invoke(entity, new Date());
            }

            if (field.getType() == Date.class && setMethod != null) {
                setMethod.invoke(entity, new Date());
            }
            else if (field.getType() == LocalDate.class && setMethod != null) {
                setMethod.invoke(entity, LocalDate.now());
            }
            else if (field.getType() == LocalDateTime.class && setMethod != null) {
                setMethod.invoke(entity, LocalDateTime.now());
            }
        }
        catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
        }
    }

    /**
     * @param entity    entity
     * @param fieldName fieldName
     * @param value     value
     */
    public static void setValue(Object entity, String fieldName, Object value) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            Method[] getSetMethod =
                    ObjectsUtil.getGetSetMethodsOfField(entity.getClass(), fieldName);
            Method setMethod = null;
            if (getSetMethod[1] != null) {
                setMethod = getSetMethod[1];
                setMethod.setAccessible(true);
//                setMethod.invoke(entity, new Date());
            }

            setMethod.invoke(entity, value);
        }
        catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
        }
    }


    /**
     * @param object data
     * @param clazz  toClass
     * @param <T>    toClassType
     * @return toClassData
     */
    public static <T> T transferToLongIntStr(Object object, Class<T> clazz) {
        Objects.requireNonNull(object);
        if (clazz == String.class) {
            return (T) String.valueOf(object);
        }
        else if (clazz == Integer.class) {
            return (T) Integer.valueOf(String.valueOf(object));
        }
        else if (clazz == Long.class) {
            return (T) Long.valueOf(String.valueOf(object));
        }
        else if (clazz == Boolean.class) {
            return (T) Boolean.valueOf(String.valueOf(object));
        }
        else {
            return (T) object;
        }
    }

}
