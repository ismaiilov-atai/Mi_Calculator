package com.example.calculator.ui.date

import androidx.lifecycle.MutableLiveData
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.ui.age.Age
import java.util.*

class DateViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

	var fromLiveDate: MutableLiveData<String> = MutableLiveData()
	var currentLiveDate: MutableLiveData<String> = MutableLiveData()

	var birthdayCalendarLiveDate: MutableLiveData<Age> = MutableLiveData()


	private var dateOfBirthAge = Age()
	private var toDayAge = Age()

	init {
		val date = Calendar.getInstance()
		date.time = Date(System.currentTimeMillis())

		setFromLiveDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
		setCurrentLiveDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
	}

	fun setFromLiveDate(year:Int, month: Int, dayOfMonth: Int){
		dateOfBirthAge = Age(dayOfMonth, month, year)
		fromLiveDate.value = "${convert(month)} $dayOfMonth $year"

		calculateBirthdayDate()
	}

	fun setCurrentLiveDate(year:Int, month: Int, dayOfMonth: Int){
		toDayAge = Age(dayOfMonth, month, year)
		currentLiveDate.value = "${convert(month)} $dayOfMonth $year"

		calculateBirthdayDate()
	}

	private fun calculateBirthdayDate() {
		val toDayAgeCalendar: Calendar = Calendar.getInstance()
		toDayAgeCalendar.set(Calendar.YEAR, toDayAge.years)
		toDayAgeCalendar.set(Calendar.DAY_OF_MONTH, toDayAge.days)
		toDayAgeCalendar.set(Calendar.MONTH, toDayAge.months)

		val toDayDateResult = toDayAgeCalendar.time

		val dateOfBirthCalendar: Calendar = Calendar.getInstance()
		dateOfBirthCalendar.set(Calendar.YEAR, dateOfBirthAge.years)
		dateOfBirthCalendar.set(Calendar.DAY_OF_MONTH, dateOfBirthAge.days)
		dateOfBirthCalendar.set(Calendar.MONTH, dateOfBirthAge.months)

		val dateOfBirthResult = dateOfBirthCalendar.time

		birthdayCalendarLiveDate.value = calculateAge(dateOfBirthResult, toDayDateResult)
	}

	private fun calculateAge(birthDate: Date, toDayDate: Date): Age {
		var years = 0
		var months = 0
		var days = 0

		val birthDay = Calendar.getInstance()
		birthDay.timeInMillis = birthDate.time

		val currentTime = toDayDate.time
		val now = Calendar.getInstance()
		now.timeInMillis = currentTime
		years = now[Calendar.YEAR] - birthDay[Calendar.YEAR]
		val currMonth = now[Calendar.MONTH] + 1
		val birthMonth = birthDay[Calendar.MONTH] + 1

		months = currMonth - birthMonth

		if (months < 0) {
			years--
			months = 12 - birthMonth + currMonth
			if (now[Calendar.DATE] < birthDay[Calendar.DATE]) months--
		} else if (months == 0 && now[Calendar.DATE] < birthDay[Calendar.DATE]) {
			years--
			months = 11
		}

		if (now[Calendar.DATE] > birthDay[Calendar.DATE]) days =
			now[Calendar.DATE] - birthDay[Calendar.DATE]
		else if (now[Calendar.DATE] < birthDay[Calendar.DATE]) {
			val today = now[Calendar.DAY_OF_MONTH]
			now.add(Calendar.MONTH, -1)
			days =
				now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay[Calendar.DAY_OF_MONTH] + today
		} else {
			days = 0
			if (months == 12) {
				years++
				months = 0
			}
		}
		return Age(days, months, years)
	}

	private fun convert(monthC: Int): String {
		return when (monthC) {
			0 -> "Jan"; 1 -> "Feb"; 2 -> "Mar"
			3 -> "Apr"; 4 -> "May"; 5 -> "Jun"
			6 -> "Jul"; 7 -> "Aug"; 8 -> "Sep"
			9 -> "Oct"; 10 -> "Nov"; 11 -> "Dec"
			else -> ""
		}
	}
}