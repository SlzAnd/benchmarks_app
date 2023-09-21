package com.example.task3benchmarks.kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task3benchmarks.data.DataItem
import com.example.task3benchmarks.domain.usecases.CalculationUseCasesKt
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppViewModelKt @Inject constructor(
    private val useCases: CalculationUseCasesKt
) : ViewModel() {

    // Variables
    var size = 0

    private val _isCalculating: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCalculating: LiveData<Boolean> = _isCalculating

    private val _collectionsLiveData: MutableLiveData<DataItem> = MutableLiveData()
    val collectionsLiveData: LiveData<DataItem> = _collectionsLiveData

    private val _mapsLiveData: MutableLiveData<DataItem> = MutableLiveData()
    val mapsLiveData: LiveData<DataItem> = _mapsLiveData

    var isCollectionsTab: Boolean = true
    var tabsFirstVisit = booleanArrayOf(true, true)

    val collectionsItems: MutableList<DataItem> = useCases.initialCollectionsData.execute()
    val mapsItems: MutableList<DataItem> = useCases.initialMapsData.execute()

    fun onEvent(event: AppEvent) {
        when (event) {
            is AppEvent.SetCollectionsItem -> {
                if (collectionsItems.contains(event.item)) {
                    collectionsItems[event.item.id] = event.item
                }
            }

            is AppEvent.SetMapsItem -> {
                if (mapsItems.contains(event.item)) {
                    mapsItems[event.item.id] = event.item
                }
            }

            AppEvent.StartCollectionsCalculation -> {
                // run progress bars
                collectionsItems.forEach { dataItem: DataItem ->
                    dataItem.isCalculating = true
                }
                _isCalculating.setValue(true)
                viewModelScope.launch {
                    useCases.collectionsFlow.execute(size)
                        .takeWhile { isCalculating.value!! }
                        .onCompletion { _isCalculating.value = false }
                        .collect { dataItem ->
                            _collectionsLiveData.postValue(dataItem)
                        }
                }
            }

            AppEvent.StartMapsCalculation -> {
                // run progress bars
                mapsItems.forEach { dataItem: DataItem ->
                    dataItem.isCalculating = true
                }
                _isCalculating.value = true

                viewModelScope.launch {
                    useCases.mapsFlow.execute(size)
                        .takeWhile { isCalculating.value!! }
                        .onCompletion { _isCalculating.value = false }
                        .collect { dataItem ->
                            _mapsLiveData.postValue(dataItem)
                        }
                }
            }

            AppEvent.StopCollectionsCalculation -> {
                _isCalculating.setValue(false)
            }

            AppEvent.StopMapsCalculation -> {
                _isCalculating.setValue(false)
            }

            is AppEvent.SetIsCalculating -> {
                _isCalculating.value = event.newValue
            }
        }
    }
}