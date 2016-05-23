package com.macoscope.ketchuplunch.view.login

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.google.android.gms.common.GoogleApiAvailability
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.model.GooglePlayServices
import com.macoscope.ketchuplunch.model.NetworkAvailability
import com.macoscope.ketchuplunch.model.login.AccountPreferencesFactory
import com.macoscope.ketchuplunch.model.login.AccountRepository
import com.macoscope.ketchuplunch.model.login.GoogleCredentialWrapper
import com.macoscope.ketchuplunch.presenter.LoginPresenter
import com.macoscope.ketchuplunch.view.lunch.LunchActivity
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity
import pub.devrel.easypermissions.EasyPermissions

class LoginActivity : AppCompatActivity(), LoginView, EasyPermissions.PermissionCallbacks {

    lateinit var loginPresenter: LoginPresenter
    private val REQUEST_PERMISSION_GET_ACCOUNTS = 103

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LoginUI().setContentView(this)
        setupPresenter()

    }

    private fun setupPresenter() {
        val accountRepository: AccountRepository = AccountRepository(this,
                GoogleCredentialWrapper(this),
                AccountPreferencesFactory(this).getPreferences())

        loginPresenter = LoginPresenter(this,
                accountRepository,
                GooglePlayServices(this),
                NetworkAvailability(this))
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
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this)
    }

    override fun chooseAccount(userCredential: GoogleAccountCredential, requestCode: Int) {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            loginPresenter.permissionGranted()
            openSelectAccountDialog(userCredential, requestCode)

        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    this.getString(R.string.login_need_contacts_permission),
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS)
        }
    }

    override fun startLunchActivity() {
        startActivity<LunchActivity>()
        finish()
    }
}
