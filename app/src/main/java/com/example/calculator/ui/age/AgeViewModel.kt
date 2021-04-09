package com.example.calculator.ui.age

import androidx.lifecycle.MutableLiveData
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import java.util.*

class AgeViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

	var birthDayLiveDate: MutableLiveData<String> = MutableLiveData()
	var currentDayLiveDate: MutableLiveData<String> = MutableLiveData()

	var dateCalendarLiveDate: MutableLiveData<Age> = MutableLiveData()
	var birthdayCalendarLiveDate: MutableLiveData<Age> = MutableLiveData()

	private var dateOfBirth: Long = 0
	private var toDay: Long = 0

	private var dateOfBirthAge = Age()
	private var toDayAge = Age()

	init {
		setDateOfBirth(2010, 7, 16)

		val date = Calendar.getInstance()
		date.time = Date(System.currentTimeMillis())

		setToDay(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))

		calculateDate()
		calculateBirthdayDate()
	}

	fun setDateOfBirth(year: Int, month: Int, dayOfMonth: Int) {
		dateOfBirth = convertData(year, month, dayOfMonth)

		dateOfBirthAge = Age(dayOfMonth, month, year)

		calculateDate()
		calculateBirthdayDate()

		birthDayLiveDate.value = "${convert(month)} $dayOfMonth $year"
	}

	fun setToDay(year: Int, month: Int, dayOfMonth: Int) {
		toDay = convertData(year, month, dayOfMonth)

		toDayAge = Age(dayOfMonth, month, year)

		calculateDate()
		calculateBirthdayDate()

		currentDayLiveDate.value = "${convert(month)} $dayOfMonth $year"
	}

	private fun calculateDate() {
		val calculateDate = toDay - dateOfBirth

		val date = Calendar.getInstance()
		date.time = Date(calculateDate)

		val year = (calculateDate / 3.156e+10).toInt()
		val month = (calculateDate / 2.628e+9).toInt()
		val day = (calculateDate / 8.64e+7).toInt()
		val weeks = (calculateDate / 6.048e+8).toInt()
		val hours = (calculateDate / 3.6e+6).toInt()
		val minutes = (calculateDate / 60000).toInt()

		dateCalendarLiveDate.value = Age(day, month, year, weeks, hours, minutes)
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

	private fun convertData(year: Int, month: Int, dayOfMonth: Int): Long {
		val myCal: Calendar = Calendar.getInstance()
		myCal.set(Calendar.YEAR, year)
		myCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
		myCal.set(Calendar.MONTH, month)

		return myCal.time.time
	}
}
