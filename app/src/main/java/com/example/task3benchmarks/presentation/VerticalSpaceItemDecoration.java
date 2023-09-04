package com.example.task3benchmarks.presentation;



import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    public GridItemDecoration(int verticalSpaceHeight) {
        this.space = (int) (verticalSpaceHeight * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        outRect.bottom = space;
        outRect.left = space;
    }
}
