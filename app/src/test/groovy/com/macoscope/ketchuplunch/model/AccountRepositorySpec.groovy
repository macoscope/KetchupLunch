package com.macoscope.ketchuplunch.model

import android.content.Context
import android.content.SharedPreferences
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.macoscope.ketchuplunch.model.repository.AccountRepository
import spock.lang.Specification

class AccountRepositorySpec extends Specification {

    def "check that accountName is not defined for new AccountRepository"() {
        given:
            Context contextStub = Stub(Context)
            GoogleAccountCredential googleAccountCredentialStub = Stub(GoogleAccountCredential)
            SharedPreferences sharedPreferencesStub = Stub(SharedPreferences)
            sharedPreferencesStub.getString("ACCOUNT_PREF_KEY", "") >> ""
            AccountRepository sut = new AccountRepository(contextStub,
                    googleAccountCredentialStub,
                    sharedPreferencesStub)
        when:
            boolean isAccountDefined = sut.isAccountNameDefined()
        then:
            isAccountDefined == false
    }

    def "check that accountName is defined"() {
        given:
            Context contextStub = Stub(Context)
            GoogleAccountCredential googleAccountCredentialStub = Stub(GoogleAccountCredential)
            SharedPreferences sharedPreferencesStub = Stub(SharedPreferences)
            sharedPreferencesStub.getString("ACCOUNT_PREF_KEY", "") >> "testName"
            AccountRepository sut = new AccountRepository(contextStub,
                googleAccountCredentialStub,
                sharedPreferencesStub)
        when:
            boolean isAccountDefined = sut.isAccountNameDefined()
        then:
            isAccountDefined == true
    }

    def "get accountName "() {
        given:
            Context contextStub = Stub(Context)
            GoogleAccountCredential googleAccountCredentialStub = Stub(GoogleAccountCredential)
            SharedPreferences sharedPreferencesStub = Stub(SharedPreferences)
            sharedPreferencesStub.getString("ACCOUNT_PREF_KEY", "") >> "testName"
            AccountRepository sut = new AccountRepository(contextStub,
                googleAccountCredentialStub,
                sharedPreferencesStub)
        when:
            String accountName = sut.getAccountName()
        then:
            accountName.equals("testName")
    }

}
