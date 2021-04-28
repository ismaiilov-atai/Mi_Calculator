package com.example.calculator.utils

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.calculator.R

fun View.setVisibility(mode: Boolean) {
    if (mode) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.adjustVisibility (mode: Boolean) {
    if (mode){ this.visibility = View.VISIBLE } else { this.visibility = View.INVISIBLE }
}

fun View.setPickedColor(pickedField: TextView, secondField: TextView ){
    pickedField.setTextColor( ContextCompat.getColor(context,R.color.purple_500) )
    secondField.setTextColor( ContextCompat.getColor(context,R.color.light_gray) )
}

fun View.enable () {
    this.isEnabled = true
    this as TextView
    this.setTextColor(ContextCompat.getColor(context, R.color.black))
}

fun View.disable () {
    this.isEnabled = false
    this as TextView
    this.setTextColor(ContextCompat.getColor(context, R.color.light_gray))
}
