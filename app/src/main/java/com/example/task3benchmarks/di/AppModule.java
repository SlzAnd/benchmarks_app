package com.example.task3benchmarks.di;

import com.example.task3benchmarks.use_case.CalculationUseCases;
import com.example.task3benchmarks.data.DataSetCreator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    public DataSetCreator provideDataSetCreator() {
        return new DataSetCreator();
    }

    @Provides
    @Singleton
    public CalculationUseCases provideUseCases() {
        return new CalculationUseCases();
    }
}
