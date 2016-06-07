package com.macoscope.ketchuplunch.blackbox

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.rule.ActivityTestRule
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.di.ScriptModule
import com.macoscope.ketchuplunch.view.lunch.LunchActivity
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger
import org.junit.Rule
import spock.lang.Specification

import static android.support.test.espresso.Espresso.onView
import static android.support.test.espresso.matcher.ViewMatchers.withText
import static com.macoscope.ketchuplunch.RecyclerViewMatcher.withRecyclerView
import static io.appflate.restmock.utils.RequestMatchers.pathContains

class LunchMenuMockServerSpec extends Specification {

    @Rule
    ActivityTestRule<LunchActivity> loginActivityRule = new ActivityTestRule(LunchActivity, true, false)

    def setup() {
        RESTMockServerStarter.startSync(new AndroidAssetsFileParser(InstrumentationRegistry.getContext()), new AndroidLogger());
        ScriptModule.rootUrl = RESTMockServer.getUrl()
    }

    def "displays menu for Monday"() {
        given:
            RESTMockServer.whenPOST(pathContains("")).thenReturnFile(200, "menu.json");
        when:
            loginActivityRule.launchActivity(new Intent(Intent.ACTION_MAIN))
        then:
            onView(withRecyclerView(R.id.lunch_menu_list).atPositionOnView(1, R.id.lunch_menu_name)).check(ViewAssertions.matches(withText("Potato Soup")))
            onView(withRecyclerView(R.id.lunch_menu_list).atPositionOnView(1, R.id.lunch_menu_count)).check(ViewAssertions.matches(withText("2/4")))
    }
}
