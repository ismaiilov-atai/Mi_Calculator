package com.example.calculator.utils.prefs

import android.content.Context
import android.content.SharedPreferences
import com.example.calculator.utils.Constants

class Prefs(context: Context) {

	private val preferenceFieldFirst: SharedPreferences = context.getSharedPreferences("localOne",Context.MODE_PRIVATE)
	private val preferenceFieldSecond: SharedPreferences = context.getSharedPreferences("localTwo",Context.MODE_PRIVATE)

	var firstField: String?
		get() = preferenceFieldFirst.getString(Constants.KEY_FIRST_FIELD, "")
		set(value) = preferenceFieldFirst.edit().putString(Constants.KEY_FIRST_FIELD, value).apply()

	var secondField: String?
	 	get() = preferenceFieldSecond.getString(Constants.KEY_SECOND_FIELD, "")
		set(value) = preferenceFieldSecond.edit().putString(Constants.KEY_SECOND_FIELD,value).apply()
}