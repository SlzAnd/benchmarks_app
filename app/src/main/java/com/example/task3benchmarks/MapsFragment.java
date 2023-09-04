package com.example.task3benchmarks;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task3benchmarks.data.DataItem;
import com.example.task3benchmarks.data.DataSetCreator;
import com.example.task3benchmarks.databinding.FragmentCollectionsBinding;
import com.example.task3benchmarks.presentation.VerticalSpaceItemDecoration;

import java.util.List;


public class MapsFragment extends Fragment {

    private static final int VERTICAL_ITEM_SPACE = 11;
    private FragmentCollectionsBinding binding = null;

    //TODO: Dependency Injection!!
    private final DataSetCreator dataSetCreator = new DataSetCreator();
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
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(mapsItems);

        RecyclerView recyclerView = view.findViewById(R.id.collections_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);

        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }
}