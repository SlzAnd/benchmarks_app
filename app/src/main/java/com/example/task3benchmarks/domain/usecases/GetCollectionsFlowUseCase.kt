package com.example.task3benchmarks.domain.usecases;

import com.example.task3benchmarks.data.DataItem;
import com.example.task3benchmarks.domain.repositories.CalculationRepository;

import javax.inject.Inject;

import kotlinx.coroutines.flow.Flow;


class GetCollectionsFlowUseCase @Inject constructor(
    private val repository: CalculationRepository
) : FlowUseCase {

    override fun execute(size: Int): Flow<DataItem> {
        return repository.getCollectionsFlow(size)
    }
}
