package com.example.task3benchmarks.di;

import com.example.task3benchmarks.CalculationsTest;
import com.example.task3benchmarks.di.AppComponent;
import com.example.task3benchmarks.di.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface TestAppComponent extends AppComponent {

    void inject(CalculationsTest calculationsTest);
}
