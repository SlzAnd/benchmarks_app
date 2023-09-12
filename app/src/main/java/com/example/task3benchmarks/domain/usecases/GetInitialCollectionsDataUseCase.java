package com.example.task3benchmarks.domain.usecases;

import com.example.task3benchmarks.MyApplication;
import com.example.task3benchmarks.data.DataItem;
import com.example.task3benchmarks.domain.repositories.CalculationRepository;

import java.util.List;

import javax.inject.Inject;

public class GetInitialCollectionsDataUseCase implements InitialDataUseCase {

    @Inject
    public GetInitialCollectionsDataUseCase() {
        MyApplication.getInstance().getAppComponent().inject(this);
    }

    @Inject
    CalculationRepository repository;

    @Override
    public List<DataItem> execute() {
        return repository.getInitialCollectionsDataSet();
    }
}
