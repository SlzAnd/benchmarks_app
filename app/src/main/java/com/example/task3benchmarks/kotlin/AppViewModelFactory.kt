package com.example.task3benchmarks.kotlin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task3benchmarks.domain.usecases.CalculationUseCasesKt

class AppViewModelFactory(private val useCases: CalculationUseCasesKt) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModelKt::class.java)) {
            return AppViewModelKt(useCases) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
