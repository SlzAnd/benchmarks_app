package com.example.task3benchmarks.data;

import java.util.Objects;

public class DataItem {
    private int id;
    private Operation operation;
    private String dataStructure;
    private Long time;

    private boolean isCalculating;

    public DataItem(int id, Operation operation, String dataStructure, Long time, boolean isCalculating) {
        this.id = id;
        this.operation = operation;
        this.dataStructure = dataStructure;
        this.time = time;
        this.isCalculating = isCalculating;
    }


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDataStructure() {
        return dataStructure;
    }

    public Operation getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "id= " + id +
                ", operation= " + operation +
                ", dataStructure='" + dataStructure + '\'' +
                ", time= " + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataItem dataItem = (DataItem) o;
        return id == dataItem.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public boolean isCalculating() {
        return isCalculating;
    }

    public void setCalculating(boolean calculating) {
        isCalculating = calculating;
    }
}
