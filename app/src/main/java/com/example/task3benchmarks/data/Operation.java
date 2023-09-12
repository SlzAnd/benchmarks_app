package com.example.task3benchmarks.data;

public enum Operation {

    ADD_START("Adding in the beginning"),
    ADD_MIDDLE("Adding in the middle"),
    ADD_END("Adding in the end"),
    SEARCH_BY_VALUE("Search by value"),
    REMOVE_START("Removing in the beginning"),
    REMOVE_MIDDLE("Removing in the middle"),
    REMOVE_END("Removing in the end"),
    ADD("Adding new"),
    SEARCH_BY_KEY("Search by key"),
    REMOVE("Removing");

    public final String name;

    Operation(String name) {
        this.name = name;
    }
}
