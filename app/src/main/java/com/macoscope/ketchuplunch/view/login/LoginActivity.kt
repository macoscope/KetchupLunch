package com.macoscope.ketchuplunch.view.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.GoogleApiAvailability
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.di.AccountModule
import com.macoscope.ketchuplunch.di.LoginModule
import com.macoscope.ketchuplunch.model.login.AccountPermission
import com.macoscope.ketchuplunch.presenter.LoginPresenter
import com.macoscope.ketchuplunch.view.lunch.LunchActivity
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity
import pub.devrel.easypermissions.EasyPermissions

class LoginActivity : AppCompatActivity(), LoginView, EasyPermissions.PermissionCallbacks {

    lateinit var loginPresenter: LoginPresenter
    lateinit var accountPermission: AccountPermission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginUI().setContentView(this)
        val loginModule = LoginModule(AccountModule(this), this, this)
        accountPermission = loginModule.provideAccountPermission()
        loginPresenter = loginModule.provideLoginPresenter()
        loginPresenter.onCreate()
    }

    override fun showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode: Int, requestCode: Int) {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val dialog = apiAvailability.getErrorDialog(
                this,
                connectionStatusCode,
                requestCode)
        dialog.show()
    }

    override fun showNoNetworkMessage() {
        val message = getString(R.string.no_network)
        displaySnackbar(message)
    }

    override fun openSelectAccountDialog(googleAccountCredential: GoogleAccountCredential, requestCode: Int) {
        startActivityForResult(
                googleAccountCredential.newChooseAccountIntent(),
                requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginPresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun showNoGooglePlayServices() {
        val message = getString(R.string.login_need_google_play)
        displaySnackbar(message)
    }

    fun displaySnackbar(message: String) = Snackbar.make(findViewById(R.id.login_main_container)!!, message,
            Snackbar.LENGTH_LONG).show()

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
        loginPresenter.permissionDenied()

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        loginPresenter.permissionGranted()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        accountPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun chooseAccount(userCredential: GoogleAccountCredential, requestCode: Int) {
        if (accountPermission.hasPermission(this)) {
            loginPresenter.permissionGranted()
            openSelectAccountDialog(userCredential, requestCode)
        } else {
            accountPermission.requestPermission(this, this)
        }
    }

    override fun startLunchActivity() {
        startActivity<LunchActivity>()
        finish()
    }
}


