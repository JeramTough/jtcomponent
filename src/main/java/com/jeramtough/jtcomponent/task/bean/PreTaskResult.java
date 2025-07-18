package com.jeramtough.jtcomponent.task.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务进行中，还没有出现结果的TaskResult对象
 * <p>
 * Created on 2018-12-28 12:46
 * by @author JeramTough
 */
public class PreTaskResult implements Serializable {

    private static final int INITIAL_PAYLOADS_MAP_CAPACITY = 3;

    private Boolean isSuccessful;
    private String message;
    private long timeConsuming;

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
    private Map<String, Object> objectPayloads;

    private Map<String, String[]> stringArrayPayloads;
    private Map<String, char[]> characterArrayPayloads;
    private Map<String, byte[]> byteArrayPayloads;
    private Map<String, short[]> shortArrayPayloads;
    private Map<String, int[]> integerArrayPayloads;
    private Map<String, long[]> longArrayPayloads;
    private Map<String, float[]> floatArrayPayloads;
    private Map<String, double[]> doubleArrayPayloads;
    private Map<String, boolean[]> booleanArrayPayloads;


    private Map<String, List> listPayloads;
    private Map<String, Map> mapPayloads;

    public PreTaskResult() {
    }


    public PreTaskResult(boolean isSuccessful) {
        this(isSuccessful, null);
    }

    public PreTaskResult(boolean isSuccessful, String message) {
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


    protected long getTimeConsuming() {
        return timeConsuming;
    }

    protected void setTimeConsuming(long timeConsuming) {
        this.timeConsuming = timeConsuming;
    }

    //
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

    public Object getObjectPayload(String key){
        return  getPayloadFromMap(key, objectPayloads);
    }

    //
    public int[] getIntArrayPayload(String key) {
        return (int[]) getPayloadFromMap(key, integerArrayPayloads);
    }

    public byte[] getByteArrayPayload(String key) {
        return (byte[]) getPayloadFromMap(key, byteArrayPayloads);
    }

    public short[] getShortArrayPayload(String key) {
        return (short[]) getPayloadFromMap(key, shortArrayPayloads);
    }

    public String[] getStringArrayPayload(String key) {
        return (String[]) getPayloadFromMap(key, stringArrayPayloads);
    }

    public double[] getDoubleArrayPayload(String key) {
        return (double[]) getPayloadFromMap(key, doubleArrayPayloads);
    }

    public float[] getFloatArrayPayload(String key) {
        return (float[]) getPayloadFromMap(key, floatArrayPayloads);
    }

    public boolean[] getBooleanArrayPayload(String key) {
        return (boolean[]) getPayloadFromMap(key, booleanArrayPayloads);
    }

    public long[] getLongArrayPayload(String key) {
        return (long[]) getPayloadFromMap(key, longArrayPayloads);
    }

    public char[] getCharArrayPayload(String key) {
        return (char[]) getPayloadFromMap(key, characterArrayPayloads);
    }

    public List getListPayload(String key) {
        return (List) getPayloadFromMap(key, listPayloads);
    }

    public Map getMapPayload(String key) {
        return (Map) getPayloadFromMap(key, mapPayloads);
    }


    //
    public Serializable getSerializablePayload(String key, Serializable defaultValue) {
        return (Serializable) getPayloadFromMap(key, defaultValue, serializablePayloads);
    }

    public Integer getIntegerPayload(String key, int defaultValue) {
        return (Integer) getPayloadFromMap(key, defaultValue, integerPayloads);
    }

    public Byte getBytePayload(String key, byte defaultValue) {
        return (Byte) getPayloadFromMap(key, defaultValue, bytePayloads);
    }

    public Short getShortPayload(String key, short defaultValue) {
        return (Short) getPayloadFromMap(key, defaultValue, shortPayloads);
    }

    public Long getLongPayload(String key, long defaultValue) {
        return (Long) getPayloadFromMap(key, defaultValue, longPayloads);
    }

    public String getStringPayload(String key, String defaultValue) {
        return (String) getPayloadFromMap(key, defaultValue, stringPayloads);
    }

    public Double getDoublePayload(String key, double defaultValue) {
        return (Double) getPayloadFromMap(key, defaultValue, doublePayloads);
    }

    public Float getFloatPayload(String key, float defaultValue) {
        return (Float) getPayloadFromMap(key, defaultValue, floatPayloads);
    }

    public Boolean getBooleanPayload(String key, boolean defaultValue) {
        return (Boolean) getPayloadFromMap(key, defaultValue, booleanPayloads);
    }

    public Character getCharPayload(String key, char defaultValue) {
        return (Character) getPayloadFromMap(key, defaultValue, characterPayloads);
    }

    //
    public int[] getIntArrayPayload(String key, int[] defaultValue) {
        return (int[]) getPayloadFromMap(key, defaultValue, integerArrayPayloads);
    }

    public byte[] getByteArrayPayload(String key, byte[] defaultValue) {
        return (byte[]) getPayloadFromMap(key, defaultValue, byteArrayPayloads);
    }

    public short[] getShortArrayPayload(String key, short[] defaultValue) {
        return (short[]) getPayloadFromMap(key, defaultValue, shortArrayPayloads);
    }

    public long[] getLongArrayPayload(String key, long[] defaultValue) {
        return (long[]) getPayloadFromMap(key, defaultValue, longArrayPayloads);
    }

    public String[] getStringArrayPayload(String key, String[] defaultValue) {
        return (String[]) getPayloadFromMap(key, defaultValue, stringArrayPayloads);
    }

    public double[] getDoubleArrayPayload(String key, double[] defaultValue) {
        return (double[]) getPayloadFromMap(key, defaultValue, doubleArrayPayloads);
    }

    public float[] getFloatArrayPayload(String key, float[] defaultValue) {
        return (float[]) getPayloadFromMap(key, defaultValue, floatArrayPayloads);
    }

    public boolean[] getBooleanArrayPayload(String key, boolean[] defaultValue) {
        return (boolean[]) getPayloadFromMap(key, defaultValue, booleanArrayPayloads);
    }

    public char[] getCharArrayPayload(String key, char[] defaultValue) {
        return (char[]) getPayloadFromMap(key, defaultValue, characterArrayPayloads);
    }

    public List getListPayload(String key, List defaultValue) {
        return (List) getPayloadFromMap(key, defaultValue, listPayloads);
    }

    public Map getMapPayload(String key, Map defaultValue) {
        return (Map) getPayloadFromMap(key, defaultValue, mapPayloads);
    }

    //put

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

    public void putPayload(String key, Character value) {
        if (characterPayloads == null) {
            characterPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        characterPayloads.put(key, value);
    }

    public void putPayload(String key, Integer value) {
        if (integerPayloads == null) {
            integerPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        integerPayloads.put(key, value);
    }

    public void putPayload(String key, Byte value) {
        if (bytePayloads == null) {
            bytePayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        bytePayloads.put(key, value);
    }

    public void putPayload(String key, Short value) {
        if (shortPayloads == null) {
            shortPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        shortPayloads.put(key, value);
    }

    public void putPayload(String key, Long value) {
        if (longPayloads == null) {
            longPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        longPayloads.put(key, value);
    }

    public void putPayload(String key, Double value) {
        if (doublePayloads == null) {
            doublePayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        doublePayloads.put(key, value);
    }

    public void putPayload(String key, Float value) {
        if (floatPayloads == null) {
            floatPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        floatPayloads.put(key, value);
    }

    public void putPayload(String key, Boolean value) {
        if (booleanPayloads == null) {
            booleanPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        booleanPayloads.put(key, value);
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

    //
    public void putPayload(String key, String[] value) {
        if (stringArrayPayloads == null) {
            stringArrayPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        stringArrayPayloads.put(key, value);
    }

    public void putPayload(String key, char[] value) {
        if (characterArrayPayloads == null) {
            characterArrayPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        characterArrayPayloads.put(key, value);
    }

    public void putPayload(String key, int[] value) {
        if (integerArrayPayloads == null) {
            integerArrayPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        integerArrayPayloads.put(key, value);
    }

    public void putPayload(String key, byte[] value) {
        if (byteArrayPayloads == null) {
            byteArrayPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        byteArrayPayloads.put(key, value);
    }

    public void putPayload(String key, short[] value) {
        if (shortArrayPayloads == null) {
            shortArrayPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        shortArrayPayloads.put(key, value);
    }

    public void putPayload(String key, long[] value) {
        if (longArrayPayloads == null) {
            longArrayPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        longArrayPayloads.put(key, value);
    }

    public void putPayload(String key, double[] value) {
        if (doubleArrayPayloads == null) {
            doubleArrayPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        doubleArrayPayloads.put(key, value);
    }

    public void putPayload(String key, float[] value) {
        if (floatArrayPayloads == null) {
            floatArrayPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        floatArrayPayloads.put(key, value);
    }

    public void putPayload(String key, boolean[] value) {
        if (booleanArrayPayloads == null) {
            booleanArrayPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        booleanArrayPayloads.put(key, value);
    }

    public void putPayload(String key, List value) {
        if (listPayloads == null) {
            listPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        listPayloads.put(key, value);
    }

    public void putPayload(String key, Map value) {
        if (mapPayloads == null) {
            mapPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        mapPayloads.put(key, value);
    }

    public void putPayloadForObject(String key, Object value) {
        if (objectPayloads == null) {
            objectPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        objectPayloads.put(key, value);
    }

    public void putPayloadForMap(String key, Map value) {
        if (mapPayloads == null) {
            mapPayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        mapPayloads.put(key, value);
    }

    public void putPayloadForSerializable(String key, Serializable value) {
        if (serializablePayloads == null) {
            serializablePayloads = new HashMap<>(INITIAL_PAYLOADS_MAP_CAPACITY);
        }
        serializablePayloads.put(key, value);
    }

    @Override
    public String toString() {
        return getDetail();
    }

    /**
     * getValue task detail and what same as toString();
     *
     * @return task detail
     */
    public String getDetail() {
        StringBuilder str = new StringBuilder();

        if (isSuccessful != null) {
            str.append("The task result is " + isSuccessful.toString().toUpperCase());
            str.append("\nThe task took " + timeConsuming + " millisecond");
        }


        if (message != null) {
            str.append(".\nThe message is  ( " + message + " )");
        }

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
                    valueTypeClass) || String.class.isAssignableFrom(
                    valueTypeClass) || Character.class.isAssignableFrom(valueTypeClass)) {
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