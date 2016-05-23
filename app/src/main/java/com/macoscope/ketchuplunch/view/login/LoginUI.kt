package com.macoscope.ketchuplunch.view.login

import android.support.v4.content.ContextCompat
import com.macoscope.ketchuplunch.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.centerInParent
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageView
import org.jetbrains.anko.padding
import org.jetbrains.anko.relativeLayout


class LoginUI: AnkoComponent<LoginActivity> {
    override fun createView(ui: AnkoContext<LoginActivity>) = with(ui){
        relativeLayout {
            padding = dip(8)
            id = R.id.login_main_container
            backgroundColor = ContextCompat.getColor(ctx, R.color.colorRed)

            imageView(R.drawable.ic_logo).lparams {  centerInParent() }
        }

    }
}
