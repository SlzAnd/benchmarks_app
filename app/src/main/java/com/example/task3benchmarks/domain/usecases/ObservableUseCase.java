package com.example.task3benchmarks.domain.usecases;

import com.example.task3benchmarks.data.DataItem;

import io.reactivex.rxjava3.core.Observable;

public interface ObservableUseCase {

    public Observable<DataItem> execute(int size);
}
