package com.example.calculator.ui.loan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class LoanViewModel(event: BaseViewModelEventListener) : BaseViewModel (event) {

	var principalValue = 0.0
	private var interestValue = 0.0
	var year: Double = 27.0
	var month: Double = 9.0

	var emi = 0.0
	var interest = 0.0
	var totalPayment = 0.0


	var termLiveData: MutableLiveData<String> = MutableLiveData()

	fun setValueCalculation(fieldValue: String, editId: Int) {
		if ( editId == R.id.principal_edit ) {
			principalValue = if (fieldValue.isNotEmpty()) fieldValue.toDouble()
			else 0.0
		}
		else if (editId == R.id.interest_edit ) {
				interestValue = if (fieldValue.isNotEmpty()) fieldValue.toDouble()
				else 0.0
		}
	}

	fun letsSee() {
		var convertedType = 0.0
		val lengthAfter = interestValue.toString().substringAfter(".")
		convertedType = if (lengthAfter.length == 1) { ("0.0$lengthAfter").toDouble() }
		else { interestValue }
		 interest = principalValue * convertedType
		 totalPayment = principalValue + interest
		 emi = (principalValue + (principalValue * year * 0.03)) / (year * 12)

		Log.e("test34234", "letsSee: convertedType $convertedType result $interest totalPay $totalPayment principalValue $principalValue emi $emi" )
	}

	fun setTermsLiveData(year: String, month: String) {
		termLiveData.value = "$year years $month month"
		this.year = year.toDouble()
		this.month = month.toDouble()
	}
}