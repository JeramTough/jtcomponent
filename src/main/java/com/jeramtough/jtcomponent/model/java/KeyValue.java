package com.jeramtough.jtcomponent.model.java;

import java.io.Serializable;

/**
 * <pre>
 * Created on 2021/10/21 下午11:15
 * by @author WeiBoWen
 * </pre>
 */
public class KeyValue implements Serializable {

    private String key;
    private Serializable value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }
}
