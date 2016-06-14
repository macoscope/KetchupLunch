package com.macoscope.ketchuplunch

import android.content.Intent
import android.support.test.rule.ActivityTestRule
import com.macoscope.ketchuplunch.view.login.LoginActivity
import org.junit.Rule
import spock.lang.Specification

class LoginSpec extends Specification {

    @Rule
    ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule(LoginActivity, true, false)

    LoginFeature loginFeature = new LoginFeature(loginActivityRule)

    def setup() {
        loginFeature.setup()
    }

    def cleanup() {
        loginFeature.cleanup()
    }

    //TODO Exercice 7
    def "moves to lunch screen when permission is granted and account is chosen by the user"() {
        given:
            def account = "darek@macoscope.net"
        when:
            loginActivityRule.launchActivity(new Intent(Intent.ACTION_MAIN))

            //TODO implement test from LoginUiAutomatorSpec using Page Object
        then:
            lunchFeature.assertToolbarIsDisplayed()
    }

    def "skips login screen when permission is granted and account is chosen by the user"() {
        given:
            loginFeature.setAccountPermissionGranted(true)
            loginFeature.setChosenAccount("darek@macoscope.net")
        when:
            LunchFeature lunchFeature = loginFeature.launchActivity()
        then:
            lunchFeature.assertToolbarIsDisplayed()
    }
}
