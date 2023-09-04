package com.example.task3benchmarks.data;

public class DataItem {
    private String operation;
    private String dataStructure;
    private Integer time;
    public DataItem(String operation, String dataStructure, Integer time) {
        this.operation = operation;
        this.dataStructure = dataStructure;
        this.time = time;
    }


    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getDataStructure() {
        return dataStructure;
    }

    public void setDataStructure(String dataStructure) {
        this.dataStructure = dataStructure;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
