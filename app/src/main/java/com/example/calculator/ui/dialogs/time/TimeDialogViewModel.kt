package com.example.calculator.ui.dialogs.time

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class TimeDialogViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

	var timeMutableLiveData: MutableLiveData<ArrayList<String>> = MutableLiveData()

	fun loadData(){
		timeMutableLiveData.value = ArrayList(listOf(
			"Year y",
			"Week wk",
			"Day d",
			"Hour h",
			"Minute min",
			"Second s",
			"Millisecond ms"
		))
	}

}