package com.vlatrof.rickandmorty

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vlatrof.rickandmorty.presentation.screen.feature.MainActivity
import org.hamcrest.BaseMatcher
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GlobalUITest {

    private val PORTRAIT = SCREEN_ORIENTATION_USER_PORTRAIT
    private val LANDSCAPE = SCREEN_ORIENTATION_USER_LANDSCAPE

    @get : Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testGlobalUI() {
        onView(withId(R.id.srl_refresh)).perform(swipeUp())
        onView(withId(R.id.srl_refresh)).perform(swipeDown())
        onView(withId(R.id.srl_refresh)).perform(swipeDown())
        onView(withId(R.id.btn_filter)).perform(click())
        onView(withId(R.id.species_input)).perform(typeText("humanoid"))
        hideKeyboard()
        onView(withId(R.id.btn_apply)).perform(click())
        onView(withId(R.id.sv_search)).perform(click())
        typeInSearchView("adam")
        hideKeyboard()
        waitABit()
        clickOnFirstRecyclerViewItem(R.id.rv_list)
        onView(withId(R.id.sv_character_details)).perform(swipeUp())
        onView(withId(R.id.sv_character_details)).perform(swipeDown())
        pressBack()
        onView(
            withContentDescription(R.string.bottom_navigation_menu_locations_content_description)
        ).perform(click())
        swipeUp()
        requestOrientation(LANDSCAPE)
        requestOrientation(PORTRAIT)
        waitABit()
        swipeUp()
        swipeDown()
        clickOnFirstRecyclerViewItem(R.id.rv_list)
        swipeUp()
        clickOnFirstRecyclerViewItem(R.id.rv_character_list)
        pressBack()
        pressBack()
    }

    private fun requestOrientation(orientation: Int) {
        (orientation == LANDSCAPE || orientation == PORTRAIT).let {
            activityRule.scenario.onActivity {
                it.requestedOrientation = orientation
            }
        }
    }

    private fun hideKeyboard() {
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())
    }

    private fun typeInSearchView(text: String) {
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText(text))
    }

    private fun clickOnFirstRecyclerViewItem(rvId: Int) {
        onView(allOf(isDisplayed(), first(withParent(withId(rvId)))))
            .perform(click())
    }

    private fun <T> first(matcher: Matcher<T>): Matcher<T>? {
        return object : BaseMatcher<T>() {
            var isFirst = true
            override fun matches(item: Any): Boolean {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false
                    return true
                }
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("should return first matching item")
            }
        }
    }

    private fun waitABit(millis: Long = 1000L) {
        Thread.sleep(millis)
    }
}
