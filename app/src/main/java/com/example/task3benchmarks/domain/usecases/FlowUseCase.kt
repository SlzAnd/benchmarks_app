package com.example.task3benchmarks.domain.usecases

import com.example.task3benchmarks.data.DataItem

import kotlinx.coroutines.flow.Flow

interface FlowUseCase {
    fun execute(size: Int): Flow<DataItem>
}
