package com.example.task3benchmarks.data;

import java.util.ArrayList;
import java.util.List;

public class DataSetCreator {

    private List<String> collectionOperations = List.of(
            "Adding in the beginning",
            "Adding in the middle",
            "Adding in the end",
            "Search by value",
            "Removing in the beginning",
            "Removing in the middle",
            "Removing in the end"
    );

    private List<String> collections = List.of(
            "ArrayList",
            "LinkedList",
            "CopyOnWriteArrayList"
    );

    private List<String> maps = List.of(
            "TreeMap",
            "HashMap"
    );

    private List<String> mapsOperations = List.of(
            "Adding new",
            "Search by key",
            "Removing"
    );
    public List<DataItem> getCollectionsDataSet() {
        List<DataItem> items = new ArrayList<>();
        for (String collection: collections) {
            for (String operation: collectionOperations) {
                items.add(new DataItem(operation, collection, null));
            }
        }
        return items;
    }

    public List<DataItem> getMapsDataSet() {
        List<DataItem> items = new ArrayList<>();
        for (String collection: maps) {
            for (String operation: mapsOperations) {
                items.add(new DataItem(operation, collection, null));
            }
        }
        return items;
    }
}
