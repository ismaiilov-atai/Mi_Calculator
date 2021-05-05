package com.example.calculator.ui.results

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class ResultsViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

	// relates if its coming from Loan
	var interestLiveData: MutableLiveData<Double> = MutableLiveData()
	var emiLiveData: MutableLiveData<String> = MutableLiveData()
	var principalLiveData: MutableLiveData<Double> = MutableLiveData()
	var totalPaymentLiveData: MutableLiveData<String> = MutableLiveData()
	var percentageProgressLiveData: MutableLiveData<Int> = MutableLiveData()
	var yearLiveData: MutableLiveData<Int> = MutableLiveData()
	var monthLiveData: MutableLiveData<Int> = MutableLiveData()

	var futureValueLiveData: MutableLiveData<String> = MutableLiveData()
	var interestValueLiveData: MutableLiveData<Double> = MutableLiveData()

	fun setValueFromInvest ( futureValue: Double, principal: Double, year: Int, month: Int) {
		yearLiveData.value = year
		monthLiveData.value = month
		futureValueLiveData.value = cutLastPaces ( futureValue )
		principalLiveData.value = principal
		percentageProgressLiveData.value = (principal * 100 / futureValue).toInt()
		interestValueLiveData.value = (futureValue - principal)
	}

	fun setValueFromLoan(interest: Double, emi: Double, totalPayment: Double, principal: Double,year: Int, month: Int) {
		interestLiveData.value = interest
		emiLiveData.value =  cutLastPaces(emi)
		totalPaymentLiveData.value = totalPayment.toString()
		principalLiveData.value = principal
		percentageProgressLiveData.value = (principal * 100 / totalPayment).toInt()
		yearLiveData.value = year
		monthLiveData.value = month
	}

	private fun cutLastPaces(toConverted: Double): String { return toConverted.toString().substringBefore(".") }

}