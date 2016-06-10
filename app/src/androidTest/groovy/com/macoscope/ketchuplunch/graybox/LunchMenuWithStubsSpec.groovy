package com.macoscope.ketchuplunch.graybox

import android.content.Intent
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.rule.ActivityTestRule
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.di.LunchModule
import com.macoscope.ketchuplunch.model.ScriptClient
import com.macoscope.ketchuplunch.view.lunch.LunchActivity
import org.junit.Rule
import org.mockito.Mockito
import spock.lang.Specification

import static android.support.test.espresso.Espresso.onView
import static android.support.test.espresso.matcher.ViewMatchers.withText
import static com.macoscope.ketchuplunch.RecyclerViewMatcher.withRecyclerView

class LunchMenuWithStubsSpec extends Specification {

    @Rule
    ActivityTestRule<LunchActivity> loginActivityRule = new ActivityTestRule(LunchActivity, true, false)

    ScriptClient scriptClientMock = Mockito.mock(ScriptClient)

    def setup() {
        LunchModule.scriptClient = scriptClientMock
    }

    //TODO Exercise 6
    def "displays menu for Monday"() {
        given:
            //TODO Return stubbed list of meals using Mockito
        when:
            loginActivityRule.launchActivity(new Intent(Intent.ACTION_MAIN))
        then:
            onView(withRecyclerView(R.id.lunch_menu_list).atPositionOnView(1, R.id.lunch_menu_name)).check(ViewAssertions.matches(withText("Potato Soup")))
            onView(withRecyclerView(R.id.lunch_menu_list).atPositionOnView(1, R.id.lunch_menu_count)).check(ViewAssertions.matches(withText("2/4")))
    }
}
