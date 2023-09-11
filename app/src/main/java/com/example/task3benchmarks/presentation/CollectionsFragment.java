package com.example.task3benchmarks.presentation;

import static com.example.task3benchmarks.util.AppConstants.VERTICAL_ITEM_SPACE;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.example.task3benchmarks.AppViewModel;
import com.example.task3benchmarks.MyApplication;
import com.example.task3benchmarks.R;
import com.example.task3benchmarks.data.DataItem;
import com.example.task3benchmarks.data.DataSetCreator;
import com.example.task3benchmarks.databinding.FragmentCollectionsBinding;
import com.example.task3benchmarks.presentation.util.RecyclerViewAdapter;
import com.example.task3benchmarks.presentation.util.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class CollectionsFragment extends Fragment {
    private FragmentCollectionsBinding binding = null;

    AppViewModel viewModel;

    List<DataItem> collectionsItems = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCollectionsBinding.inflate(inflater, container, false);

        View view = binding.collectionsGrid;

        setupRecyclerView(view);

        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setupRecyclerView(View view) {
        Context context = requireContext();

        collectionsItems = viewModel.getCollectionsItems();

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(collectionsItems, viewModel);

        RecyclerView recyclerView = view.findViewById(R.id.collections_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(context,
                3,
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        viewModel.getIsCalculating().observe(getViewLifecycleOwner(), isCalculating -> {
            if (isCalculating) {
                collectionsItems = viewModel.getCollectionsItems();
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getCollectionsLiveData().observe(getViewLifecycleOwner(), dataItem -> {
            if (dataItem != null) {
                int itemPosition = dataItem.getId();
                collectionsItems.set(itemPosition, dataItem);
                viewModel.setCollectionsItem(dataItem);
                recyclerViewAdapter.notifyItemChanged(itemPosition);
            }
        });
    }


    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}