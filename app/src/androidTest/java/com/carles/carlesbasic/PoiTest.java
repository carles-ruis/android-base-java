package com.carles.carlesbasic;

import com.carles.carlesbasic.presentation.PoiListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PoiTest {

    @Rule
    public ActivityTestRule<PoiListActivity> activityTestRule = new ActivityTestRule<>(PoiListActivity.class);

    @Test
    public void poiListAndDetails() {
        // poi list
        onView(withText(R.string.poilist_title)).check(matches(isDisplayed()));
        onData(withId(R.id.poilist_recyclerview)).atPosition(0).perform(click());
        // poi details
        onView(withText(R.string.poidetail_title)).check(matches(isDisplayed()));
        onView(withId(R.id.poidetail_address_textview)).check(matches(withText("")));
        onView(withId(R.id.poidetail_transport_textview)).check(matches(withText("")));
        pressBack();
        // poi list
        onView(withText(R.string.poilist_title)).check(matches(isDisplayed()));
    }
}
