package com.example.calculator.ui.length

import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import java.lang.Exception

class LengthViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

	enum class ClickType(var viewId: Int, var viewType: String? = null) {
		ZERO(R.id.figures_zero_length, "0"),
		ONE(R.id.figures_one_length, "1"),
		TWO(R.id.figures_two_length, "2"),
		THREE(R.id.figures_three_length, "3"),
		FOUR(R.id.figures_four_length, "4"),
		FIVE(R.id.figures_five_length, "5"),
		SIX(R.id.figures_six_length, "6"),
		SEVEN(R.id.figures_seven_length, "7"),
		EIGHT(R.id.figures_eight_length, "8"),
		NINE(R.id.figures_nine_length, "9"),
		DOT(R.id.figures_dot_length, "."),
		AC(R.id.length_clear_btn),
		CLEAR(R.id.length_remove_btn);

		companion object {
			fun valueType(value: Int?) = values().find { it.viewId == value }
		}
	}

	enum class Micrometer (var valueName: String, var inMicrometer: Double ) {
		MICROMETER ("Micrometer μm", 1.0),
		MILLIMETER ("Millimeter mm", 1000.0),
		CENTIMETER("Centimeter cm", 10000.0),
		DECIMETER("Decimeter dm", 100000.0),
		METER("Meter m", 1e+6),
		KILOMETER("Kilometer km",1e+9);

		companion object { fun valueType(value: String) = values().find { it.valueName == value } }
	}

	enum class FieldType {
		FIRST_FIELD, SECOND_FIELD
	}

	enum class LengthDropDownType {
		DROP_FIRST, DROP_SECOND
	}
	var firstFieldUnit = Micrometer.METER
	var secondFieldUnit = Micrometer.CENTIMETER

	var convertedValueFirst = 0.0
	var convertedValueSecond = 0.0

	var firstFieldValueLiveData: MutableLiveData<String> = MutableLiveData()
	var secondFieldValueLiveData: MutableLiveData<String> = MutableLiveData()

	var firstDropValueLiveData: MutableLiveData<String> = MutableLiveData()
	var secondDropValueLiveData: MutableLiveData<String> = MutableLiveData()

	var firstDescriptionValueLiveData: MutableLiveData<String> = MutableLiveData()
	var secondDescriptionValueLiveData: MutableLiveData<String> = MutableLiveData()


	private var firstStringField = "0"
	private var secondStringField = "0"
	private var fieldType = FieldType.FIRST_FIELD
	private var dropDownType = LengthDropDownType.DROP_FIRST

	fun eachClick(viewId: Int?) {
		val operation = ClickType.valueType(viewId)
		if (operation?.viewType != null) operationFunc(operation)
		else if (operation?.viewType == null) nonOperationFunc(operation?.viewId)

		dataSand()
		math()
	}

	private fun math() {
		convertedValueFirst = (firstStringField.toDouble() * firstFieldUnit.inMicrometer)
		convertedValueSecond = (secondStringField.toDouble() * secondFieldUnit.inMicrometer)

		try {
			if (fieldType == FieldType.FIRST_FIELD) { secondStringField = (convertedValueFirst / secondFieldUnit.inMicrometer).toString() }
			else { firstStringField = (convertedValueSecond / firstFieldUnit.inMicrometer).toString() }
		} catch (e: Exception) {}

		dataSand()
	}

	private fun operationFunc(viewValue: ClickType?) {
		if (fieldType == FieldType.FIRST_FIELD) {
			if (firstStringField == "0" && viewValue?.viewType != ClickType.DOT.viewType) firstStringField = ""
			else if (firstStringField.contains(ClickType.DOT.viewType.toString()) && viewValue?.viewType == ClickType.DOT.viewType) return

			firstStringField += viewValue?.viewType

		} else if (fieldType == FieldType.SECOND_FIELD) {
			if (secondStringField == "0" && viewValue?.viewType != ClickType.DOT.viewType) secondStringField = ""
			else if (secondStringField.contains(ClickType.DOT.viewType.toString()) && viewValue?.viewType == ClickType.DOT.viewType) return

			secondStringField += viewValue?.viewType
		}

		dataSand()
	}

	private fun nonOperationFunc(viewId: Int?) {
		when (viewId) {
			ClickType.AC.viewId -> clearAll()
			ClickType.CLEAR.viewId -> removeLast()
		}
	}

	private fun dataSand() {
		if (firstStringField.isNotEmpty()) {
			firstFieldValueLiveData.value = firstStringField
		}
		if (secondStringField.isNotEmpty()) {
			secondFieldValueLiveData.value = secondStringField
		}

		if (firstStringField.isEmpty()) {
			firstStringField = "0"; firstFieldValueLiveData.value = firstStringField
		}
		if (secondStringField.isEmpty()) {
			secondStringField = "0"; secondFieldValueLiveData.value = secondStringField
		}

		if (firstStringField.takeLast(2) == ".0") {
			firstStringField = firstStringField.dropLast(2)
			firstFieldValueLiveData.value = firstStringField
		}
		if (secondStringField.takeLast(2) == ".0") {
			secondStringField = secondStringField.dropLast(2)
			secondFieldValueLiveData.value = secondStringField
		}
	}

	private fun removeLast() {
		if (fieldType == FieldType.FIRST_FIELD) {
			if (firstStringField.isNotEmpty() && firstStringField != "0") {
				firstStringField = firstStringField.dropLast(1)
			} else if (firstStringField.isEmpty()) {
				firstStringField = "0"
			}
		} else if (fieldType == FieldType.SECOND_FIELD) {
			if (secondStringField.isNotEmpty()) {
				secondStringField = secondStringField.dropLast(1)
			} else if (secondStringField.isEmpty()) {
				secondStringField = "0"
			}
		}
	}

	private fun clearAll() {
		firstStringField = "0"
		secondStringField = "0"
	}

	fun setLengthFieldType(fieldType: FieldType) { this.fieldType = fieldType }
	fun setLengthDropDownType ( dropDownType: LengthDropDownType ) { this.dropDownType = dropDownType }

	fun setValues(timeUnit: String) {
		if (dropDownType == LengthDropDownType.DROP_FIRST) {
			firstDropValueLiveData.value = dropDownConvert(timeUnit)
			firstDescriptionValueLiveData.value = descriptionConvert(timeUnit)
			Micrometer.valueType(timeUnit)?.let { firstFieldUnit = it }
		} else if (dropDownType == LengthDropDownType.DROP_SECOND) {
			secondDropValueLiveData.value = dropDownConvert(timeUnit)
			secondDescriptionValueLiveData.value = descriptionConvert(timeUnit)
			Micrometer.valueType(timeUnit)?.let { secondFieldUnit = it }
		}

		math()
	}

	private fun descriptionConvert(timeUnit: String): String {
		return timeUnit.substringBefore(" ")
	}

	private fun dropDownConvert ( timeUnit: String ): String {
		return timeUnit.substringAfter(" ")
	}
}