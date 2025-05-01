package com.example.automatingtextprocessing.model;

import java.util.*;

public class DataEntry {
    private String key;
    private String value;

    public DataEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    // getters and setters
    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataEntry)) return false;
        DataEntry entry = (DataEntry) o;
        return key.equals(entry.key) && value.equals(entry.value);
    }


    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return key + ": " + value;
    }
}
