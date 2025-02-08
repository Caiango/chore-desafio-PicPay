package com.picpay.desafio.android.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.picpay.domain.screen.ScreenStatus

@BindingAdapter("isVisible")
fun View.isVisible(status: ScreenStatus) {
    visibility = if (status.isLoading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}