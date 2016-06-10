package com.macoscope.ketchuplunch.blackbox

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.uiautomator.UiDevice
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.view.login.LoginActivity
import org.junit.Rule
import spock.lang.Specification

import static android.support.test.espresso.Espresso.onView
import static android.support.test.espresso.assertion.ViewAssertions.matches
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import static android.support.test.espresso.matcher.ViewMatchers.withId

class LoginUiAutomatorSpec extends Specification {

    //Run on CI before tests:
    //adb shell pm clear com.macoscope.ketchuplunch

    //Other useful commands:
    //adb shell pm revoke com.macoscope.ketchuplunch android.permission.GET_ACCOUNTS

    @Rule
    ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule(LoginActivity, true, false)

    //TODO Exercise 4
    def "moves to lunch screen when permission is granted and account is chosen by the user"() {
        given:
            def uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        when:
            loginActivityRule.launchActivity(new Intent(Intent.ACTION_MAIN))

            // TODO handle Runtime Permission popup and Account Chooser

        then:
            onView(withId(R.id.lunch_toolbar)).check(matches(isDisplayed()))
    }
}
