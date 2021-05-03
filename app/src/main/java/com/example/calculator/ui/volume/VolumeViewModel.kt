package com.example.calculator.ui.volume

import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class VolumeViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

	enum class ClickType ( var viewId: Int, var viewType: String? = null ) {
		ZERO(R.id.figures_zero_volume, "0"),
		ONE(R.id.figures_one_volume, "1"),
		TWO(R.id.figures_two_volume, "2"),
		THREE(R.id.figures_three_volume, "3"),
		FOUR(R.id.figures_four_volume, "4"),
		FIVE(R.id.figures_five_volume, "5"),
		SIX(R.id.figures_six_volume, "6"),
		SEVEN(R.id.figures_seven_volume, "7"),
		EIGHT(R.id.figures_eight_volume, "8"),
		NINE(R.id.figures_nine_volume, "9"),
		DOT(R.id.figures_dot_volume, "."),
		AC(R.id.volume_clear_btn),
		CLEAR(R.id.volume_remove_btn);

		companion object { fun valueType(value: Int?) = values().find { it.viewId == value } }
	}

	enum class FieldType {
		FIRST_FIELD,SECOND_FIELD
	}

	enum class VolumeDropDownType {
		FIRST_DROP, SECOND_DROP
	}

	enum class CubicMillimeter ( var valueName: String, var inCubMilliliter: Double ) {
		MILLILITER( "Milliliter ml", 1000.0),
		CENTILITER( "Centiliter cl", 10000.0),
		DECILITER( "Deciliter dl", 100000.0),
		LITER( "Liter l", 1e+6),
		CUBIC_MILLIMETER( "Cubic millimeter mm³", 1.0),
		CUBIC_CENTIMETER( "Cubic centimeter cm³", 1000.0),
		CUBIC_DECIMETER( "Cubic decimeter dm³", 1000000.0),
		CUBIC_METER( "Cubic meter m³", 1e+9);

		companion object { fun valueType(value: String) = values().find { it.valueName == value } }

	}

	var firstFieldValueLiveData: MutableLiveData<String> = MutableLiveData()
	var secondFieldValueLiveData: MutableLiveData<String> = MutableLiveData()
	var firstVolumeDropValueLiveData: MutableLiveData<String> = MutableLiveData()
	var secondVolumeDropValueLiveData: MutableLiveData<String> = MutableLiveData()
	var firstVolumeDescriptionValueLiveData: MutableLiveData<String> = MutableLiveData()
	var secondVolumeDescriptionValueLiveData: MutableLiveData<String> = MutableLiveData()

	private var firstConverted = 0.0
	private var secondConverted = 0.0
	private var firstUnitType = CubicMillimeter.CUBIC_METER
	private var secondUnitType = CubicMillimeter.CUBIC_CENTIMETER
	private var dropDownType = VolumeDropDownType.FIRST_DROP
	private var fieldType = FieldType.FIRST_FIELD
	private var firstStringField = "0"
	private var secondStringField = "0"

	fun eachClick(viewId: Int?) {
		val operation = ClickType.valueType(viewId)
		if (operation?.viewType != null) operationFunc(operation)
		else if (operation?.viewType == null) nonOperationFunc(operation?.viewId)

		dataSand()
		math()
	}

	private fun math() {
		firstConverted = (firstStringField.toDouble() * firstUnitType.inCubMilliliter)
		secondConverted = (secondStringField.toDouble() * secondUnitType.inCubMilliliter)
		try {
			if ( fieldType == FieldType.FIRST_FIELD ) { secondStringField = (firstConverted / secondUnitType.inCubMilliliter).toString() }
			else { firstStringField = ( secondConverted / firstUnitType.inCubMilliliter).toString() }
		} catch (e: Exception) { }
		dataSand()
	}

	private fun operationFunc(viewValue: ClickType?) {
		if (fieldType == FieldType.FIRST_FIELD) {
			if (firstStringField == "0" && viewValue?.viewType != ClickType.DOT.viewType) firstStringField = ""
			else if (firstStringField.contains(ClickType.DOT.viewType.toString()) && viewValue?.viewType == ClickType.DOT.viewType) return

			firstStringField += viewValue?.viewType

		} else if (fieldType == FieldType.SECOND_FIELD ) {
			if (secondStringField == "0" && viewValue?.viewType != ClickType.DOT.viewType) secondStringField = ""
			else if (secondStringField.contains(ClickType.DOT.viewType.toString()) && viewValue?.viewType == ClickType.DOT.viewType) return

			secondStringField += viewValue?.viewType
		}

		dataSand()
		math()
	}

	private fun nonOperationFunc(viewId: Int?) {
		when (viewId) {
			ClickType.AC.viewId -> clearAll()
			ClickType.CLEAR.viewId -> removeLast()
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

	fun setVolumeFieldType(fieldType: FieldType) { this.fieldType = fieldType }
	fun setVolumeDropDownType ( dropDownType: VolumeDropDownType) { this.dropDownType = dropDownType }

	fun setValues(timeUnit: String) {
		if (dropDownType == VolumeDropDownType.FIRST_DROP) {
			firstVolumeDropValueLiveData.value = dropDownConvert(timeUnit)
			firstVolumeDescriptionValueLiveData.value = descriptionConvert(timeUnit)
			CubicMillimeter.valueType(timeUnit)?.let { firstUnitType = it }
		} else if (dropDownType == VolumeDropDownType.SECOND_DROP) {
			secondVolumeDropValueLiveData.value = dropDownConvert(timeUnit)
			secondVolumeDescriptionValueLiveData.value = descriptionConvert(timeUnit)
			CubicMillimeter.valueType(timeUnit)?.let { secondUnitType = it }
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