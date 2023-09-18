package com.example.task3benchmarks

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.tabs.TabLayout
import com.google.common.truth.Truth.assertThat
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@SuppressLint("CheckResult")
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private val validSize = "1000000"

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun test_initialUIVisibility() {
        assertThat(onView(withId(R.id.dialog_container)).check(matches(isDisplayed())))
        assertThat(onView(withId(R.id.toolbar_title)).check(matches(withText(R.string.activity))))
        assertThat(onView(withId(R.id.tab_layout)).check(matches(matchCurrentTabTitle("Collections"))))
    }

    @Test
    fun test_changingTabsInTabLayout() {
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(1))
        assertThat(onView(withId(R.id.tab_layout)).check(matches(not(matchCurrentTabTitle("Collections")))))
        assertThat(onView(withId(R.id.tab_layout)).check(matches(matchCurrentTabTitle("Maps"))))
    }

    @Test
    fun test_successValidationShowCollectionsFragment() {
        enterValidData()
        //check: dialog - not visible, fragment content - visible
        assertThat(onView(withId(R.id.dialog_container)).check(matches(not(isDisplayed()))))
        assertThat(onView(withId(R.id.view_pager)).check(matches(isDisplayed())))
    }

    @Test
    fun test_startStopButtonChangingUI() {
        enterValidData()
        assertThat(onView(withId(R.id.start_stop_button)).check(matches(withText(R.string.stop_button_text))))
        onView(withId(R.id.start_stop_button)).perform(click())
        assertThat(onView(withId(R.id.start_stop_button)).check(matches(withText(R.string.start_button_text))))
    }

    @Test
    fun test_startStopInput() {
        enterValidData()
        assertThat(onView(withId(R.id.start_stop_input)).check(matches(withText(validSize))))
        onView(withId(R.id.start_stop_button)).perform(click())
        // click input field -> hide content and show dialog
        onView(withId(R.id.start_stop_input)).perform(click())
        assertThat(onView(withId(R.id.view_pager)).check(matches(not(isDisplayed()))))
        assertThat(onView(withId(R.id.dialog_container)).check(matches(isDisplayed())))
    }

    @Test
    fun test_firstVisitMapsTabShowsDialog() {
        enterValidData()
        //change tab
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(1))
        //check dialog visibility
        assertThat(onView(withId(R.id.dialog_container)).check(matches(isDisplayed())))
        assertThat(onView(withId(R.id.view_pager)).check(matches(not(isDisplayed()))))
    }

    @Test
    fun test_correctNumberOfCollectionsItems() {
        enterValidData()
        assertThat(onView(withId(R.id.collections_recycler_view)).check(hasItems(21)))
    }

    @Test
    fun test_correctNumberOfMapsItems() {
        // select maps tab
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(1))
        enterValidData()
        assertThat(onView(withId(R.id.maps_recycler_view)).check(hasItems(6)))
    }

    @Test
    fun test_checkRecycleViewItemText() {
        enterValidData()
        assertThat(
            onView(withId(R.id.collections_recycler_view))
                .check(
                    matches(
                        atPosition(
                            10,
                            hasDescendant(withText("Search by value LinkedList N/A ms"))
                        )
                    )
                )
        )
    }

    @Test
    fun test_checkRecycleViewItemProgressBarVisibility() {
        // start calculations - show progress bars
        enterValidData()
        assertThat(
            onView(withId(R.id.collections_recycler_view))
                .check(
                    matches(
                        atPosition(
                            9,
                            hasDescendant(allOf(withId(R.id.grid_item_progress_bar), isDisplayed()))
                        )
                    )
                )
        )

        // stop calculations - progress bar should be hidden
        onView(withId(R.id.start_stop_button)).perform(click())
        assertThat(
            onView(withId(R.id.collections_recycler_view))
                .check(
                    matches(
                        atPosition(
                            9,
                            hasDescendant(
                                allOf(
                                    withId(R.id.grid_item_progress_bar),
                                    not(isDisplayed())
                                )
                            )
                        )
                    )
                )
        )
    }

    private fun matchCurrentTabTitle(tabTitle: String): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description
                    ?.appendText("unable to match title of current selected tab with $tabTitle")
            }

            override fun matchesSafely(item: View?): Boolean {
                val tabLayout = item as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabLayout.selectedTabPosition)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index ${tabLayout.selectedTabPosition}"))
                        .build()

                return tabAtIndex.text.toString().contains(tabTitle, true)
            }
        }
    }

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() =
                allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }

    private fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    private fun enterValidData() {
        onView(withId(R.id.dialog_input)).perform(typeText(validSize))
        onView(withId(R.id.dialog_input)).perform(closeSoftKeyboard())
        onView(withId(R.id.calculate_button)).perform(click())
    }

    private fun hasItems(expectedCount: Int): RecyclerViewItemCountAssertion {
        return RecyclerViewItemCountAssertion(expectedCount)
    }

}