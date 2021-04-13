package com.example.calculator.ui.dialogs.speed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SpeedViewModel : ViewModel() {

	var speedUnitLiveData: MutableLiveData<ArrayList<String>> = MutableLiveData()

	fun loadData(){
		speedUnitLiveData.value = ArrayList(listOf(
			"Tonne t",
			"Kilogram kg",
			"Gram g",
			"Milligram mg",
			"Microgram ug",
			"Quintal q",
			"Pound ib",
			"Ounce oz",
			"Carat ct",
			"Grain gr"
		))
	}

}