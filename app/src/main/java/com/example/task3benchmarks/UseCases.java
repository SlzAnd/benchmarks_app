package com.example.task3benchmarks;

import com.example.task3benchmarks.data.DataItem;
import com.example.task3benchmarks.data.DataSetCreator;
import com.example.task3benchmarks.data.Operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UseCases {

    private static DataSetCreator dataSetCreator = new DataSetCreator();

    public static Observable<DataItem> getCollectionsObservable(int size) {
        return Observable
                .fromIterable(dataSetCreator.getCollectionsDataSet())
                .flatMap(item -> Observable.just(item)
                        .observeOn(Schedulers.computation())
                        .map(dataItem -> {
                            dataItem.setCalculating(true);
                            long time = 0;
                            List<Integer> fullList = new ArrayList<>();
                            for (int i = 0; i < size; i++) {
                                fullList.add(i);
                            }
                            if (Objects.equals(dataItem.getDataStructure(), "ArrayList")) {
                                ArrayList<Integer> list = new ArrayList<>(fullList);
                                time = calculateCollectionOperationDuration(list, dataItem.getOperation());
                            } else if (Objects.equals(dataItem.getDataStructure(), "LinkedList")) {
                                LinkedList<Integer> linkedList = new LinkedList<>(fullList);
                                time = calculateCollectionOperationDuration(linkedList, dataItem.getOperation());
                            } else if (Objects.equals(dataItem.getDataStructure(), "CopyOnWriteArrayList")) {
                                CopyOnWriteArrayList<Integer> COWList = new CopyOnWriteArrayList<>(fullList);
                                time = calculateCollectionOperationDuration(COWList, dataItem.getOperation());
                            }
                            dataItem.setTime(time);
                            dataItem.setCalculating(false);
                            return dataItem;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                );
    }

    public static Observable<DataItem> getMapsObservable(int size) {
        return Observable
                .fromIterable(dataSetCreator.getMapsDataSet())
                .flatMap(item -> Observable.just(item)
                        .observeOn(Schedulers.computation())
                        .map(dataItem -> {
                            dataItem.setCalculating(true);
                            long time;
                            StringBuilder stringValue = new StringBuilder();
                            if (Objects.equals(dataItem.getDataStructure(), "HashMap")) {
                                HashMap<String, String> map = new HashMap<>();
                                for (int i = 0; i < size; i++) {
                                    map.put(String.valueOf(i), stringValue.toString());
                                }
                                time = calculateMapOperationDuration(map, dataItem.getOperation());
                            } else {
                                TreeMap<String, String> treeMap = new TreeMap<>();
                                for (int i = 0; i < size; i++) {
                                    treeMap.put(String.valueOf(i), stringValue.toString());
                                }
                                time = calculateMapOperationDuration(treeMap, dataItem.getOperation());
                            }
                            dataItem.setTime(time);
                            dataItem.setCalculating(false);
                            return dataItem;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                );
    }

    private static Long calculateCollectionOperationDuration(List<Integer> list, Operation operation) {
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
        } else if (operation == Operation.REMOVE_END){
            list.remove(list.size() - 1);
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private static Long calculateMapOperationDuration(Map<String, String> map, Operation operation) {
        String key = String.valueOf(map.size() / 2);
        long startTime = System.currentTimeMillis();
        if (operation == Operation.ADD) {
            map.put("hello", "world");
        } else if (operation == Operation.SEARCH_BY_KEY) {
            map.get(key);
        } else if (operation == Operation.REMOVE) {
            map.remove(key);
        }
        long endTime = System.currentTimeMillis();
        return  endTime - startTime;
    }
}
