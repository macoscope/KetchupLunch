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
import static com.macoscope.ketchuplunch.blackbox.UiAutomatorUtils.General.clickOnText
import static com.macoscope.ketchuplunch.blackbox.UiAutomatorUtils.General.clickOnTextSilent
import static com.macoscope.ketchuplunch.blackbox.UiAutomatorUtils.RuntimePermissionPopup.allowCurrentPermission

class LoginUiAutomatorSpec extends Specification {

    //Run on CI before tests:
    //adb shell pm clear com.macoscope.ketchuplunch

    //Other useful commands:
    //adb shell pm revoke com.macoscope.ketchuplunch android.permission.GET_ACCOUNTS

    @Rule
    ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule(LoginActivity, true, false)

    def "moves to lunch screen when permission is granted and account is chosen by the user"() {
        given:
            def uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        when:
            loginActivityRule.launchActivity(new Intent(Intent.ACTION_MAIN))

            clickOnTextSilent(uiDevice, "OK")
            allowCurrentPermission(uiDevice)
            clickOnText(uiDevice, "darek@macoscope.net")
            clickOnText(uiDevice, "OK")

        then:
            onView(withId(R.id.lunch_toolbar)).check(matches(isDisplayed()))
    }
}
