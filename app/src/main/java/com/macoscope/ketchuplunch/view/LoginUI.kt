package com.macoscope.ketchuplunch.view

import android.support.v4.content.ContextCompat
import com.macoscope.ketchuplunch.R
import org.jetbrains.anko.*


class LoginUI: AnkoComponent<LoginActivity> {
    override fun createView(ui: AnkoContext<LoginActivity>) = with(ui){
        relativeLayout {
            padding = dip(8)
            id = R.id.login_main_container
            backgroundColor = ContextCompat.getColor(ctx, R.color.colorRed)

            imageView(R.drawable.splash_logo).lparams {  centerInParent() }
        }

    }
}
