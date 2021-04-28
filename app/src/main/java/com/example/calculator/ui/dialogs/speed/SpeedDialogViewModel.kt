package com.example.calculator.ui.dialogs.speed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class SpeedDialogViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

	var speedUnitLiveData: MutableLiveData<ArrayList<String>> = MutableLiveData()

	fun loadData(){
		speedUnitLiveData.value = ArrayList(listOf(
			"Lightspeed c",
			"Meter per second m/s",
			"Kilometer per hour km/h",
			"Kilometer per second km/s"
		))
	}

}