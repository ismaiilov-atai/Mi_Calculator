package com.example.calculator.ui.dialogs.mass

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MassViewModel: ViewModel() {

	var massLiveData: MutableLiveData<ArrayList<String>> = MutableLiveData()

	fun loadData(){
		massLiveData.value = ArrayList(listOf(
			"Tonne t",
			"Kilogram kg",
			"Gram g",
			"Milligram mg",
			"Microgram ug",
			"Quintal q",
			"Pound ib",
			"Ounce oz",
			"Carat ct",
			"Grain gr",
			"Long ton l.t",
			"Short ton sh.t",
			"UK hundredweght cwt",
			"US hundredweght cwt",
			"Stone st",
			"Dram dr",
			"Dan Dan ",
			"Jin jin",
			"Qian qian",
			"Liang liang",
			"Jin (Taiwan) jin(tw)"
		))
	}

}