package com.jeramtough.jtcomponent.business.bean;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created on 2018-12-28 12:46
 * by @author JeramTough
 */
public class BusinessResult {

    private Boolean isSuccessful;
    private String message;
    private HashMap<String, Serializable> payloads;

    public BusinessResult() {
        this(false);
    }


    public BusinessResult(boolean isSuccessful) {
        this(isSuccessful, null);
    }

    public BusinessResult(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }

    public Boolean isSuccessful() {
        return isSuccessful;
    }

    public String getMessage() {
        return message;
    }

    public Serializable getPayload(String key) {
        return payloads.get(key);
    }

    public Integer getIntegerPayload(String key) {
        return null;
    }

    public String getStringPayload(String key) {
        return null;
    }

    public Double getDoublePayload(String key) {
        return null;
    }

    public Float getFloatPayload(String key) {
        return null;
    }

    public Boolean getBooleanPayload(String key) {
        return null;
    }

    public Long getLongPayload(String key) {
        return null;
    }

    public Character getCharPayload(String key) {
        return null;
    }

}
