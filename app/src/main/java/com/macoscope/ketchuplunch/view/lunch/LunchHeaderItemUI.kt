package com.macoscope.ketchuplunch.view.lunch

import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.macoscope.ketchuplunch.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textView

class LunchHeaderItemUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            frameLayout() {
                lparams(width = matchParent, height = dip(28f))
                backgroundColor = ContextCompat.getColor(ctx, R.color.lightGrey)
                textView {
                    id = R.id.lunch_meal_type
                    textSize = 14f
                    gravity = Gravity.CENTER
                }
            }
        }
    }
}