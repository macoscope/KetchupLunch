package com.macoscope.ketchuplunch.view.lunch

import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.macoscope.ketchuplunch.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.dip
import org.jetbrains.anko.horizontalPadding
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.imageView
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.singleLine
import org.jetbrains.anko.textView

class LunchMenuItemUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {

            linearLayout {
                lparams(width = matchParent, height = dip(48))
                orientation = LinearLayout.HORIZONTAL
                horizontalPadding = dip(16)

                textView {
                    lparams {
                        gravity = Gravity.CENTER_VERTICAL
                        weight = 1.0f
                    }
                    id = R.id.lunch_menu_name
                    singleLine = true
                    ellipsize = TextUtils.TruncateAt.END
                    textSize = 16f
                }

                imageView {
                    lparams(width =  dip(24), height = dip(24)){
                        gravity = Gravity.CENTER_VERTICAL
                        marginStart = dip(16)
                    }
                    imageResource = R.drawable.splash_logo
                }

                textView {
                    lparams {
                        gravity = Gravity.CENTER_VERTICAL
                    }
                    id = R.id.lunch_menu_count
                    textSize = 16f
                }
            }
        }
    }
}