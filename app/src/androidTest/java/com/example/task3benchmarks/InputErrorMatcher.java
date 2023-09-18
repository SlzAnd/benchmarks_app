package com.example.task3benchmarks;

import android.view.View;

import androidx.test.espresso.matcher.BoundedMatcher;

import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;

public class InputErrorMatcher extends BoundedMatcher<View, TextInputLayout> {

    private final String expectedErrorMessage;

    public InputErrorMatcher(String expectedErrorMessage) {
        super(TextInputLayout.class);
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Override
    protected boolean matchesSafely(TextInputLayout item) {
        if (item == null) {
            return false;
        }

        CharSequence error = item.getError();
        if (error == null) {
            return false;
        }
        String hint = error.toString();

        return expectedErrorMessage.equals(hint);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has error message: ");
        description.appendValue(expectedErrorMessage);
    }
}
