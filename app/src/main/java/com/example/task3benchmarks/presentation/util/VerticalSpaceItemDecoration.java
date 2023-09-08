package com.example.task3benchmarks.presentation.util;



import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpace;

    public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
        this.verticalSpace = (int) (verticalSpaceHeight * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = verticalSpace;
    }
}
