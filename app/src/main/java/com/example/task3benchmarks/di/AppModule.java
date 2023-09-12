package com.example.task3benchmarks.di;

import com.example.task3benchmarks.data.CalculationRepositoryImpl;
import com.example.task3benchmarks.domain.repositories.CalculationRepository;
import com.example.task3benchmarks.domain.usecases.CalculationUseCases;
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

    @Provides
    @Singleton
    public CalculationRepository provideCalculationRepository() {
        return new CalculationRepositoryImpl();
    }
}
