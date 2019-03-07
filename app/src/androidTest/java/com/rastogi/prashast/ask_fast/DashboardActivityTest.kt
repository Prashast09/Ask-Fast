package com.rastogi.prashast.ask_fast

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class DashboardActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(DashboardActivity::class.java)

    @Before
    fun init() {
        activityRule.getActivity()
            .getSupportFragmentManager().beginTransaction()
    }

    @Test
    fun testing() {
        Thread.sleep(5000)
        onView(withId(R.id.movie_list_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView
            .ViewHolder>(0, click())
        )

        Thread.sleep(3000)

    }

}