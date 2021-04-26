package com.example.calculator.ui.dialogs.area

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class AreaUnitViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

	var unitListLiveData : MutableLiveData<ArrayList<String>> = MutableLiveData()

	fun loadData(){
		unitListLiveData.value = ArrayList(listOf(
			"Square kilometer km²",
			"Hectare ha",
			"Are a",
			"Square meter m²",
			"Square decimeter dm²",
			"Square centimeter cm²",
			"Square millimeter mm²",
		))
	}

}