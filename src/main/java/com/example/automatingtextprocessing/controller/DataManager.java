package com.example.automatingtextprocessing.controller;

import com.example.automatingtextprocessing.model.DataEntry;
import java.util.HashSet;
import java.util.Set;

public class DataManager {
    private final Set<DataEntry> entries = new HashSet<>();

    public boolean addEntry(DataEntry entry) {
        return entries.add(entry);
    }

    public boolean removeEntry(String key) {
        return entries.removeIf(entry ->
                entry.getKey().equalsIgnoreCase(key));
    }

    public Set<DataEntry> getAllEntries() {
        return new HashSet<>(entries);
    }

    public void clearAllEntries() {
        entries.clear();
    }
}