package com.example.task3benchmarks.di;

import com.example.task3benchmarks.AppViewModel;
import com.example.task3benchmarks.data.CalculationRepositoryImpl;
import com.example.task3benchmarks.domain.usecases.GetCollectionsFlowUseCase;
import com.example.task3benchmarks.domain.usecases.GetCollectionsObservableUseCase;
import com.example.task3benchmarks.domain.usecases.GetInitialCollectionsDataUseCase;
import com.example.task3benchmarks.domain.usecases.GetInitialMapsDataUseCase;
import com.example.task3benchmarks.domain.usecases.GetMapsFlowUseCase;
import com.example.task3benchmarks.domain.usecases.GetMapsObservableUseCase;
import com.example.task3benchmarks.kotlin.AppViewModelKt;
import com.example.task3benchmarks.kotlin.CollectionsFragmentKt;
import com.example.task3benchmarks.kotlin.EnterDialogKt;
import com.example.task3benchmarks.kotlin.MainActivityKt;
import com.example.task3benchmarks.kotlin.MapsFragmentKt;
import com.example.task3benchmarks.presentation.CollectionsFragment;
import com.example.task3benchmarks.presentation.MapsFragment;
import com.example.task3benchmarks.domain.usecases.CalculationUseCases;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {


    void inject(CalculationUseCases calculationUseCases);

    void inject(AppViewModel viewModel);

    void inject(CollectionsFragment collectionsFragment);

    void inject(MapsFragment mapsFragment);

    void inject(GetInitialCollectionsDataUseCase getInitialCollectionsDataUseCase);

    void inject(GetInitialMapsDataUseCase getInitialMapsDataUseCase);

    void inject(GetCollectionsObservableUseCase getCollectionsObservableUseCase);

    void inject(GetMapsObservableUseCase getMapsObservableUseCase);

    void inject(CalculationRepositoryImpl calculationRepository);

    void inject(GetCollectionsFlowUseCase getCollectionsFlowUseCase);

    void inject(GetMapsFlowUseCase getMapsFlowUseCase);

    void inject(AppViewModelKt viewModel);

    void inject(MainActivityKt mainActivityKt);

    void inject(EnterDialogKt enterDialogKt);

    void inject(CollectionsFragmentKt collectionsFragmentKt);

    void inject(MapsFragmentKt mapsFragmentKt);
}
