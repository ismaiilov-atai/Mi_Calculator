package com.example.calculator.ui.mass

import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import java.lang.Exception

class MassViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

	enum class ClickType ( var viewId: Int, var viewType: String? = null ) {
		ZERO(R.id.figures_zero_mass, "0"),
		ONE(R.id.figures_one_mass, "1"),
		TWO(R.id.figures_two_mass, "2"),
		THREE(R.id.figures_three_mass, "3"),
		FOUR(R.id.figures_four_mass, "4"),
		FIVE(R.id.figures_five_mass, "5"),
		SIX(R.id.figures_six_mass, "6"),
		SEVEN(R.id.figures_seven_mass, "7"),
		EIGHT(R.id.figures_eight_mass, "8"),
		NINE(R.id.figures_nine_mass, "9"),
		DOT(R.id.figures_dot_mass, "."),
		AC(R.id.mass_clear_btn),
		CLEAR(R.id.mass_remove_btn);

		companion object { fun valueType(value: Int?) = values().find { it.viewId == value } }
	}

	enum class Micrograms (var nameValue: String, var inMicrograms: Double ) {
		MICROGRAMS("Microgram ug",1.0),
		MILLIGRAM("Milligram mg",1000.0),
		GRAM("Gram g", 1e+6),
		KILOGRAM("Kilogram kg",1e+9),
		TONNE("Tonne t",1e+12);

		companion object { fun valueType(value: String) = values().find { it.nameValue == value } }
	}

	enum class MessDropDownType {
		FIRST_DROP, SECOND_DROP
	}

	enum class FieldType {
		FIRST_FIELD,SECOND_FIELD
	}

	private var firstUnitType = Micrograms.KILOGRAM
	private var secondUnitType = Micrograms.GRAM

	var firstFieldValueLiveData: MutableLiveData<String> = MutableLiveData()
	var secondFieldValueLiveData: MutableLiveData<String> = MutableLiveData()

	var firstMassDropValueLiveData: MutableLiveData<String> = MutableLiveData()
	var secondMassDropValueLiveData: MutableLiveData<String> = MutableLiveData()

	var firstMassDescriptionValueLiveData: MutableLiveData<String> = MutableLiveData()
	var secondMassDescriptionValueLiveData: MutableLiveData<String> = MutableLiveData()

	private var firstConvertedType = 0.0
	private var secondConvertedType = 0.0
	private var dropDownType = MessDropDownType.FIRST_DROP
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
		firstConvertedType = ( firstStringField.toDouble() * firstUnitType?.inMicrograms!! )
		secondConvertedType = ( secondStringField.toDouble() * secondUnitType?.inMicrograms!!)
		try {
			if ( fieldType == FieldType.FIRST_FIELD ) { secondStringField = (firstConvertedType / secondUnitType.inMicrograms).toString() }
			else { firstStringField = ( secondConvertedType / firstUnitType.inMicrograms).toString() }
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

	fun setMassFieldType(fieldType: FieldType) { this.fieldType = fieldType }
	fun setMassDropDownType ( dropDownType: MessDropDownType) { this.dropDownType = dropDownType }

	fun setValues(timeUnit: String) {
		if (dropDownType == MessDropDownType.FIRST_DROP) {
			firstMassDropValueLiveData.value = dropDownConvert(timeUnit)
			firstMassDescriptionValueLiveData.value = descriptionConvert(timeUnit)
			Micrograms.valueType(timeUnit)?.let { firstUnitType = it }
		} else if (dropDownType == MessDropDownType.SECOND_DROP) {
			secondMassDropValueLiveData.value = dropDownConvert(timeUnit)
			secondMassDescriptionValueLiveData.value = descriptionConvert(timeUnit)
			Micrograms.valueType(timeUnit)?.let { secondUnitType = it }
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