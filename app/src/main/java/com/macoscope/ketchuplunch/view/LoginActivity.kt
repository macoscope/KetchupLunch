package com.macoscope.ketchuplunch.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.presenter.LoginPresenter

class LoginActivity : AppCompatActivity(), LoginView {

    val loginPresenter: LoginPresenter = LoginPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode: Int) {
        //TODO: implement
    }

    override fun showNoNetworkMessage() {
        //TODO: implement
    }
}
