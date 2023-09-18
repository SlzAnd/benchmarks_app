package com.example.task3benchmarks

import android.annotation.SuppressLint
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.task3benchmarks.presentation.EnterDialog
import com.google.common.truth.Truth.assertThat;
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@SuppressLint("CheckResult")
@RunWith(AndroidJUnit4::class)
class EnterDialogFragmentTest {

    private lateinit var scenario: FragmentScenario<EnterDialog>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_Task3Benchmarks)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun test_displaysCorrectTextMessageInDialogTextView() {
        assertThat(
            onView(withId(R.id.dialog_text_view))
                .check(matches(withText(R.string.enter_dialog_collections_message)))
        )
        assertThat(
            onView(withId(R.id.dialog_text_view)).check(matches(hasFontSize(14f)))
        )
    }

    @Test
    fun test_displaysHintTextInDialogTextInput() {
        assertThat(
            onView(withId(R.id.dialog_input))
                .check(matches(withHint(R.string.dialog_input_collections_hint_text)))
        )
        assertThat(
            onView(withId(R.id.dialog_text_view)).check(matches(hasFontSize(14f)))
        )
    }

    @Test
    fun test_successValidationInput() {
        onView(withId(R.id.dialog_input)).perform(typeText("1_000_000"))
        onView(withId(R.id.dialog_input)).perform(closeSoftKeyboard())
        onView(withId(R.id.calculate_button)).perform(click())
        assertThat(
            onView(withId(R.id.dialog_input))
                .check(matches(not(hasErrorMessage("Error.You need enter elements count."))))
        )
    }

    @Test
    fun test_notSuccessValidationInput() {
        onView(withId(R.id.dialog_input)).perform(typeText("1000"))
        onView(withId(R.id.dialog_input)).perform(closeSoftKeyboard())
        onView(withId(R.id.calculate_button)).perform(click())
        assertThat(
            onView(withId(R.id.dialog_input_layout))
                .check(matches(hasErrorMessage("Error.You need enter elements count.")))
        )
    }


    private fun hasFontSize(fontSize: Float): FontSizeMatcher {
        return FontSizeMatcher(fontSize)
    }

    private fun hasErrorMessage(expectedErrorMessage: String): InputErrorMatcher {
        return InputErrorMatcher(expectedErrorMessage)
    }
}