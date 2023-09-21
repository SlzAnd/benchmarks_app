package com.example.task3benchmarks.kotlin

import com.example.task3benchmarks.data.DataItem

sealed class AppEvent {
    data class SetCollectionsItem(val item: DataItem) : AppEvent()
    data class SetMapsItem(val item: DataItem) : AppEvent()
    data class SetIsCalculating(val newValue: Boolean) : AppEvent()
    data object StartCollectionsCalculation : AppEvent()
    data object StartMapsCalculation : AppEvent()
    data object StopCollectionsCalculation : AppEvent()
    data object StopMapsCalculation : AppEvent()
}
