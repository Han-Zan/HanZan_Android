package com.kud.hanzan.utils

import android.view.View
import androidx.databinding.BindingAdapter

object CustomBinder {
    // xml 뷰의 attributes 추가
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun setVisibility(view: View, visible: Boolean){
        view.visibility = if(visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleInvisible")
    fun setVisibleInvisible(view: View, visible: Boolean){
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }
}