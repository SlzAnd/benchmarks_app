package com.example.task3benchmarks.di;

import com.example.task3benchmarks.AppViewModel;
import com.example.task3benchmarks.CollectionsFragment;
import com.example.task3benchmarks.MapsFragment;
import com.example.task3benchmarks.UseCases;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(UseCases useCases);
    void inject(AppViewModel viewModel);
    void inject(CollectionsFragment collectionsFragment);
    void inject(MapsFragment mapsFragment);
}
