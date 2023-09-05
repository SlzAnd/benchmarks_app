package com.example.task3benchmarks.data;

public class DataItem {
    private Operation operation;
    private String dataStructure;
    private Long time;
    public DataItem(Operation operation, String dataStructure, Long time) {
        this.operation = operation;
        this.dataStructure = dataStructure;
        this.time = time;
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

    public void setDataStructure(String dataStructure) {
        this.dataStructure = dataStructure;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
