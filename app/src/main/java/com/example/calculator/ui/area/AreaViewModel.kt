package com.example.calculator.ui.area

import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.utils.Constants

class AreaViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

	enum class Type {
		FIRST, SECOND
	}

	enum class AreaUnit (var type: String, var valueMillimeter: Double?) {
		MILLIMETER("Square millimeter mm²",1.0),
		CENTIMETER("Square centimeter cm²", 100.0),
		DECIMETER("Square decimeter dm²", 10000.0),
		METER("Square meter m²", 1000000.0),
		AREA("Are a", 100000000.0),
		HECTARE("Hectare ha", 100000000.0),
		KILOMETER("Square kilometer km²", 1000000000000.0);

		companion object {
			fun valueOfType(value: String?) = values().find { it.type == value }
		}
	}

	enum class BottomType(var id: Int, val type: String? = null) {
		ZERO(R.id.figures_zero_area, "0"),
		ONE(R.id.figures_one_area, "1"),
		TWO(R.id.figures_two_area, "2"),
		THREE(R.id.figures_three_area, "3"),
		FOUR(R.id.figures_four_area, "4"),
		FIVE(R.id.figures_five_area, "5"),
		SIX(R.id.figures_six_area, "6"),
		SEVEN(R.id.figures_seven_normal_area, "7"),
		EIGHT(R.id.figures_eight_area, "8"),
		NINE(R.id.figures_nine_area, "9"),
		DOT(R.id.figures_dot_area, "."),
		AC(R.id.area_clear_btn),
		CLEAR(R.id.area_delete_btn);

		companion object {
			fun valueOf(value: Int?) =  values().find { it.id == value }
		}
	}

	var firstFieldLiveData: MutableLiveData<String> = MutableLiveData()
	var secondFieldLiveData: MutableLiveData<String> = MutableLiveData()

	var firstUnitPickerLiveData: MutableLiveData<String> = MutableLiveData()
	var secondUnitPickerLiveData: MutableLiveData<String> = MutableLiveData()

	private var firstFieldValue = ""
	private var secondFieldValue = ""

	private var stringFieldOne = "0"
	private var stringFieldTwo = "0"

	private var resultFieldOne = 0.0
	private var resultFieldTwo = 0.0

	private var firstUnit = AreaUnit.KILOMETER
	private var secondUnit = AreaUnit.MILLIMETER

	var type = Type.FIRST

	fun onButtonClick(viewId: Int?) {
		val clickOperation = BottomType.valueOf(viewId)
		if (clickOperation?.type != null) {
			if (type == Type.FIRST) {
				if (stringFieldOne == "0" && clickOperation.type != BottomType.DOT.type) { stringFieldOne = "" }
				else if (clickOperation.type == BottomType.DOT.type && stringFieldOne.contains(BottomType.DOT.type)) { return }

				stringFieldOne += clickOperation.type

			} else if (type == Type.SECOND ) {
				if (stringFieldTwo == "0" && clickOperation.type != BottomType.DOT.type ) { stringFieldTwo = "" }
				else if (clickOperation.type == BottomType.DOT.type && stringFieldTwo.contains(BottomType.DOT.type)) { return }

				stringFieldTwo += clickOperation.type
			}

		} else {
			clickOperation?.let { clickOperation(it) }
		}

		sendData()
		math()

	}

	private fun clickOperation(operationType: BottomType) {
		when (operationType) {
			BottomType.CLEAR -> removeLast()
			BottomType.AC -> clearAll()
			else -> return
		}
	}

	private fun math () {
		try {
			resultFieldOne = stringFieldOne.toDouble() * firstUnit.valueMillimeter!!
			resultFieldTwo = stringFieldTwo.toDouble() * secondUnit.valueMillimeter!!

			if (type == Type.FIRST) {
				stringFieldTwo = ((resultFieldOne) / secondUnit.valueMillimeter!!).toString()
			}
			else if ( type == Type.SECOND ) {
				stringFieldOne = ((resultFieldTwo) / firstUnit.valueMillimeter!!).toString()
			}

		} catch (e: Exception) { }

		sendData()

	}


	private fun clearAll() {
		stringFieldOne = "0"; stringFieldTwo = "0"

		sendData()
	}

	private fun removeLast() {
		if (type == Type.FIRST) {
			 stringFieldOne = stringFieldOne.dropLast(1)
		}
		else if (type == Type.SECOND ) {
			stringFieldTwo = stringFieldTwo.dropLast(1)
		}
	}

	private fun sendData() {

		stringFieldOne = checkValues(stringFieldOne)
		stringFieldTwo = checkValues(stringFieldTwo)


		if (stringFieldOne.isNotEmpty()) {
			firstFieldLiveData.value = stringFieldOne
		} else {
			firstFieldLiveData.value = "0"
		}

		if (stringFieldTwo.isNotEmpty()) {
			secondFieldLiveData.value = stringFieldTwo
		} else {
			secondFieldLiveData.value = "0"
		}

		if ( type == Type.FIRST && stringFieldOne.isEmpty()) {
			stringFieldOne = "0"
			firstFieldLiveData.value = stringFieldOne
		}

		if (type == Type.SECOND && stringFieldTwo.isEmpty()) {
			stringFieldTwo = "0"
			secondFieldLiveData.value = stringFieldTwo
		}

	}

	private fun checkValues(toConvert: String): String {
		return if (toConvert.takeLast(2) == ".0") toConvert.dropLast(2)
		else toConvert
	}

	fun fieldChange(type: Type) {
		this.type = type

		math()
	}

	fun setDropDownUnit(unitArea: String, type: Type) {
		if (type == Type.FIRST) {
			firstUnitPickerLiveData.value = unitArea; firstFieldValue = unitArea
			AreaUnit.valueOfType(unitArea)?.let { firstUnit = it }
		} else if (type == Type.SECOND) {
			secondUnitPickerLiveData.value = unitArea; secondFieldValue = unitArea
			AreaUnit.valueOfType(unitArea)?.let { secondUnit = it }
		}

		math()
	}

	fun inputConverter(unitValue: String): String {
		return when (unitValue) {
			Constants.SQUARE_KILOMETER -> "km²"
			Constants.HECTARE_HA -> "ha"
			Constants.AREA_A -> "a"
			Constants.SQUARE_METER -> "m²"
			Constants.SQUARE_DECIMETER -> "dm²"
			Constants.SQUARE_CENTIMETER -> "cm²"
			Constants.SQUARE_MILLIMETER_MM2 -> "mm²"
			else -> "not supported"
		}
	}

}