package com.picpay.desafio.android.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun View.isVisible(isPresent: Boolean) {
    visibility = if (isPresent) {
        View.VISIBLE
    } else {
        View.GONE
    }
}