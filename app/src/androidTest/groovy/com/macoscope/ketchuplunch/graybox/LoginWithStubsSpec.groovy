package com.macoscope.ketchuplunch.graybox

import android.content.Context
import android.content.Intent
import android.support.test.espresso.intent.Intents
import android.support.test.rule.ActivityTestRule
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.di.AccountModule
import com.macoscope.ketchuplunch.di.LoginModule
import com.macoscope.ketchuplunch.model.login.AccountPermission
import com.macoscope.ketchuplunch.model.login.AccountRepository
import com.macoscope.ketchuplunch.view.login.LoginActivity
import org.junit.Rule
import org.mockito.Mockito
import spock.lang.Specification

import static android.support.test.espresso.Espresso.onView
import static android.support.test.espresso.assertion.ViewAssertions.matches
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import static android.support.test.espresso.matcher.ViewMatchers.withId
import static org.mockito.BDDMockito.given

class LoginWithStubsSpec extends Specification {

    @Rule
    ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule(LoginActivity, true, false)

    AccountPermission accountPermissionMock = Mockito.mock(AccountPermission)
    AccountRepository accountRepositoryMock = Mockito.mock(AccountRepository)

    def setup() {
        Intents.init()
        LoginModule.accountPermission = null
        AccountModule.accountRepository = null
    }

    def cleanup() {
        Intents.release()
    }

    //TODO Exercise 5
    def "skips login screen when permission is granted and account is chosen by the user"() {
        given:
            stubHasPermissionCheck(true)

            //TODO Choose account using Espresso Intents

        when:
            loginActivityRule.launchActivity(new Intent(Intent.ACTION_MAIN))
        then:
            onView(withId(R.id.lunch_toolbar)).check(matches(isDisplayed()))
    }

    def "skips login screen when account is stored"() {
        given:
            stubAccountName("darek@macoscope.net")
        when:
            loginActivityRule.launchActivity(new Intent(Intent.ACTION_MAIN))
        then:
            onView(withId(R.id.lunch_toolbar)).check(matches(isDisplayed()))
    }

    private void stubAccountName(String accountName) {
        given(accountRepositoryMock.accountNameDefined).willReturn(accountName != null)
        given(accountRepositoryMock.accountName).willReturn(accountName)
        AccountModule.accountRepository = accountRepositoryMock
    }

    private void stubHasPermissionCheck(Boolean hasPermission) {
        given(accountPermissionMock.hasPermission(Mockito.isA(Context))).willReturn(hasPermission)
        LoginModule.accountPermission = accountPermissionMock
    }
}
