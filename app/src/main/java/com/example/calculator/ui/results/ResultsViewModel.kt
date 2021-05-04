package com.example.calculator.ui.results

import androidx.lifecycle.MutableLiveData
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class ResultsViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

	// relates if its coming from Loan
	var interestLiveData: MutableLiveData<Double> = MutableLiveData()
	var emiLiveData: MutableLiveData<Double> = MutableLiveData()
	var principalLiveData: MutableLiveData<Double> = MutableLiveData()
	var totalPaymentLiveData: MutableLiveData<Double> = MutableLiveData()
	var percentageProgressLiveData: MutableLiveData<Int> = MutableLiveData()
	var yearLiveData: MutableLiveData<Double> = MutableLiveData()
	var monthLiveData: MutableLiveData<Double> = MutableLiveData()

	var futureValueLiveData: MutableLiveData<Double> = MutableLiveData()
	var interestValueLiveData: MutableLiveData<Double> = MutableLiveData()

	fun setValueFromInvest ( futureValue: Double, principal: Double, year: Double, month: Double) {
		yearLiveData.value = year
		monthLiveData.value = month
		futureValueLiveData.value = futureValue
		interestValueLiveData.value = (futureValue - principal)
	}

	fun setValueFromLoan(interest: Double, emi: Double, totalPayment: Double, principal: Double,year: Double, month: Double) {
		interestLiveData.value = interest
		emiLiveData.value = emi
		totalPaymentLiveData.value = totalPayment
		principalLiveData.value = principal
		percentageProgressLiveData.value = (principal * 100 / totalPayment).toInt()
		yearLiveData.value = year
		monthLiveData.value = month
	}


}