package com.example.task3benchmarks.presentation;

import static com.example.task3benchmarks.util.AppConstants.VERTICAL_ITEM_SPACE;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task3benchmarks.AppViewModel;
import com.example.task3benchmarks.data.DataItem;
import com.example.task3benchmarks.presentation.util.RecyclerViewAdapter;
import com.example.task3benchmarks.presentation.util.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFragment extends Fragment {

    private List<DataItem> items = new ArrayList<>();

    abstract List<DataItem> getItems();

    abstract void setItem(DataItem item);

    void setupRecyclerView(LiveData<DataItem> liveData, AppViewModel viewModel, RecyclerView recyclerView) {
        Context context = requireContext();

        items = getItems();

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(items);

        GridLayoutManager layoutManager = new GridLayoutManager(context,
                3,
                LinearLayoutManager.VERTICAL,
                false);

        if (recyclerView != null) {
            recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
        }

        viewModel.getIsCalculating().observe(getViewLifecycleOwner(), isCalculating -> {
            if (isCalculating) {
                items = getItems();
            }  else {
                items.forEach(dataItem -> dataItem.setCalculating(false));
            }
            recyclerViewAdapter.notifyDataSetChanged();
        });

        liveData.observe(getViewLifecycleOwner(), dataItem -> {
            if (dataItem != null) {
                int itemPosition = dataItem.getId();
                items.set(itemPosition, dataItem);
                setItem(dataItem);
                recyclerViewAdapter.notifyItemChanged(itemPosition);
            }
        });
    }
}
