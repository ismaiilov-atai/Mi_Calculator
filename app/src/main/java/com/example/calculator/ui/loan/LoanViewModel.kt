package com.example.calculator.ui.loan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class LoanViewModel(event: BaseViewModelEventListener) : BaseViewModel (event) {

	private var principalValue = 0.0
	private var interestValue = 0.0

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
		Log.e("test4324", "letsSee: principalValue $principalValue  interestValue $interestValue")
	}

	fun setTermsLiveData(year: String, month: String) {
		termLiveData.value = "$year years $month month"
	}
}