package com.example.task3benchmarks.kotlin

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task3benchmarks.data.DataItem
import com.example.task3benchmarks.presentation.util.RecyclerViewAdapter
import com.example.task3benchmarks.presentation.util.VerticalSpaceItemDecoration
import com.example.task3benchmarks.util.AppConstants

abstract class BaseFragmentKt : Fragment() {

    private var items: MutableList<DataItem> = ArrayList()

    abstract fun getItems(): MutableList<DataItem>

    abstract fun setItem(item: DataItem)

    open fun setupRecyclerView(
        liveData: LiveData<DataItem>,
        viewModel: AppViewModelKt,
        recyclerView: RecyclerView?
    ) {
        val context = requireContext()
        items = getItems()
        val recyclerViewAdapter = RecyclerViewAdapter(items)
        val layoutManager = GridLayoutManager(
            context,
            3,
            LinearLayoutManager.VERTICAL,
            false
        )
        if (recyclerView != null) {
            recyclerView.addItemDecoration(VerticalSpaceItemDecoration(AppConstants.VERTICAL_ITEM_SPACE))
            recyclerView.adapter = recyclerViewAdapter
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = layoutManager
        }

        viewModel.isCalculating.observe(viewLifecycleOwner) { isCalculating: Boolean ->
            if (isCalculating) {
                items = getItems()
            } else {
                items.forEach { dataItem: DataItem ->
                    dataItem.isCalculating = false
                }
            }
            recyclerViewAdapter.notifyDataSetChanged()
        }

        liveData.observe(
            viewLifecycleOwner
        ) { dataItem: DataItem? ->
            if (dataItem != null) {
                val itemPosition = dataItem.id
                items[itemPosition] = dataItem
                setItem(dataItem)
                recyclerViewAdapter.notifyItemChanged(itemPosition)
            }
        }

    }
}