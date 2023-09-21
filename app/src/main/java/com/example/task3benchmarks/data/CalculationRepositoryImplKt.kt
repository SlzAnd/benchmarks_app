package com.example.task3benchmarks.data

import com.example.task3benchmarks.domain.repositories.CalculationRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.LinkedList
import java.util.TreeMap
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

class CalculationRepositoryImplKt @Inject constructor(
    private val dataSetCreator: DataSetCreator
) : CalculationRepository {
    override fun getInitialCollectionsDataSet(): MutableList<DataItem> {
        return dataSetCreator.collectionsDataSet
    }

    override fun getInitialMapsDataSet(): MutableList<DataItem> {
        return dataSetCreator.mapsDataSet
    }

    override fun getCollectionsObservable(size: Int): Observable<DataItem> {
        return Observable
            .fromIterable(dataSetCreator.collectionsDataSet)
            .flatMap { item: DataItem ->
                Observable.just(item)
                    .observeOn(Schedulers.computation())
                    .map { dataItem: DataItem ->
                        dataItem.isCalculating = true
                        var time: Long = 0
                        when (dataItem.dataStructure) {
                            "ArrayList" -> {
                                val list = java.util.ArrayList<Int>(size)
                                for (i in 0 until size) {
                                    list.add(i)
                                }
                                time =
                                    calculateCollectionOperationDuration(list, dataItem.operation)
                            }

                            "LinkedList" -> {
                                val linkedList = LinkedList<Int>()
                                for (i in 0 until size) {
                                    linkedList.add(i)
                                }
                                time =
                                    calculateCollectionOperationDuration(
                                        linkedList,
                                        dataItem.operation
                                    )
                            }

                            "CopyOnWriteArrayList" -> {
                                val list: MutableList<Int> = java.util.ArrayList()
                                for (i in 0 until size) {
                                    list.add(i)
                                }
                                val COWList =
                                    CopyOnWriteArrayList(list)
                                time = calculateCollectionOperationDuration(
                                    COWList,
                                    dataItem.operation
                                )
                            }
                        }
                        dataItem.time = time
                        dataItem.isCalculating = false
                        dataItem
                    }
            }
    }

    override fun getMapsObservable(size: Int): Observable<DataItem> {
        return Observable
            .fromIterable(dataSetCreator.mapsDataSet)
            .flatMap { item: DataItem ->
                Observable.just(item)
                    .observeOn(Schedulers.computation())
                    .map { dataItem: DataItem ->
                        dataItem.isCalculating = true
                        val time: Long
                        val value = 1
                        if (dataItem.dataStructure == "HashMap") {
                            val map = java.util.HashMap<Int, Int>()
                            for (i in 0 until size) {
                                map[i] = value
                            }
                            time = calculateMapOperationDuration(map, dataItem.operation)
                        } else {
                            val treeMap = TreeMap<Int, Int>()
                            for (i in 0 until size) {
                                treeMap[i] = value
                            }
                            time = calculateMapOperationDuration(treeMap, dataItem.operation)
                        }
                        dataItem.time = time
                        dataItem.isCalculating = false
                        dataItem
                    }
            }
    }

    override fun getCollectionsFlow(size: Int): Flow<DataItem> {
        if (size > 0) {
            return flow {
                dataSetCreator.collectionsDataSet.map { dataItem ->
                    dataItem.isCalculating = true
                    val time = withContext(Dispatchers.Default) {
                        when (dataItem.dataStructure) {
                            "ArrayList" -> {
                                val list = ArrayList<Int>(size)
                                for (i in 0 until size) {
                                    list.add(i)
                                }
                                calculateCollectionOperationDuration(list, dataItem.operation)
                            }

                            "LinkedList" -> {
                                val linkedList = LinkedList<Int>()
                                for (i in 0 until size) {
                                    linkedList.add(i)
                                }
                                calculateCollectionOperationDuration(linkedList, dataItem.operation)
                            }

                            "CopyOnWriteArrayList" -> {
                                val list: MutableList<Int> = ArrayList()
                                for (i in 0 until size) {
                                    list.add(i)
                                }
                                val COWList = CopyOnWriteArrayList(list)
                                calculateCollectionOperationDuration(COWList, dataItem.operation)
                            }

                            else -> {
                                0L
                            }
                        }
                    }
                    dataItem.time = time
                    dataItem.isCalculating = false
                    emit(dataItem)
                }
            }
        } else return emptyFlow()
    }

    override fun getMapsFlow(size: Int): Flow<DataItem> {
        return flow {
            dataSetCreator.mapsDataSet.map { dataItem ->
                dataItem.isCalculating = true
                var time: Long = 0
                val value = 1
                withContext(Dispatchers.Default) {
                    if (dataItem.dataStructure == "HashMap") {
                        val map = HashMap<Int, Int>()
                        for (i in 0 until size) {
                            map[i] = value
                        }
                        time = calculateMapOperationDuration(map, dataItem.operation)
                    } else {
                        val treeMap = TreeMap<Int, Int>()
                        for (i in 0 until size) {
                            treeMap[i] = value
                        }
                        time = calculateMapOperationDuration(treeMap, dataItem.operation)
                    }
                }
                dataItem.time = time
                dataItem.isCalculating = false
                emit(dataItem)
            }
        }
    }


    private fun calculateCollectionOperationDuration(
        list: MutableList<Int>,
        operation: Operation
    ): Long {
        val startTime = System.currentTimeMillis()
        if (operation == Operation.ADD_START) {
            list.add(0, 0)
        } else if (operation == Operation.ADD_MIDDLE) {
            list.add(list.size / 2, 2)
        } else if (operation == Operation.ADD_END) {
            list.add(2)
        } else if (operation == Operation.SEARCH_BY_VALUE) {
            list.indexOf(5)
        } else if (operation == Operation.REMOVE_START) {
            list.removeAt(0)
        } else if (operation == Operation.REMOVE_MIDDLE) {
            list.removeAt(list.size / 2)
        } else if (operation == Operation.REMOVE_END) {
            list.removeAt(list.size - 1)
        }
        val endTime = System.currentTimeMillis()
        return endTime - startTime
    }

    private fun calculateMapOperationDuration(
        map: MutableMap<Int, Int>,
        operation: Operation
    ): Long {
        val key = 55555
        val startTime = System.currentTimeMillis()
        if (operation == Operation.ADD) {
            map[5] = 25
        } else if (operation == Operation.SEARCH_BY_KEY) {
            map[key]
        } else if (operation == Operation.REMOVE) {
            map.remove(key)
        }
        val endTime = System.currentTimeMillis()
        return endTime - startTime
    }
}