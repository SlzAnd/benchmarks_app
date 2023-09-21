package com.example.task3benchmarks.domain.repositories;

import io.reactivex.rxjava3.core.Observable;
import kotlinx.coroutines.flow.Flow;

import com.example.task3benchmarks.data.DataItem;

import java.util.List;

public interface CalculationRepository {

    public List<DataItem> getInitialCollectionsDataSet();

    public List<DataItem> getInitialMapsDataSet();

    public Observable<DataItem> getCollectionsObservable(int size);

    public Observable<DataItem> getMapsObservable(int size);

    public Flow<DataItem> getCollectionsFlow(int size);

    public Flow<DataItem> getMapsFlow(int size);
}
