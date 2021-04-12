package com.example.calculator.ui.dialogs.length

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LengthUnitViewModel: ViewModel(){

	var lengthUnitList: MutableLiveData<ArrayList<String>> = MutableLiveData()

	fun loadData(){
		lengthUnitList.value = ArrayList(listOf(
			"Kilometer km",
			"Meter km",
			"Decimeter dm",
			"Centimeter cm",
			"Millimeter mm",
			"Micrometer um",
			"Nanometer nm",
			"Picometer pm",
			"Nautical mile nmi",
			"Mile mi",
			"Femara fur",
			"Fathom ftm",
			"Yard yd",
			"Foot ft",
			"Inch in",
			"Foot ft |  inch in",
			"Gongli gongli",
			"Li li",
			"Zhang zhang",
			"Cun cun",
			"fen fen",
			"Lii lii",
			"Hao hao",
			"Parsec pc",
			"Lunar distance id",
			"Astronomical unit ø",
			"Light year ly"
		))
	}
}