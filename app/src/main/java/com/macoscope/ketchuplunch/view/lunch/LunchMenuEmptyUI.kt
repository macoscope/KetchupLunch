package com.macoscope.ketchuplunch.view.lunch

import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.View
import com.macoscope.ketchuplunch.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.textView

class LunchMenuEmptyUI() : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View {
        return with(ui) {
            relativeLayout {
                lparams(width = matchParent, height = matchParent)
                gravity = Gravity.CENTER
                textView(R.string.lunch_empty_menu)
            }
        }
    }
}

