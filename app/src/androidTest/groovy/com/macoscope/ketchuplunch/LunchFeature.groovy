package com.macoscope.ketchuplunch

import static android.support.test.espresso.Espresso.onView
import static android.support.test.espresso.assertion.ViewAssertions.matches
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import static android.support.test.espresso.matcher.ViewMatchers.withId

public class LunchFeature {

    void assertToolbarIsDisplayed() {
        onView(withId(R.id.lunch_toolbar)).check(matches(isDisplayed()))
    }

}
