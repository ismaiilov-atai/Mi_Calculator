package com.example.calculator.ui.dialogs.area

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AreaUnitViewModel: ViewModel() {

	var unitListLiveData : MutableLiveData<ArrayList<String>> = MutableLiveData()

	fun loadData(){
		unitListLiveData.value = ArrayList(listOf(
			"Square kilometer km2",
			"Hectare ha",
			"Are a",
			"Square meter m2",
			"Square decimeter dm2",
			"Square centimeter cm2",
			"Square millimeter mm2",
			"Square micron um2",
			"Square Acre ac",
			"Square mile mile2",
			"Square yard yd2",
			"Square foot ft2",
			"Square inch in2",
			"Square rod rd2",
			"Qing qing",
			"Mu mu",
			"Square chi chi2",
			"Square cun cun2",
			"Square kilometer gongli2"))
	}
}