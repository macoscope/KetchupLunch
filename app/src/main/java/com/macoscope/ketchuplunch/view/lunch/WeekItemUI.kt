package com.macoscope.ketchuplunch.view.lunch

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.macoscope.ketchuplunch.R
import org.jetbrains.anko.*

class WeekItemUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            textView {
                id = R.id.week_name
                singleLine = true
                ellipsize = TextUtils.TruncateAt.END
                textSize = 18f
                height = dip(40f)
                gravity = Gravity.CENTER_VERTICAL
                textColor = Color.WHITE
                setPadding(dip(8f), 0, dip(8f), 0)
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorRed)
            }
        }


    }
}