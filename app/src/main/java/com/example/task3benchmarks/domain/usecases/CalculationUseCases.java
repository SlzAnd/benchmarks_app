package com.example.task3benchmarks.domain.usecases;


import com.example.task3benchmarks.MyApplication;

import javax.inject.Inject;

public class CalculationUseCases {

    @Inject
    public CalculationUseCases() {
        MyApplication.getInstance().getAppComponent().inject(this);
    }

    @Inject
    GetInitialCollectionsDataUseCase initialCollectionsDataUseCase;

    @Inject
    GetInitialMapsDataUseCase initialMapsDataUseCase;

    @Inject
    GetCollectionsObservableUseCase collectionsObservableUseCase;

    @Inject
    GetMapsObservableUseCase mapsObservableUseCase;

    @Inject
    GetCollectionsFlowUseCase collectionsFlowUseCase;

    @Inject
    GetMapsFlowUseCase mapsFlowUseCase;


    public GetInitialCollectionsDataUseCase getInitialCollectionsData() {
        return initialCollectionsDataUseCase;
    }

    public GetInitialMapsDataUseCase getInitialMapsData() {
        return initialMapsDataUseCase;
    }

    public GetCollectionsObservableUseCase getCollectionsObservable() {
        return collectionsObservableUseCase;
    }

    public GetMapsObservableUseCase getMapsObservable() {
        return mapsObservableUseCase;
    }

    public GetCollectionsFlowUseCase getCollectionsFlow() {
        return collectionsFlowUseCase;
    }

    public GetMapsFlowUseCase getMapsFlow() {
        return mapsFlowUseCase;
    }
}
