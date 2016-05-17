package com.macoscope.ketchuplunch.model

import android.content.Context
import android.content.SharedPreferences
import com.macoscope.ketchuplunch.model.login.AccountRepository
import com.macoscope.ketchuplunch.model.login.GoogleCredentialWrapper
import spock.lang.Specification

class AccountRepositorySpec extends Specification {

    Context contextStub = Stub(Context)
    SharedPreferences sharedPreferencesStub = Stub(SharedPreferences)
    GoogleCredentialWrapper googleCredentialWrapperStub = Stub(GoogleCredentialWrapper)

    def "check that accountName is not defined for empty shared preferences"() {
        given:
            sharedPreferencesStub.getString("ACCOUNT_PREF_KEY", "") >> ""
            AccountRepository sut = new AccountRepository(contextStub,
                    googleCredentialWrapperStub,
                    sharedPreferencesStub)
        when:
            boolean isAccountDefined = sut.isAccountNameDefined()
        then:
            isAccountDefined == false
    }

    def "check that accountName is defined for not empty shared preferences"() {
        given:
            sharedPreferencesStub.getString("ACCOUNT_PREF_KEY", "") >> "testName"
            AccountRepository sut = new AccountRepository(contextStub,
                    googleCredentialWrapperStub,
                sharedPreferencesStub)
        when:
            boolean isAccountDefined = sut.isAccountNameDefined()
        then:
            isAccountDefined == true
    }

    def "get accountName from AccountRepository if stored in shared preferences"() {
        given:
            sharedPreferencesStub.getString("ACCOUNT_PREF_KEY", "") >> "testName"
        AccountRepository sut = new AccountRepository(contextStub,
                    googleCredentialWrapperStub,
                sharedPreferencesStub)
        when:
            String accountName = sut.getAccountName()
        then:
            accountName.equals("testName")
    }

}
