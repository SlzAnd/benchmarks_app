package com.example.task3benchmarks.presentation.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task3benchmarks.R;
import com.example.task3benchmarks.data.DataItem;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DataViewHolder> {

    private List<DataItem> items;

    public RecyclerViewAdapter(List<DataItem> items) {
        this.items = items;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {


        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView dataItemTextView = itemView.findViewById(R.id.data_item_text_view);
        ProgressBar progressBar = itemView.findViewById(R.id.grid_item_progress_bar);

        public void setData(DataItem item) {
            if (item.isCalculating()) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
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
        dataViewHolder.setData(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
