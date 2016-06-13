package com.macoscope.ketchuplunch.view.lunch

import android.graphics.Color
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.macoscope.ketchuplunch.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.singleLine
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textView

class WeekToolbarItemUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            textView {
                id = R.id.week_toolbar_name
                singleLine = true
                ellipsize = TextUtils.TruncateAt.END
                textSize = 18f
                gravity = Gravity.RIGHT
                textColor = Color.WHITE
            }
        }
    }
}