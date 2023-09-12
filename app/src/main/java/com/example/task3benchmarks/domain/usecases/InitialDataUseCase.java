package com.example.task3benchmarks.domain.usecases;

import com.example.task3benchmarks.data.DataItem;

import java.util.List;

public interface InitialDataUseCase {

    public List<DataItem> execute();
}
