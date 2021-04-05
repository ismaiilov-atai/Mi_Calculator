package com.example.calculator.utils

import android.view.View

fun View.setVisibility(mode: Boolean) {
    if (mode) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}