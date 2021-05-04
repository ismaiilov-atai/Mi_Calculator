package com.example.calculator.ui.investment

import androidx.lifecycle.MutableLiveData
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import kotlin.math.pow

class InvestmentViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

	var principalValue = 0.0
	private var interestValue = 0.0
	var year: Int = 27
	var month: Int = 0
	private var convertedYear = 0
	var futureValue = 0.0

	var termLiveData: MutableLiveData<String> = MutableLiveData()

	fun setValueCalculation(fieldValue: String) {
		principalValue = if (fieldValue.isNotEmpty()) fieldValue.toDouble()
		else 0.0
	}

	fun letsDoMath() {
		var convertedType = 0.0
		val lengthAfter = interestValue.toString().substringAfter(".")
		convertedType = if (lengthAfter.length == 1) { ("0.0$lengthAfter").toDouble() }
		else { interestValue }
		futureValue = principalValue * ((1 + convertedType).pow(convertedYear))

	}

	fun setTermsLiveData(year: String, month: String) {
		termLiveData.value = "$year years $month month"
		this.year = year.toInt()
		this.month = month.toInt()
		convertedYear = (year.toInt() + (month.toInt() / 12))
	}

	fun setInterestValue( interestValue: String ) { this.interestValue = interestValue.toDouble() }

}