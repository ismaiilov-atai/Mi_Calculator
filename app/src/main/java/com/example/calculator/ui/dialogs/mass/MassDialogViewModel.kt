package com.example.calculator.ui.dialogs.mass

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class MassDialogViewModel(event: BaseViewModelEventListener): BaseViewModel (event) {

	var massLiveData: MutableLiveData<ArrayList<String>> = MutableLiveData()

	fun loadData(){
		massLiveData.value = ArrayList(listOf(
			"Tonne t",
			"Kilogram kg",
			"Gram g",
			"Milligram mg",
			"Microgram ug"
		))
	}

}