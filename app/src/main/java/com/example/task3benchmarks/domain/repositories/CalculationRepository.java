package com.example.task3benchmarks.domain.repositories;

import io.reactivex.rxjava3.core.Observable;

import com.example.task3benchmarks.data.DataItem;

import java.util.List;

public interface CalculationRepository {

    public List<DataItem> getInitialCollectionsDataSet();

    public List<DataItem> getInitialMapsDataSet();

    public Observable<DataItem> getCollectionsObservable(int size);

    public Observable<DataItem> getMapsObservable(int size);
}
