package com.example.calculator.ui.time

import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.ui.dataconvert.DataConverterViewModel

class TimeViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

	enum class ClickType ( var viewId: Int, var viewType: String? = null) {
		ZERO(R.id.figures_zero_time, "0"),
		ONE(R.id.figures_one_time, "1"),
		TWO(R.id.figures_two_time, "2"),
		THREE(R.id.figures_three_time, "3"),
		FOUR(R.id.figures_four_time, "4"),
		FIVE(R.id.figures_five_time, "5"),
		SIX(R.id.figures_six_time, "6"),
		SEVEN(R.id.figures_seven_time, "7"),
		EIGHT(R.id.figures_eight_time, "8"),
		NINE(R.id.figures_nine_time, "9"),
		DOT(R.id.figures_dot_time, "."),
		AC(R.id.time_clear_btn),
		CLEAR(R.id.time_remove_btn);

		companion object { fun valueType (value: Int? ) = values().find { it.viewId == value } }
	}

	enum class Milliseconds ( var valueName: String ,var valueMilliseconds: Double ) {
		MILLISECONDS ("Millisecond ms",1.0),
		SECOND ("Second s",1000.0),
		MINUTE ("Minute min",60000.0),
		HOUR ( "Hour h",3.6e+6),
		DAY ( "Day d",8.64e+7),
		WEEK ( "Week wk",6.048e+8),
		YEAR ("Year y",3.154e+10);

		companion object { fun valueOfType(value: String?) = values().find { it.valueName == value } }
	}

	enum class FieldType {
		FIRST_FIELD, SECOND_FIELD
	}

	enum class TimeDropDownType {
		DROP_FIRST, DROP_SECOND
	}

	private var firstTimeUnit = Milliseconds.MINUTE
	private var secondTimeUnit = Milliseconds.SECOND

	var firstFieldValueLiveData: MutableLiveData<String> = MutableLiveData()
	var secondFieldValueLiveData: MutableLiveData<String> = MutableLiveData()

	var firstDropValueLiveData: MutableLiveData<String> = MutableLiveData()
	var secondDropValueLiveData: MutableLiveData<String> = MutableLiveData()

	var firstDescriptionValueLiveData: MutableLiveData<String> = MutableLiveData()
	var secondDescriptionValueLiveData: MutableLiveData<String> = MutableLiveData()

	private var dropDownType = TimeDropDownType.DROP_FIRST
	private var fieldType = FieldType.FIRST_FIELD
	private var firstStringField = "0"
	private var secondStringField = "0"

	private var firstFieldMilliseconds = 0.0
	private var secondFieldMilliseconds = 0.0

	fun eachClick ( viewId: Int? ) {
		val operation = ClickType.valueType(viewId)
		if (operation?.viewType != null) { operationClick(operation) }
		else if (operation?.viewType == null) { nonOperationType(operation?.viewId) }

		dataSand()
		math()

	}

	private fun math() {
		firstFieldMilliseconds = (firstStringField.toDouble() * firstTimeUnit.valueMilliseconds)
		secondFieldMilliseconds = (secondStringField.toDouble() * secondTimeUnit.valueMilliseconds)

		try {
			if ( fieldType == FieldType.FIRST_FIELD ) {
				secondStringField = (firstFieldMilliseconds / secondTimeUnit.valueMilliseconds).toString()
			} else {
				firstStringField = (secondFieldMilliseconds / firstTimeUnit.valueMilliseconds).toString()
			}
		} catch (e: Exception) {}

		dataSand()
	}

	private fun operationClick(operation: ClickType?) {
		if (fieldType == FieldType.FIRST_FIELD) {
			if ( firstStringField == "0" && operation?.viewType != ClickType.DOT.viewType) firstStringField = ""
			else if ( firstStringField.contains(ClickType.DOT.viewType.toString()) && operation?.viewType == ClickType.DOT.viewType ) { return }
			firstStringField += operation?.viewType
		} else if ( fieldType == FieldType.SECOND_FIELD ) {
			if ( secondStringField == "0" && operation?.viewType != ClickType.DOT.viewType ) secondStringField = ""
			else if ( secondStringField.contains(ClickType.DOT.viewType.toString()) && operation?.viewType == ClickType.DOT.viewType ) { return }
			secondStringField += operation?.viewType
		}
	}

	private fun nonOperationType(viewId: Int?) {
		when (viewId) {
			ClickType.AC.viewId -> clearAll()
			ClickType.CLEAR.viewId -> removeLast()
		}
	}


	private fun dataSand() {
		if ( firstStringField.isNotEmpty() ) { firstFieldValueLiveData.value = firstStringField }
		if ( secondStringField.isNotEmpty() ) { secondFieldValueLiveData.value = secondStringField }

		if ( firstStringField.isEmpty() ) { firstStringField = "0"; firstFieldValueLiveData.value = firstStringField }
		if ( secondStringField.isEmpty()) { secondStringField = "0"; secondFieldValueLiveData.value = secondStringField }

		if (firstStringField.takeLast(2 ) == ".0" ) {
			firstStringField = firstStringField.dropLast(2)
			firstFieldValueLiveData.value = firstStringField
		}

		if (secondStringField.takeLast(2 ) == ".0" ) {
			secondStringField = secondStringField.dropLast(2)
			secondFieldValueLiveData.value = secondStringField
		}

	}

	private fun removeLast() {
		if (fieldType == FieldType.FIRST_FIELD ) {
			if (firstStringField.isNotEmpty() && firstStringField != "0") {
				firstStringField = firstStringField.dropLast(1)
			} else if ( firstStringField.isEmpty() ) { firstStringField = "0" }
		} else if ( fieldType == FieldType.SECOND_FIELD ) {
			if (secondStringField.isNotEmpty()) {
				secondStringField = secondStringField.dropLast(1)
			} else if (secondStringField.isEmpty() ) {
				secondStringField = "0"
			}
		}
	}

	private fun clearAll() {
		firstStringField = "0"
		secondStringField = "0"
	}

	fun chosenField ( chosenField: FieldType ) { fieldType = chosenField }

	private fun dropDownConvert ( timeUnit: String ): String {
		return timeUnit.substringAfter(" ")
	}

	private fun descriptionConvert ( timeUnit: String ): String {
		return timeUnit.substringBefore(" ")
	}

	fun setTimeDropDown (timeDropType: TimeDropDownType ) { dropDownType = timeDropType }

	fun setValues ( timeUnit: String ) {
		if ( dropDownType == TimeDropDownType.DROP_FIRST ) {
			firstDropValueLiveData.value = dropDownConvert(timeUnit)
			firstDescriptionValueLiveData.value = descriptionConvert(timeUnit)
			Milliseconds.valueOfType(timeUnit)?.let { firstTimeUnit = it }
		} else if ( dropDownType == TimeDropDownType.DROP_SECOND ) {
			secondDropValueLiveData.value = dropDownConvert(timeUnit)
			secondDescriptionValueLiveData.value = descriptionConvert(timeUnit)
			Milliseconds.valueOfType(timeUnit)?.let { secondTimeUnit = it }
		}

		math()
	}

}