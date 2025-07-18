package com.jeramtough.jtcomponent.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Created on 2024/11/27 下午10:19
 * by @author WeiBoWen
 * </pre>
 */
public class JtBeanUtil {

    /**
     * 将 JavaBean 转换为 Map
     * 优先使用 org.springframework.cglib.beans.BeanMap
     *
     * @param bean JavaBean 对象
     * @return 转换后的 Map
     */
    public static Map<String, Object> beanToMap(Object bean) {
        if (bean == null) {
            return new HashMap<>();
        }

        // 检查是否存在 BeanMap 类
        if (isBeanMapPresent()) {
            return convertUsingBeanMap(bean);
        }

        // 使用自定义方式转换
        return convertUsingIntrospector(bean);
    }

    /**
     * 检查当前项目是否包含 org.springframework.cglib.beans.BeanMap
     *
     * @return 是否存在 BeanMap 类
     */
    private static boolean isBeanMapPresent() {
        try {
            Class.forName("org.springframework.cglib.beans.BeanMap");
            return true;
        }
        catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 使用 org.springframework.cglib.beans.BeanMap 进行转换
     *
     * @param bean JavaBean 对象
     * @return 转换后的 Map
     */
    @SuppressWarnings("unchecked")
    private static Map<String, Object> convertUsingBeanMap(Object bean) {
        try {
            Class<?> beanMapClass = Class.forName("org.springframework.cglib.beans.BeanMap");
            Object beanMap = beanMapClass.getMethod("create", Object.class).invoke(null, bean);

            Map<String, Object> resultMap = new HashMap<>();
            for (Object key : ((Map<Object, Object>) beanMap).keySet()) {
                resultMap.put(key.toString(), ((Map<Object, Object>) beanMap).get(key));
            }
            return resultMap;
        }
        catch (Exception e) {
            throw new RuntimeException("Error using BeanMap to convert core to map", e);
        }
    }

    /**
     * 使用 Introspector 自定义转换方法
     *
     * @param bean JavaBean 对象
     * @return 转换后的 Map
     */
    private static Map<String, Object> convertUsingIntrospector(Object bean) {
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                String propertyName = propertyDescriptor.getName();
                if (!"class".equals(propertyName)) {
                    Method readMethod = propertyDescriptor.getReadMethod();
                    if (readMethod != null) {
                        Object value = readMethod.invoke(bean);
                        map.put(propertyName, value);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error using Introspector to convert core to map", e);
        }
        return map;
    }

}
