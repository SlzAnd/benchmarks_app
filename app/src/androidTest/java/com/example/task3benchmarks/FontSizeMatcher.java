package com.example.task3benchmarks;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;

public class FontSizeMatcher extends BoundedMatcher<View, TextView> {

    private final float expectedSize;

    public FontSizeMatcher(float expectedSize) {
        super(TextView.class);
        this.expectedSize = expectedSize;
    }

    @Override
    protected boolean matchesSafely(TextView item) {
        if (item == null) {
            return false;
        }
        float pixels = ((TextView) item).getTextSize();
        float actualSize = Math.round(pixels / item.getResources().getDisplayMetrics().scaledDensity);
        return actualSize == expectedSize;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has fontSize: ");
        description.appendValue(expectedSize);
    }

}
