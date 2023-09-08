package com.example.task3benchmarks;

import android.app.Application;

import com.example.task3benchmarks.di.AppComponent;
import com.example.task3benchmarks.di.AppModule;
import com.example.task3benchmarks.di.DaggerAppComponent;


public class MyApplication extends Application {

    private static MyApplication instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
