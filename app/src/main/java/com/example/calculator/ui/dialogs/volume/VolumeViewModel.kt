package com.example.calculator.ui.dialogs.volume

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class VolumeViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

	var volumeUnitLiveData: MutableLiveData<ArrayList<String>> = MutableLiveData()

	fun loadData(){
		volumeUnitLiveData.value = ArrayList(listOf(
			"Cubic meter m³",
			"Cubic decimeter dm³",
			"Cubic centimeter cm³",
			"Cubic millimeter mm³",
			"Hectoliter hl",
			"Liter l",
			"Deciliter dl",
			"Centiliter cl",
			"Milliliter ml",
			"Cubic foot ft³",
			"Cubic inch in³",
			"Cubic yard yd³",
			"Arce-foot af³"
		))
	}
}