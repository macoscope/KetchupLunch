package com.macoscope.ketchuplunch

import android.support.test.rule.ActivityTestRule
import com.macoscope.ketchuplunch.view.login.LoginActivity
import org.junit.Rule
import spock.lang.Specification

class LoginSpec extends Specification {

    @Rule
    ActivityTestRule<LoginActivity> loginActivity = new ActivityTestRule(LoginActivity)

    def "lunch login screen"() {
        expect:
            loginActivity.getActivity() instanceof LoginActivity
    }
}
