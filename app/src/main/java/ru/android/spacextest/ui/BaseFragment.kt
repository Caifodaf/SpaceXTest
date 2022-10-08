package ru.android.spacextest.ui

import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

abstract class BaseFragment(): Fragment() {

    companion object{
        internal const val ERROR_VISIBILITY_SHOW = 0
        internal const val LOADER_VISIBILITY_SHOW = 1
        internal const val MINE_VISIBILITY = 2
    }

    private var loaderLayout: FrameLayout? = null
    private var errorLayout: FrameLayout? = null
    private var mainLayout: FrameLayout? = null

    internal fun setLayoutBase(
        loaderLayout: FrameLayout,
        errorLayout: FrameLayout,
        mainLayout: FrameLayout
    ) {
        this.loaderLayout = loaderLayout
        this.errorLayout = errorLayout
        this.mainLayout = mainLayout
    }

    internal fun errorViewVisibility(type:Int){
        when(type){
            ERROR_VISIBILITY_SHOW->{
                loaderLayout!!.visibility = View.GONE
                errorLayout!!.visibility = View.VISIBLE
                mainLayout!!.visibility = View.GONE
            }
            LOADER_VISIBILITY_SHOW->{
                loaderLayout!!.visibility = View.VISIBLE
                errorLayout!!.visibility = View.GONE
                mainLayout!!.visibility = View.GONE
            }
            MINE_VISIBILITY->{
                loaderLayout!!.visibility = View.GONE
                errorLayout!!.visibility = View.GONE
                mainLayout!!.visibility = View.VISIBLE
            }
        }
    }

}