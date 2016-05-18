package com.macoscope.ketchuplunch.view.lunch

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.macoscope.ketchuplunch.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textView

class LunchMenuItemUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {

            linearLayout {
                lparams(width = matchParent, height = dip(48))
                backgroundColor = Color.GRAY
                orientation = LinearLayout.HORIZONTAL

                textView { id = R.id.lunch_menu_name }
                textView { id = R.id.lunch_menu_count }
            }

        }
    }
}