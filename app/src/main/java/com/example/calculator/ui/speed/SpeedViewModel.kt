package com.example.calculator.ui.speed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.utils.Constants

class SpeedViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

	enum class ClickType(var  clickId: Int, val clickType: String? = null) {
		ZERO(R.id.figures_zero_speed, "0"),
		ONE(R.id.figures_one_speed, "1"),
		TWO(R.id.figures_two_speed, "2"),
		THREE(R.id.figures_three_speed, "3"),
		FOUR(R.id.figures_four_speed, "4"),
		FIVE(R.id.figures_five_speed, "5"),
		SIX(R.id.figures_six_speed, "6"),
		SEVEN(R.id.figures_seven_speed, "7"),
		EIGHT(R.id.figures_eight_speed, "8"),
		NINE(R.id.figures_nine_speed, "9"),
		DOT(R.id.figures_dot_speed, "."),
		AC(R.id.speed_clear_btn),
		CLEAR(R.id.speed_remove_btn);

		companion object {
			fun valueOf( value: Int? ) = values().find { it.clickId == value }
		}
	}

	enum class MeterSecond(var type: String, var valueSeconds: Double?) {
		LIGHTSPEED(Constants.LIGHT_SPEED, 2.998e+8),
		METER_PER_SECOND(Constants.METER_PER_SECOND, 1.0),
		KILOMETER_PER_SECOND(Constants.KILOMETER_PER_SECOND, 1000.0),
		KILOMETER_PER_HOUR(Constants.KILOMETER_PER_HOUR, 3.60);

		companion object {
			fun ofType(value: String?) = values().find { it.type == value }
		}
	}

	enum class ChosenField {
		FIRST,SECOND
	}

	enum class DropDownType { FIRSTDROP, SECONDDROP }

	var firstFieldMutableLiveData: MutableLiveData<String> = MutableLiveData()
	var secondFieldMutableLiveData: MutableLiveData<String> = MutableLiveData()

	var firstDropDownLiveData: MutableLiveData<String> = MutableLiveData()
	var secondDropDownLiveData: MutableLiveData<String> = MutableLiveData()

	var firstDescriptionLiveData: MutableLiveData<String> = MutableLiveData()
	var secondDescriptionLiveData: MutableLiveData<String> = MutableLiveData()


	private var chosenField = ChosenField.FIRST
	private var dropDownType = DropDownType.FIRSTDROP

	private var stringFieldFirst = "0"
	private var stringFieldSecond = "0"

	private var firstValueUnit: Double = 0.0
	private var secondValueUnit: Double = 0.0

	private var firstSpeedUnit: MeterSecond? = MeterSecond.METER_PER_SECOND
	private var secondSpeedUnit: MeterSecond? = MeterSecond.KILOMETER_PER_SECOND

	fun onBtnClick ( viewId: Int? ) {
		val operation = ClickType.valueOf(viewId)

		if (operation?.clickType != null) { operationClick(operation) }
		else { nonOperationClick(operation?.clickId) }

		dataSand()
		math()
	}


	private fun math() {
		try {
			firstValueUnit = ( stringFieldFirst.toDouble() ) * firstSpeedUnit?.valueSeconds!!
			secondValueUnit = ( stringFieldSecond.toDouble() ) * secondSpeedUnit?.valueSeconds!!

			if ( chosenField == ChosenField.FIRST ) {
				stringFieldSecond = ( firstValueUnit / secondSpeedUnit?.valueSeconds!! ).toString()
			}
			else if ( chosenField == ChosenField.SECOND ) {
				stringFieldFirst = ( secondValueUnit / firstSpeedUnit?.valueSeconds!! ).toString()
			}
		} catch ( e: Exception ) {}

		dataSand()
	}

	private fun nonOperationClick(clickId: Int?) {
		if (clickId == ClickType.AC.clickId) clearAll()
		else if (clickId == ClickType.CLEAR.clickId) removeLast()
	}

	private fun removeLast() {
		if ( chosenField == ChosenField.FIRST ) {
			stringFieldFirst = stringFieldFirst.dropLast(1)
			if (stringFieldFirst.isEmpty()) stringFieldFirst = ""
		} else if ( chosenField == ChosenField.SECOND ) {
			stringFieldSecond = stringFieldSecond.dropLast(1)
			if (stringFieldSecond.isEmpty()) stringFieldSecond = ""
		}
	}

	private fun clearAll() {
		stringFieldSecond = ""
		stringFieldFirst = ""
	}

	private fun operationClick(operation: ClickType) {
		if (chosenField == ChosenField.FIRST) {
			if(stringFieldFirst == "0" && operation.clickType != ClickType.DOT.toString()) stringFieldFirst = ""

			stringFieldFirst += operation.clickType
		} else {
			if(stringFieldSecond == "0" && operation.clickType != ClickType.DOT.toString()) stringFieldSecond = ""

			stringFieldSecond += operation.clickType
		}
	}

	private fun dataSand() {
		if (stringFieldFirst.isNotEmpty()) { firstFieldMutableLiveData.value = stringFieldFirst }
		else { firstFieldMutableLiveData.value = "0" }

		if (stringFieldSecond.isNotEmpty() ) { secondFieldMutableLiveData.value = stringFieldSecond }
		else { secondFieldMutableLiveData.value = "0" }

		if (stringFieldFirst.isEmpty() && chosenField == ChosenField.FIRST) {
			stringFieldSecond = "0"
			secondFieldMutableLiveData.value = stringFieldSecond
		}

		if ( stringFieldSecond.isEmpty() && chosenField == ChosenField.SECOND ) {
			stringFieldFirst = "0"
			firstFieldMutableLiveData.value = stringFieldFirst
		}
	}

	fun setDropDownType (dropDownType: DropDownType) {
		this.dropDownType = dropDownType
	}

	fun setChosenField(chosenField: ChosenField) {
		this.chosenField = chosenField
	}

	fun setDropDownValue ( dropDownValue: String ) {
		if ( dropDownType == DropDownType.FIRSTDROP )  {
			firstDropDownLiveData.value = dropDownConverter(dropDownValue)
			firstDescriptionLiveData.value = dropDescriptionConverter(dropDownValue)
		}
		else if (dropDownType == DropDownType.SECONDDROP ) {
			secondDropDownLiveData.value = dropDownConverter(dropDownValue)
			secondDescriptionLiveData.value = dropDescriptionConverter(dropDownValue)
		}
	}

	private fun dropDownConverter ( convertValue: String ): String {
		try {
			if (chosenField == ChosenField.FIRST) {
				firstSpeedUnit = MeterSecond.ofType(convertValue)
			} else if (chosenField == ChosenField.SECOND) {
				secondSpeedUnit = MeterSecond.valueOf(convertValue)
			}
		} catch (e: Exception) {}

			return when (convertValue) {
				Constants.LIGHT_SPEED -> "c"
				Constants.METER_PER_SECOND -> "m/s"
				Constants.KILOMETER_PER_SECOND -> "km/s"
				Constants.KILOMETER_PER_HOUR -> "km/h"
				else -> "chosen non"
			}

	}

	private fun dropDescriptionConverter ( dropDescription: String ): String {
		return  when (dropDescription) {
			Constants.LIGHT_SPEED ->  "Lightspeed"
			Constants.METER_PER_SECOND -> "Meter per second"
			Constants.KILOMETER_PER_SECOND -> "Kilometer per second"
			Constants.KILOMETER_PER_HOUR -> "Kilometer per hour"
			else -> "chosen non"
		}
	}
}