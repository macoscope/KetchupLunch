package com.macoscope.ketchuplunch.blackbox

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import com.macoscope.ketchuplunch.di.ScriptModule
import com.macoscope.ketchuplunch.view.lunch.LunchActivity
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger
import org.junit.Rule
import spock.lang.Specification

class LunchMenuMockServerSpec extends Specification {

    @Rule
    ActivityTestRule<LunchActivity> loginActivityRule = new ActivityTestRule(LunchActivity, true, false)

    def setup() {
        RESTMockServerStarter.startSync(new AndroidAssetsFileParser(InstrumentationRegistry.getContext()), new AndroidLogger());
        ScriptModule.rootUrl = RESTMockServer.getUrl()
    }

    //TODO Exercise 2 & 3
    def "displays menu for Monday"() {
        given:
            //TODO setup stub here
        when:
            loginActivityRule.launchActivity(new Intent(Intent.ACTION_MAIN))
        then:
            //TODO assert view data
            true
    }
}
