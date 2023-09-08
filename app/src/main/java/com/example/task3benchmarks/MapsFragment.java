package com.example.task3benchmarks;


import static com.example.task3benchmarks.util.AppConstants.VERTICAL_ITEM_SPACE;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task3benchmarks.data.DataItem;
import com.example.task3benchmarks.data.DataSetCreator;
import com.example.task3benchmarks.databinding.FragmentCollectionsBinding;
import com.example.task3benchmarks.presentation.RecyclerViewAdapter;
import com.example.task3benchmarks.presentation.VerticalSpaceItemDecoration;

import java.util.List;


public class MapsFragment extends Fragment {

    private FragmentCollectionsBinding binding = null;
    private AppViewModel viewModel;

    //TODO: Dependency Injection!!
    private final DataSetCreator dataSetCreator = new DataSetCreator();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCollectionsBinding.inflate(inflater, container, false);

        View view = binding.collectionsGrid;
        setupRecyclerView(view);

        return binding.getRoot();
    }


    private void setupRecyclerView(View view) {
        Context context = requireContext();
        List<DataItem> mapsItems = dataSetCreator.getMapsDataSet();
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(mapsItems, viewModel);

        RecyclerView recyclerView = view.findViewById(R.id.collections_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);

        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        viewModel.getIsCalculating().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isCalculating) {
                if (isCalculating && !viewModel.isCollectionsTab) {
                    mapsItems.forEach(item -> item.setCalculating(true));
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });

        viewModel.getMapsLiveData().observe(getViewLifecycleOwner(), new Observer<DataItem>() {
            @Override
            public void onChanged(DataItem item) {
                if (item != null) {
                    int itemPosition = item.getId();
                    mapsItems.set(itemPosition, item);
                    recyclerViewAdapter.notifyItemChanged(itemPosition);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}