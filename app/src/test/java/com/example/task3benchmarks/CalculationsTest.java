package com.example.task3benchmarks;

import com.example.task3benchmarks.data.DataItem;
import com.example.task3benchmarks.data.DataSetCreator;
import com.example.task3benchmarks.domain.repositories.CalculationRepository;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;


public class CalculationsTest {

    @Inject
    DataSetCreator dataSetCreator;

    @Inject
    CalculationRepository repository;

    private TestObserver<DataItem> testObserver;
    private static final int SIZE = 1_000_000;

    @Before
    public void setup() {
        TestAppComponent testAppComponent = DaggerTestAppComponent.builder().appModule(new TestModule()).build();
        testAppComponent.inject(this);
        testObserver = new TestObserver<>();
    }

    @Test
    public void successfullyCreatedCollectionsDataSet() {
        assertThat(dataSetCreator.getCollectionsDataSet()).isNotEmpty();
    }

    @Test
    public void successfullyCreatedMapsDataSet() {
        assertThat(dataSetCreator.getMapsDataSet()).isNotEmpty();
    }

    @Test
    public void noErrorsInCollectionsObservable() {
        Observable<DataItem> observable = repository.getCollectionsObservable(SIZE);
        observable.subscribe(testObserver);
        testObserver.awaitCount(21);
        testObserver.assertNoErrors();
        testObserver.dispose();
    }

    @Test
    public void noErrorsInMapsObservable() {
        Observable<DataItem> observable = repository.getMapsObservable(SIZE);
        observable.subscribe(testObserver);
        testObserver.awaitCount(6);
        testObserver.assertNoErrors();
        testObserver.dispose();
    }

    @Test
    public void successfullyCompletedCollectionsObservable() {
        Observable<DataItem> observable = repository.getCollectionsObservable(SIZE);
        observable.subscribe(testObserver);
        testObserver.awaitCount(21);
        testObserver.assertComplete();
        testObserver.dispose();
    }

    @Test
    public void successfullyCompletedMapsObservable() {
        Observable<DataItem> observable = repository.getMapsObservable(SIZE);
        observable.subscribe(testObserver);
        testObserver.awaitCount(6);
        testObserver.assertComplete();
        testObserver.dispose();
    }

}
