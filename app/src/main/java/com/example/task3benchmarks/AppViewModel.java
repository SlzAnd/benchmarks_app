package com.example.task3benchmarks;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.task3benchmarks.data.DataItem;
import com.example.task3benchmarks.use_case.CalculationUseCases;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AppViewModel extends ViewModel {


    public AppViewModel() {
        MyApplication.getInstance().getAppComponent().inject(this);
        collectionsItems = useCases.getInitialCollectionsDataItems();
        mapsItems = useCases.getInitialMapsDataItems();
    }

    @Inject
    CalculationUseCases useCases;

    // Variables
    public int size = 0;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<DataItem> collectionsLiveData = new MutableLiveData<>();
    private final MutableLiveData<DataItem> mapsLiveData = new MutableLiveData<>();
    public boolean isCollectionsTab = true;
    public boolean[] tabsFirstVisit = {true, true};
    private final MutableLiveData<Boolean>  isCalculating = new MutableLiveData<>(false);
    private final List<DataItem> collectionsItems;
    private final List<DataItem> mapsItems;


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

    public  List<DataItem> getCollectionsItems() {
        return collectionsItems;
    }

    public void setCollectionsItem(DataItem item) {
        if (collectionsItems.contains(item)) {
            collectionsItems.set(item.getId(), item);
        };
    }

    public  List<DataItem> getMapsItems() {
        return mapsItems;
    }

    public void setMapsItem(DataItem item) {
        if (mapsItems.contains(item)) {
            mapsItems.set(item.getId(), item);
        };
    }


    // Functions
    public void startCollectionsCalculation() {
        disposables.clear();

        // run progress bars
        collectionsItems.forEach(dataItem -> dataItem.setCalculating(true));
        isCalculating.setValue(true);

        useCases.getCollectionsObservable(size)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(@NonNull DataItem item) {
                        collectionsLiveData.postValue(item);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        isCalculating.setValue(false);
                    }

                });
    }

    public void stopCollectionsCalculation() {
        isCalculating.setValue(false);
        disposables.clear();
    }


    public void startMapsCalculations() {
        disposables.clear();

        // run progress bars
        mapsItems.forEach(dataItem -> dataItem.setCalculating(true));
        isCalculating.setValue(true);

        useCases.getMapsObservable(size)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(@NonNull DataItem item) {
                        mapsLiveData.postValue(item);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        isCalculating.setValue(false);
                    }
                });
    }

    public void stopMapsCalculations() {
        disposables.clear();
        isCalculating.setValue(false);
    }


}
