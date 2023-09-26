package com.example.task3benchmarks.data

data class DataItem(
    val id: Int,
    val operation: Operation,
    val dataStructure: String,
    var time: Long?,
    var isCalculating: Boolean
)
