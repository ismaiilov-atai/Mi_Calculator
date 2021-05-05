package com.example.calculator.ui.investment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import kotlin.math.pow

class InvestmentViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

	enum class InvestmentType {
		ONE_TIME,RECURRING
	}

	private var investType = InvestmentType.ONE_TIME
	private var interestValue = 0.0
	private var convertedYear = 0
	var principalValue = 0.0
	var year: Int = 2
	var month: Int = 1
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

		if (investType == InvestmentType.ONE_TIME) futureValue = principalValue * ( (1 + convertedType).pow(convertedYear) )
		else  Log.e("test23441", "letsDoMath: in right path")
	}

	fun setTermsLiveData(year: String, month: String) {
		termLiveData.value = "$year years $month month"
		this.year = year.toInt()
		this.month = month.toInt()
		convertedYear = (year.toInt() + (month.toInt() / 12))
	}

	fun setInvestType ( investType: InvestmentType ) { this.investType = investType }
	fun setInterestValue( interestValue: String ) { this.interestValue = interestValue.toDouble() }

}