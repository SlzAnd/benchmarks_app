package com.example.task3benchmarks;

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

import com.example.task3benchmarks.data.DataItem;
import com.example.task3benchmarks.data.DataSetCreator;
import com.example.task3benchmarks.databinding.FragmentCollectionsBinding;
import com.example.task3benchmarks.presentation.RecyclerViewAdapter;
import com.example.task3benchmarks.presentation.VerticalSpaceItemDecoration;

import java.util.List;

import javax.inject.Inject;


public class CollectionsFragment extends Fragment {
    private FragmentCollectionsBinding binding = null;

    @Inject
    DataSetCreator dataSetCreator;

    AppViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().getAppComponent().inject(this);
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

    private void setupRecyclerView(View view) {
        Context context = requireContext();
        List<DataItem> collectionsItems = dataSetCreator.getCollectionsDataSet();
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(collectionsItems, viewModel);

        RecyclerView recyclerView = view.findViewById(R.id.collections_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);

        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        viewModel.getIsCalculating().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isCalculating) {
                if (isCalculating && viewModel.isCollectionsTab) {
                    collectionsItems.forEach(item -> item.setCalculating(true));
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });


        viewModel.getCollectionsLiveData().observe(getViewLifecycleOwner(), new Observer<DataItem>() {
            @Override
            public void onChanged(DataItem dataItem) {
                if (dataItem != null) {
                    int itemPosition = dataItem.getId();
                    collectionsItems.set(itemPosition, dataItem);
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