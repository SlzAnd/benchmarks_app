package com.example.task3benchmarks.di;

import com.example.task3benchmarks.AppViewModel;
import com.example.task3benchmarks.presentation.CollectionsFragment;
import com.example.task3benchmarks.presentation.MapsFragment;
import com.example.task3benchmarks.use_case.CalculationUseCases;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(CalculationUseCases calculationUseCases);
    void inject(AppViewModel viewModel);
    void inject(CollectionsFragment collectionsFragment);
    void inject(MapsFragment mapsFragment);
}
