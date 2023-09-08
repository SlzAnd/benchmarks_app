package com.example.task3benchmarks.presentation.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task3benchmarks.AppViewModel;
import com.example.task3benchmarks.R;
import com.example.task3benchmarks.data.DataItem;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DataViewHolder> {

    private List<DataItem> items;
    private static AppViewModel viewModel;

    public RecyclerViewAdapter(List<DataItem> items, AppViewModel viewModel) {
        this.items = items;
        this.viewModel = viewModel;

        viewModel.getIsCalculating().observeForever(isCalculating -> {
            if (!isCalculating) {
                items.forEach(dataItem -> dataItem.setCalculating(false));
                notifyDataSetChanged();
            }
        });
    }


    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private DataItem dataItem;

        private int currentPosition = -1;


        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        TextView dataItemTextView = itemView.findViewById(R.id.data_item_textView);
        ProgressBar progressBar = itemView.findViewById(R.id.grid_item_progress_bar);

        public void setData(DataItem item, int position) {
            if (item.isCalculating()) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
            this.currentPosition = position;
            this.dataItem = item;
            String time = "N/A";
            if (item.getTime() != null) {
                time = item.getTime().toString();
            }
            StringBuilder text = new StringBuilder(item.getOperation().name)
                    .append(" ")
                    .append(item.getDataStructure())
                    .append(" ")
                    .append(time)
                    .append(" ms");
            dataItemTextView.setText(text);
        }
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int position) {
        DataItem item = items.get(position);
        dataViewHolder.setData(item, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
