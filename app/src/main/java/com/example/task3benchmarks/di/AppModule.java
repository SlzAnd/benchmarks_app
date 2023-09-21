package com.example.task3benchmarks.di;

import androidx.lifecycle.ViewModelProvider;

import com.example.task3benchmarks.data.CalculationRepositoryImplKt;
import com.example.task3benchmarks.domain.repositories.CalculationRepository;
import com.example.task3benchmarks.domain.usecases.CalculationUseCases;
import com.example.task3benchmarks.data.DataSetCreator;
import com.example.task3benchmarks.domain.usecases.CalculationUseCasesKt;
import com.example.task3benchmarks.domain.usecases.GetCollectionsFlowUseCase;
import com.example.task3benchmarks.domain.usecases.GetInitialCollectionsDataUseCase;
import com.example.task3benchmarks.domain.usecases.GetInitialMapsDataUseCase;
import com.example.task3benchmarks.domain.usecases.GetMapsFlowUseCase;
import com.example.task3benchmarks.kotlin.AppViewModelFactory;

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
    public CalculationUseCasesKt provideUseCasesKt(CalculationRepository repository) {
        return new CalculationUseCasesKt(
                new GetCollectionsFlowUseCase(repository),
                new GetMapsFlowUseCase(repository),
                new GetInitialCollectionsDataUseCase(),
                new GetInitialMapsDataUseCase()
        );
    }

    @Provides
    @Singleton
    public CalculationRepository provideCalculationRepository(DataSetCreator dataSetCreator) {
        return new CalculationRepositoryImplKt(dataSetCreator);
    }

    @Provides
    @Singleton
    public ViewModelProvider.Factory provideViewModelFactory(CalculationUseCasesKt useCases) {
        return new AppViewModelFactory(useCases);
    }
}
