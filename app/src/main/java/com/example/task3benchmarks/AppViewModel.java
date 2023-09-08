package com.example.task3benchmarks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.task3benchmarks.data.DataItem;
import com.example.task3benchmarks.use_case.CalculationUseCases;

import java.util.concurrent.atomic.AtomicInteger;


import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AppViewModel extends ViewModel {


    public AppViewModel() {
        MyApplication.getInstance().getAppComponent().inject(this);
    }

    @Inject
    CalculationUseCases useCases;

    // Variables
    public int size = 0;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<DataItem> collectionsLiveData = new MutableLiveData<>();
    private final MutableLiveData<DataItem> mapsLiveData = new MutableLiveData<>();
    public boolean isCollectionsTab = true;
    public boolean[] tabsFirstVisit = {true, true};
    private final MutableLiveData<Boolean>  isCalculating = new MutableLiveData<>(false);
    private final AtomicInteger ongoingCalculationsCount = new AtomicInteger(0);


    // Getters
    public LiveData<DataItem> getCollectionsLiveData() {
        return collectionsLiveData;
    };

    public LiveData<DataItem> getMapsLiveData() {
        return mapsLiveData;
    };

    public MutableLiveData<Boolean> getIsCalculating() {
        return isCalculating;
    }


    // Functions
    public void startCollectionsCalculation() {
        disposables.clear();
        isCalculating.setValue(true);
        Disposable disposable = useCases.getCollectionsObservable(size)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    ongoingCalculationsCount.incrementAndGet();
                    collectionsLiveData.postValue(result);
                    if (ongoingCalculationsCount.get() == 21) {
                        isCalculating.setValue(false);
                        ongoingCalculationsCount.set(0);
                    }
                });
        disposables.add(disposable);
    }

    public void stopCollectionsCalculation() {
        isCalculating.setValue(false);
        disposables.clear();
    }


    public void startMapsCalculations() {
        disposables.clear();
        isCalculating.setValue(true);
        Disposable disposable = useCases.getMapsObservable(size)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    ongoingCalculationsCount.incrementAndGet();
                    mapsLiveData.postValue(result);
                    if (ongoingCalculationsCount.get() == 6) {
                        isCalculating.setValue(false);
                        ongoingCalculationsCount.set(0);
                    }
                });
        disposables.add(disposable);
    }

    public void stopMapsCalculations() {
        disposables.clear();
        isCalculating.setValue(false);
    }
}
