package com.example.calculator.ui.bmi

import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import kotlinx.coroutines.launch

class BMIViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

	enum class ClickType(var  clickId: Int, val clickType: String? = null) {
		ZERO(R.id.figures_zero_bmi, "0"),
		ONE(R.id.figures_one_bmi, "1"),
		TWO(R.id.figures_two_bmi, "2"),
		THREE(R.id.figures_three_bmi, "3"),
		FOUR(R.id.figures_four_bmi, "4"),
		FIVE(R.id.figures_five_bmi, "5"),
		SIX(R.id.figures_six_bmi, "6"),
		SEVEN(R.id.figures_seven_normal_bmi, "7"),
		EIGHT(R.id.figures_eight_bmi, "8"),
		NINE(R.id.figures_nine_bmi, "9"),
		DOT(R.id.figures_dot_bmi, "."),
		AC(R.id.bmi_clear_btn),
		CLEAR(R.id.bmi_remove_btn);

		companion object {
			fun valueOf( value: Int? ) = values().find { it.clickId == value }
		}
	}

	enum class KiloGrams(var type: String, var doubleUnit: Double?) {
		KILOGRAMS("Kilograms", 1.0),
		POUNDS("Pounds", 0.453592);

		companion object {
			fun valueType( value: String?) = values().find { it.type == value }
		}
	}

	enum class Meter (var type: String, var doubleUnit: Double?) {
		CENTIMETERS("Centimeters", 0.01),
		METERS("Meters", 1.0),
		FEET("Feet",0.3048),
		INCHES("Inches", 0.0254);

		companion object {
			fun valueType (value: String?) = values().find { it.type == value}
		}
	}

	enum class ChosenType {
		FIRST,SECOND
	}

	var type = ChosenType.FIRST

	var firstUnit = KiloGrams.KILOGRAMS
	var secondUnit = Meter.CENTIMETERS

	var firstFieldConverted = 0.0
	var secondFieldConverted = 0.0

	var firstFieldLiveData: MutableLiveData<String> = MutableLiveData()
	var secondFieldLiveData: MutableLiveData<String> = MutableLiveData()

	var unitWeightLiveData: MutableLiveData<String> = MutableLiveData()
	var unitHeightLiveData: MutableLiveData<String> = MutableLiveData()

	var stringFirstField = "0"
	var stringSecondField = "0"

	fun setField (typeValue: ChosenType) {
		this.type = typeValue
	}

	fun clickButton ( viewId: Int? ) {
		val operation = ClickType.valueOf(viewId)
		when {
			operation?.clickType != null -> { checkFieldInput(operation) }
			else -> { operation?.let { clickNonOperational(it) } }
		}

		dataSand()
	}

	fun math(): String {
		if (stringFirstField.isNotEmpty() && stringSecondField.isNotEmpty() && stringFirstField != "0" && stringSecondField != "0" ) {
			firstFieldConverted = stringFirstField.toDouble() * firstUnit.doubleUnit!!
			secondFieldConverted = stringSecondField.toDouble() * secondUnit.doubleUnit!!
			val result = (firstFieldConverted / (secondFieldConverted * secondFieldConverted)).toString()
			val counted = result.substringAfter(".").length - 1
			return result.dropLast(counted)
		}
		return "0"

	}

	private fun checkFieldInput(operation: ClickType) {
		if (type == ChosenType.FIRST) {
				if (stringFirstField == "0" && operation.clickType != ClickType.DOT.clickType) { stringFirstField = "" }
				else if (stringFirstField.contains(".") && operation.clickType == ClickType.DOT.clickType) { return }
				stringFirstField += operation.clickType
		} else if (type == ChosenType.SECOND) {
				if (stringSecondField == "0" && operation.clickType != ClickType.DOT.clickType) {
					stringSecondField = ""
				} else if (stringSecondField.contains(".") && operation.clickType == ClickType.DOT.clickType) { return }

				stringSecondField += operation.clickType
		}
	}

	fun setWeight(weight: String) {
		unitWeightLiveData.value = weight
		firstUnit = KiloGrams.valueType(weight)!!
	}

	fun setHeight(height: String) {
		unitHeightLiveData.value = height
		secondUnit = Meter.valueType(height)!!
	}

	private fun dataSand() {
		if ( stringFirstField.isNotEmpty()) { firstFieldLiveData.value = stringFirstField }
		else {
			stringFirstField = "0"
			firstFieldLiveData.value = stringFirstField
		}

		if (stringSecondField.isNotEmpty())  secondFieldLiveData.value = stringSecondField
		else {
			stringSecondField = "0"
			secondFieldLiveData.value = stringSecondField
		}
	}

	private fun clickNonOperational(it: ClickType) {
		if (it.clickId == ClickType.AC.clickId) { clearAll() }
		else if (it.clickId == ClickType.CLEAR.clickId) { removeLast() }
	}

	private fun removeLast() {
			if(type == ChosenType.FIRST ) stringFirstField = stringFirstField.dropLast(1)
			else if (type == ChosenType.SECOND && stringSecondField != "0") stringSecondField = stringSecondField.dropLast(1)

		dataSand()

	}


	private fun clearAll() {
		stringFirstField = "0"
		stringSecondField = "0"
	}
}