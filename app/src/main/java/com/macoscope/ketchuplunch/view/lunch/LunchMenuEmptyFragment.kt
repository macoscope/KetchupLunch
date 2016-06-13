package com.macoscope.ketchuplunch.view.lunch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx

class LunchMenuEmptyFragment : Fragment() {

    companion object {
        fun newInstance(): LunchMenuEmptyFragment {
            return LunchMenuEmptyFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return LunchMenuEmptyUI().createView(AnkoContext.create(ctx, this))
    }
}


