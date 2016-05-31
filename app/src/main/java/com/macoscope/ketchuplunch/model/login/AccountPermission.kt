package com.macoscope.ketchuplunch.model.login

import android.Manifest
import android.content.Context
import android.support.v4.app.ActivityCompat
import com.macoscope.ketchuplunch.R
import pub.devrel.easypermissions.EasyPermissions

class AccountPermission {

    private val REQUEST_PERMISSION_GET_ACCOUNTS = 103

    fun hasPermission(context: Context) : Boolean {
        return EasyPermissions.hasPermissions(context, Manifest.permission.GET_ACCOUNTS)
    }

    fun requestPermission(context: Context, callback: ActivityCompat.OnRequestPermissionsResultCallback) {
        // Request the GET_ACCOUNTS permission via a user dialog
        EasyPermissions.requestPermissions(
                context,
                context.getString(R.string.login_need_contacts_permission),
                REQUEST_PERMISSION_GET_ACCOUNTS,
                Manifest.permission.GET_ACCOUNTS)
    }
}