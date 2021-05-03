package com.example.calculator.ui.dialogs.length

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class LengthDialogViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

	var lengthUnitList: MutableLiveData<ArrayList<String>> = MutableLiveData()

	fun loadData(){
		lengthUnitList.value = ArrayList(listOf(
			"Kilometer km",
			"Meter m",
			"Decimeter dm",
			"Centimeter cm",
			"Millimeter mm",
			"Micrometer μm"
		))
	}
}