package com.macoscope.ketchuplunch

import com.macoscope.ketchuplunch.model.login.AccountRepository
import com.macoscope.ketchuplunch.model.login.GoogleCredentialWrapper

class LogOutUseCase(val credentialsWrapper: GoogleCredentialWrapper, val accountRepository: AccountRepository) {

    open fun logOut(){
        accountRepository.clearAccount()
        credentialsWrapper.clearSelectedAccountName()
    }
}