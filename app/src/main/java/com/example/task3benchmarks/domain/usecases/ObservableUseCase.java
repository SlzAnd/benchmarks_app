package com.example.task3benchmarks.domain.usecases;

import io.reactivex.rxjava3.core.Observable;

import com.example.task3benchmarks.data.DataItem;

public interface ObservableUseCase {

    public Observable<DataItem> execute(int size);
}
