package com.jeramtough.jtcomponent.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JtClassUtil {
    private static final Map<Class<?>, List<Field>> CLASS_FIELD_CACHE = new ConcurrentHashMap();
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new IdentityHashMap(8);

    public JtClassUtil() {
    }


    public static Object getFieldValue(Object entity, String fieldName) {
        Class<?> cls = entity.getClass();
        Map fieldMaps = getFieldMap(cls);

        try {
            Field field = (Field) fieldMaps.get(fieldName);
            if (field == null) {
                String message = String.format("Error: NoSuchField in %s for %s.  Cause:",
                        cls.getSimpleName(), fieldName);
                throw new IllegalStateException(message);
            }
            field.setAccessible(true);
            return field.get(entity);
        }
        catch (ReflectiveOperationException var5) {
            String message = String.format("Error: Cannot read field in %s.  Cause:",
                    cls.getSimpleName());
            message = message + var5.getMessage();
            throw new IllegalStateException(message);
        }
    }

    public static Class<?> getSuperClassGenericType(final Class<?> clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            System.err.println(
                    String.format("Warn: %s's superclass not ParameterizedType", clazz.getSimpleName()));
            return Object.class;
        }
        else {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            if (index < params.length && index >= 0) {
                if (!(params[index] instanceof Class)) {
                    System.out.println(String.format("Warn: %s not set the actual class on superclass generic " +
                                    "parameter",
                            clazz.getSimpleName()));
                    return Object.class;
                }
                else {
                    return (Class) params[index];
                }
            }
            else {
                System.out.println(String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index,
                        clazz.getSimpleName(), params.length));
                return Object.class;
            }
        }
    }

    public static Map<String, Field> getFieldMap(Class<?> clazz) {
        List<Field> fieldList = getFieldList(clazz);

        boolean isNotEmpty = JtCollectionUtil.isNotEmpty(fieldList);

        return isNotEmpty ? (Map) fieldList.stream().collect(
                Collectors.toMap(Field::getName, (field) -> {
                    return field;
                })) : Collections.emptyMap();
    }

    public static List<Field> getFieldList(Class<?> clazz) {
        return Objects.isNull(clazz) ? Collections.emptyList() : (List) JtCollectionUtil.computeIfAbsent(
                CLASS_FIELD_CACHE, clazz, (k) -> {
                    Field[] fields = k.getDeclaredFields();
                    List<Field> superFields = new ArrayList();

                    for (Class currentClass = k.getSuperclass(); currentClass != null;
                         currentClass = currentClass.getSuperclass()) {
                        Field[] declaredFields = currentClass.getDeclaredFields();
                        Collections.addAll(superFields, declaredFields);
                    }

                    Map<String, Field> fieldMap = excludeOverrideSuperField(fields, superFields);
                    return (List) fieldMap.values().stream().filter((f) -> {
                        return !Modifier.isStatic(f.getModifiers());
                    }).filter((f) -> {
                        return !Modifier.isTransient(f.getModifiers());
                    }).collect(Collectors.toList());
                });
    }

    /**
     * @param clazz class
     * @deprecated
     * @return list
     */
    @Deprecated
    public static List<Field> doGetFieldList(Class<?> clazz) {
        if (clazz.getSuperclass() != null) {
            Map<String, Field> fieldMap = excludeOverrideSuperField(clazz.getDeclaredFields(),
                    getFieldList(clazz.getSuperclass()));
            return (List) fieldMap.values().stream().filter((f) -> {
                return !Modifier.isStatic(f.getModifiers());
            }).filter((f) -> {
                return !Modifier.isTransient(f.getModifiers());
            }).collect(Collectors.toList());
        }
        else {
            return Collections.emptyList();
        }
    }

    public static Map<String, Field> excludeOverrideSuperField(Field[] fields, List<Field> superFieldList) {
        Map<String, Field> fieldMap = (Map) Stream.of(fields).collect(
                Collectors.toMap(Field::getName, Function.identity(), (u, v) -> {
                    throw new IllegalStateException(String.format("Duplicate key %s", u));
                }, LinkedHashMap::new));
        superFieldList.stream().filter((field) -> {
            return !fieldMap.containsKey(field.getName());
        }).forEach((f) -> {
            Field var10000 = (Field) fieldMap.put(f.getName(), f);
        });
        return fieldMap;
    }

    public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalStateException("Class must not be null");
        }

        return clazz.isPrimitive() || PRIMITIVE_WRAPPER_TYPE_MAP.containsKey(clazz);
    }

    static {
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, Boolean.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, Byte.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, Character.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, Double.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, Float.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, Integer.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, Long.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, Short.TYPE);
    }
}
