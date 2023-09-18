package com.example.task3benchmarks.domain.usecases;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

import com.example.task3benchmarks.MyApplication;
import com.example.task3benchmarks.data.DataItem;
import com.example.task3benchmarks.domain.repositories.CalculationRepository;

import javax.inject.Inject;

public class GetCollectionsObservableUseCase implements ObservableUseCase {

    @Inject
    public GetCollectionsObservableUseCase() {
        MyApplication.getInstance().getAppComponent().inject(this);
    }

    @Inject
    CalculationRepository repository;

    @Override
    public Observable<DataItem> execute(int size) {
        return repository.getCollectionsObservable(size).observeOn(AndroidSchedulers.mainThread());
    }
}
