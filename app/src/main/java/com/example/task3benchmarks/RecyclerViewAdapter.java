package com.example.task3benchmarks;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task3benchmarks.data.DataItem;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private List<DataItem> items;

    public DataAdapter(List<DataItem> items) {
        this.items = items;
    }


    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private DataItem dataItem;

        private int currentPosition = -1;


        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        TextView dataItemTextView = itemView.findViewById(R.id.data_item_textView);

        public void setData(DataItem item, int position) {
            this.currentPosition = position;
            this.dataItem = item;
            String time = "N/A";
            if (item.getTime() != null) {
                time = item.getTime().toString();
            }
            StringBuilder text = new StringBuilder(item.getOperation())
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
        Log.d("OLHA", ""+items.size());
        return items.size();
    }
}
