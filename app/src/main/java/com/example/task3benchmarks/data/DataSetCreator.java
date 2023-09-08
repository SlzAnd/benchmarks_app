package com.example.task3benchmarks.data;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DataSetCreator {

    @Inject
    public DataSetCreator() {
    }

    private List<Operation> collectionOperations = List.of(
            Operation.ADD_START,
            Operation.ADD_MIDDLE,
            Operation.ADD_END,
            Operation.SEARCH_BY_VALUE,
            Operation.REMOVE_START,
            Operation.REMOVE_MIDDLE,
            Operation.REMOVE_END
    );

    private final List<String> collections = List.of(
            "ArrayList",
            "LinkedList",
            "CopyOnWriteArrayList"
    );

    private final List<String> maps = List.of(
            "HashMap",
            "TreeMap"
    );

    private final List<Operation> mapsOperations = List.of(
            Operation.ADD,
            Operation.SEARCH_BY_KEY,
            Operation.REMOVE
    );


    public List<DataItem> getCollectionsDataSet() {
        List<DataItem> items = new ArrayList<>();
        int index = 0;
        for (String collection: collections) {
            for (Operation operation: collectionOperations) {
                items.add(new DataItem(index, operation, collection, null, false));
                index++;
            }
        }
        return items;
    }

    public List<DataItem> getMapsDataSet() {
        List<DataItem> items = new ArrayList<>();
        int index = 0;
        for (String map: maps) {
            for (Operation operation: mapsOperations) {
                items.add(new DataItem(index, operation, map, null, false));
                index++;
            }
        }
        return items;
    }
}
