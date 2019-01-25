package com.jeramtough.jtcomponent.task.bean.no;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2018-12-28 12:46
 * by @author JeramTough
 */
public class TaskResult {

    private static final int INITIAL_PAYLOADS_MAP_CAPACITY = 3;

    private Boolean isSuccessful;
    private String message;
    private Map<String, Serializable> serializablePayloads;
    private Map<String, String> stringPayloads;
    private Map<String, Character> characterPayloads;
    private Map<String, Byte> bytePayloads;
    private Map<String, Short> shortPayloads;
    private Map<String, Integer> integerPayloads;
    private Map<String, Long> longPayloads;
    private Map<String, Float> floatPayloads;
    private Map<String, Double> doublePayloads;
    private Map<String, Boolean> booleanPayloads;

    public TaskResult() {
    }


    public TaskResult(boolean isSuccessful) {
        this(isSuccessful, null);
    }

    public TaskResult(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    protected Boolean getSuccessful() {
        return isSuccessful;
    }

    protected Boolean isSuccessful() {
        return isSuccessful;
    }

    protected void setSuccessful(Boolean successful) {
        isSuccessful = successful;
    }

    public Serializable getSerializablePayload(String key) {
        return (Serializable) getPayloadFromMap(key, serializablePayloads);
    }

    public Integer getIntegerPayload(String key) {
        return (Integer) getPayloadFromMap(key, integerPayloads);
    }

    public Byte getBytePayload(String key) {
        return (Byte) getPayloadFromMap(key, bytePayloads);
    }

    public Short getShortPayload(String key) {
        return (Short) getPayloadFromMap(key, shortPayloads);
    }

    public String getStringPayload(String key) {
        return (String) getPayloadFromMap(key, stringPayloads);
    }

    public Double getDoublePayload(String key) {
        return (Double) getPayloadFromMap(key, doublePayloads);
    }

    public Float getFloatPayload(String key) {
        return (Float) getPayloadFromMap(key, floatPayloads);
    }

    public Boolean getBooleanPayload(String key) {
        return (Boolean) getPayloadFromMap(key, booleanPayloads);
    }

    public Long getLongPayload(String key) {
        return (Long) getPayloadFromMap(key, longPayloads);
    }

    public Character getCharPayload(String key) {
        return (Character) getPayloadFromMap(key, characterPayloads);
    }


    //
    public Serializable getSerializablePayload(String key, Serializable defaultValue) {
        return (Serializable) getPayloadFromMap(key, defaultValue, serializablePayloads);
    }

    public Integer getIntegerPayload(String key, int defaultValue) {
        return (Integer) getPayloadFromMap(key, integerPayloads);
    }

    public Byte getBytePayload(String key, byte defaultValue) {
        return (Byte) getPayloadFromMap(key, bytePayloads);
    }

    public Short getShortPayload(String key, short defaultValue) {
        return (Short) getPayloadFromMap(key, shortPayloads);
    }

    public Long getLongPayload(String key, long defaultValue) {
        return (Long) getPayloadFromMap(key, longPayloads);
    }

    public String getStringPayload(String key, String defaultValue) {
        return (String) getPayloadFromMap(key, stringPayloads);
    }

    public Double getDoublePayload(String key, double defaultValue) {
        return (Double) getPayloadFromMap(key, doublePayloads);
    }

    public Float getFloatPayload(String key, float defaultValue) {
        return (Float) getPayloadFromMap(key, floatPayloads);
    }

    public Boolean getBooleanPayload(String key, boolean defaultValue) {
        return (Boolean) getPayloadFromMap(key, booleanPayloads);
    }

    public Character getCharPayload(String key, char defaultValue) {
        return (Character) getPayloadFromMap(key, characterPayloads);
    }


    //
    public void putPayload(String key, Serializable value) {
        if (serializablePayloads == null) {
            serializablePayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        serializablePayloads.put(key, value);
    }

    public void putPayload(String key, String value) {
        if (stringPayloads == null) {
            stringPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        stringPayloads.put(key, value);
    }

    public void putPayload(String key, char value) {
        if (characterPayloads == null) {
            characterPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        characterPayloads.put(key, value);
    }

    public void putPayload(String key, int value) {
        if (integerPayloads == null) {
            integerPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        integerPayloads.put(key, value);
    }

    public void putPayload(String key, byte value) {
        if (bytePayloads == null) {
            bytePayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        bytePayloads.put(key, value);
    }

    public void putPayload(String key, short value) {
        if (shortPayloads == null) {
            shortPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        shortPayloads.put(key, value);
    }

    public void putPayload(String key, long value) {
        if (longPayloads == null) {
            longPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        longPayloads.put(key, value);
    }

    public void putPayload(String key, double value) {
        if (doublePayloads == null) {
            doublePayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        doublePayloads.put(key, value);
    }

    public void putPayload(String key, float value) {
        if (floatPayloads == null) {
            floatPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        floatPayloads.put(key, value);
    }

    public void putPayload(String key, boolean value) {
        if (booleanPayloads == null) {
            booleanPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        booleanPayloads.put(key, value);
    }


    public String getDetail() {
        StringBuilder str = new StringBuilder(
                "The business status is " + isSuccessful.toString().toUpperCase()
                        + " . The message is " + message + ".");
        str.append(getPayloadsDetail(serializablePayloads));
        str.append(getPayloadsDetail(stringPayloads));
        str.append(getPayloadsDetail(characterPayloads));
        str.append(getPayloadsDetail(bytePayloads));
        str.append(getPayloadsDetail(shortPayloads));
        str.append(getPayloadsDetail(integerPayloads));
        str.append(getPayloadsDetail(longPayloads));
        str.append(getPayloadsDetail(floatPayloads));
        str.append(getPayloadsDetail(doublePayloads));
        str.append(getPayloadsDetail(booleanPayloads));
        return str.toString();
    }

    //*********************************
    private Object getPayloadFromMap(String key, Map map) {
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    private Object getPayloadFromMap(String key, Object defaultValue, Map map) {
        Object value = getPayloadFromMap(key, map);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    private String getPayloadsDetail(Map map) {

        String str = "";
        if (map == null || map.size() == 0) {
            str = "";
        }
        else {

            Class valueTypeClass = map.values().iterator().next().getClass();
            String valueTypeName = "";
            if (Number.class.isAssignableFrom(
                    valueTypeClass) || String.class.isAssignableFrom(valueTypeClass)) {
                valueTypeName = valueTypeClass.getSimpleName();
            }
            else {
                valueTypeName = "Serializable";
            }
            str = str + "\n===========================\n" +
                    "The payloads of " + valueTypeName + " contains\n{\n";

            int index = 0;
            for (Object key : map.keySet()) {
                Object value = map.get(key);
                if (value == null) {
                    value = "NULL";
                }
                str = str + String.format("[%d] '%s':%s\n", index, key.toString(),
                        value.toString());
                index++;
            }
            str = str + "}";
        }
        return str;
    }
}