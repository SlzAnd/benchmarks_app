package com.example.task3benchmarks.presentation;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task3benchmarks.AppViewModel;
import com.example.task3benchmarks.data.DataItem;
import com.example.task3benchmarks.databinding.FragmentCollectionsBinding;

import java.util.List;


public class CollectionsFragment extends BaseFragment {
    private FragmentCollectionsBinding binding = null;

    AppViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCollectionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = binding.collectionsRecyclerView;
        super.setupRecyclerView(
                viewModel.getCollectionsLiveData(),
                viewModel,
                recyclerView
        );
    }

    @Override
    List<DataItem> getItems() {
        return viewModel.getCollectionsItems();
    }

    @Override
    void setItem(DataItem item) {
        viewModel.setCollectionsItem(item);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}