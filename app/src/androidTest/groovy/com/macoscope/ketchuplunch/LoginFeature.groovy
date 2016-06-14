package com.macoscope.ketchuplunch

import android.accounts.AccountManager
import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.intent.Intents
import android.support.test.rule.ActivityTestRule
import android.support.test.uiautomator.UiDevice
import com.macoscope.ketchuplunch.di.LoginModule
import com.macoscope.ketchuplunch.model.login.AccountPermission
import com.macoscope.ketchuplunch.view.login.LoginActivity
import groovy.transform.TypeChecked
import org.mockito.BDDMockito
import org.mockito.Mockito

import static android.support.test.espresso.intent.Intents.intending
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage

@TypeChecked
public class LoginFeature {

    AccountPermission accountPermissionMock = Mockito.mock(AccountPermission)
    ActivityTestRule<LoginActivity> loginTestRule
    UiDevice uiDevice

    LoginFeature(ActivityTestRule<LoginActivity> loginTestRule) {
        this.loginTestRule = loginTestRule
        this.uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    def setup() {
        Intents.init()
        LoginModule.accountPermission = null
    }

    def cleanup() {
        Intents.release()
    }
    //PRECONDITIONS:
    def setAccountPermissionGranted(Boolean hasPermission) {
        BDDMockito.given(accountPermissionMock.hasPermission(Mockito.isA(Context))).willReturn(hasPermission)
        LoginModule.accountPermission = accountPermissionMock
        return this
    }

    def setChosenAccount(String account) {
        intending(toPackage("com.google.android.gms")).respondWith(new Instrumentation.ActivityResult
                (Activity.RESULT_OK, new Intent().putExtra(AccountManager.KEY_ACCOUNT_NAME, account)))
        return this
    }

    //ACTIONS:
    def launchActivity() {
        loginTestRule.launchActivity(new Intent(Intent.ACTION_MAIN))
        return new LunchFeature()
    }

    //ASSERTIONS:

}
