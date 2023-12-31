package com.example.task3benchmarks.data;


import com.example.task3benchmarks.domain.repositories.CalculationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlinx.coroutines.flow.Flow;

public class CalculationRepositoryImpl implements CalculationRepository {

    @Inject
    public CalculationRepositoryImpl(DataSetCreator dataSetCreator) {
        this.dataSetCreator = dataSetCreator;
    }

    DataSetCreator dataSetCreator;

    @Override
    public List<DataItem> getInitialCollectionsDataSet() {
        return dataSetCreator.getCollectionsDataSet();
    }

    @Override
    public List<DataItem> getInitialMapsDataSet() {
        return dataSetCreator.getMapsDataSet();
    }

    @Override
    public Observable<DataItem> getCollectionsObservable(int size) {
        return Observable
                .fromIterable(dataSetCreator.getCollectionsDataSet())
                .flatMap(item -> Observable.just(item)
                        .observeOn(Schedulers.computation())
                        .map(dataItem -> {
                            dataItem.setCalculating(true);
                            long time = 0;
                            if (Objects.equals(dataItem.getDataStructure(), "ArrayList")) {
                                ArrayList<Integer> list = new ArrayList<>(size);
                                for (int i = 0; i < size; i++) {
                                    list.add(i);
                                }
                                time = calculateCollectionOperationDuration(list, dataItem.getOperation());
                            } else if (Objects.equals(dataItem.getDataStructure(), "LinkedList")) {
                                LinkedList<Integer> linkedList = new LinkedList<>();
                                for (int i = 0; i < size; i++) {
                                    linkedList.add(i);
                                }
                                time = calculateCollectionOperationDuration(linkedList, dataItem.getOperation());
                            } else if (Objects.equals(dataItem.getDataStructure(), "CopyOnWriteArrayList")) {
                                List<Integer> list = new ArrayList<>();
                                for (int i = 0; i < size; i++) {
                                    list.add(i);
                                }
                                CopyOnWriteArrayList<Integer> COWList = new CopyOnWriteArrayList<>(list);
                                time = calculateCollectionOperationDuration(COWList, dataItem.getOperation());
                            }
                            dataItem.setTime(time);
                            dataItem.setCalculating(false);
                            return dataItem;
                        })
                );
    }

    @Override
    public Observable<DataItem> getMapsObservable(int size) {
        return Observable
                .fromIterable(dataSetCreator.getMapsDataSet())
                .flatMap(item -> Observable.just(item)
                        .observeOn(Schedulers.computation())
                        .map(dataItem -> {
                            dataItem.setCalculating(true);
                            long time;
                            Integer value = 1;
                            if (Objects.equals(dataItem.getDataStructure(), "HashMap")) {
                                HashMap<Integer, Integer> map = new HashMap<>();
                                for (int i = 0; i < size; i++) {
                                    map.put(i, value);
                                }
                                time = calculateMapOperationDuration(map, dataItem.getOperation());
                            } else {
                                TreeMap<Integer, Integer> treeMap = new TreeMap<>();
                                for (int i = 0; i < size; i++) {
                                    treeMap.put(i, value);
                                }
                                time = calculateMapOperationDuration(treeMap, dataItem.getOperation());
                            }
                            dataItem.setTime(time);
                            dataItem.setCalculating(false);
                            return dataItem;
                        })
                );
    }

    @Override
    public Flow<DataItem> getCollectionsFlow(int size) {
        return null;
    }

    @Override
    public Flow<DataItem> getMapsFlow(int size) {
        return null;
    }

    private Long calculateCollectionOperationDuration(List<Integer> list, Operation operation) {
        long startTime = System.currentTimeMillis();
        if (operation == Operation.ADD_START) {
            list.add(0, 0);
        } else if (operation == Operation.ADD_MIDDLE) {
            list.add(list.size() / 2, 2);
        } else if (operation == Operation.ADD_END) {
            list.add(2);
        } else if (operation == Operation.SEARCH_BY_VALUE) {
            list.indexOf(5);
        } else if (operation == Operation.REMOVE_START) {
            list.remove(0);
        } else if (operation == Operation.REMOVE_MIDDLE) {
            list.remove(list.size() / 2);
        } else if (operation == Operation.REMOVE_END) {
            list.remove(list.size() - 1);
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private Long calculateMapOperationDuration(Map<Integer, Integer> map, Operation operation) {
        Integer key = 55555;
        long startTime = System.currentTimeMillis();
        if (operation == Operation.ADD) {
            map.put(5, 25);
        } else if (operation == Operation.SEARCH_BY_KEY) {
            map.get(key);
        } else if (operation == Operation.REMOVE) {
            map.remove(key);
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
