package com.example.task3benchmarks.domain.usecases

data class CalculationUseCasesKt(
    val collectionsFlow: GetCollectionsFlowUseCase,
    val mapsFlow: GetMapsFlowUseCase,
    val initialCollectionsData: GetInitialCollectionsDataUseCase,
    val initialMapsData: GetInitialMapsDataUseCase
)