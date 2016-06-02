package com.macoscope.ketchuplunch.model.login

import android.Manifest
import android.content.Context
import android.support.v4.app.ActivityCompat
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.view.login.LoginActivity
import pub.devrel.easypermissions.EasyPermissions

open class AccountPermission {

    private val REQUEST_PERMISSION_GET_ACCOUNTS = 103

    open fun hasPermission(context: Context) : Boolean {
        return EasyPermissions.hasPermissions(context, Manifest.permission.GET_ACCOUNTS)
    }

    open fun requestPermission(context: Context, callback: ActivityCompat.OnRequestPermissionsResultCallback) {
        // Request the GET_ACCOUNTS permission via a user dialog
        EasyPermissions.requestPermissions(
                context,
                context.getString(R.string.login_need_contacts_permission),
                REQUEST_PERMISSION_GET_ACCOUNTS,
                Manifest.permission.GET_ACCOUNTS)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray, loginActivity: LoginActivity) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, loginActivity)
    }
}