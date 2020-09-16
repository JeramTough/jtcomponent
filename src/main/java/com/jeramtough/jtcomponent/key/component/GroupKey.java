package com.jeramtough.jtcomponent.key.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created on 2019-08-08 21:32
 * by @author JeramTough
 */
public class GroupKey {

    private String keyString;
    private List<Integer> keys;

    public GroupKey() {
        keys = new ArrayList<>();
    }

    public GroupKey(List<Integer> keys) {
        this.keys = keys;
        toKeyString(keys);
    }

    public GroupKey append(int key) {
        keys.add(key);
        toKeyString(keys);
        return this;
    }

    //************************

    private void toKeyString(List<Integer> keys) {
        Collections.sort(keys);
        keyString = "key:";
        keys.forEach(integer -> keyString = keyString + integer);
    }

    public String getKeyString() {
        return keyString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GroupKey groupKey = (GroupKey) o;

        return Objects.equals(keyString, groupKey.keyString);
    }

    @Override
    public int hashCode() {
        return keyString != null ? keyString.hashCode() : 0;
    }
}
