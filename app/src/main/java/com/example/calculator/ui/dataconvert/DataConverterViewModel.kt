package com.example.calculator.ui.dataconvert

import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.utils.App.Companion.prefs

class DataConverterViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

	enum class FiguresClick(var id: Int?, var type: String? = null) {
		ZERO(R.id.figures_zero_data, "0"), ONE(R.id.figures_one_data, "1"),
		TWO(R.id.figures_two_data, "2"), THREE(R.id.figures_three_data, "3"),
		FOUR(R.id.figures_four_data, "4"), FIVE(R.id.figures_five_data, "5"),
		SIX(R.id.figures_six_data, "6"), SEVEN(R.id.figures_seven_data, "7"),
		EIGHT(R.id.figures_eight_data, "8"), NINE(R.id.figures_nine_data, "9"),
		AC(R.id.data_clear_btn), CLEAR(R.id.data_remove_btn_data);

		companion object {
			fun valueOf(value: Int?) = values().find { it.id == value }
		}
	}

	private var fieldString = ""
	var toFieldLiveData: MutableLiveData<String> = MutableLiveData()
	var toSecondFieldLiveData: MutableLiveData<String> = MutableLiveData()
	var isClicked = false

	fun onButtonClick(viewId: Int?) {
		val operation = FiguresClick.valueOf(viewId)
		if (operation?.type != null) {
			onClickMath(operation)
		} else if (operation != null) onOperationClick(operation)
	}

	fun isFieldChanged(isChanged: Boolean) {
		fieldString = ""
		fieldString = if(!isChanged){ prefs?.firstField.toString() } else { prefs?.secondField.toString() }
		isClicked = isChanged

	}

	private fun onOperationClick(operation: FiguresClick) {
		when (operation) {
			FiguresClick.AC -> clearAll()
			FiguresClick.CLEAR -> removeLast()
		}
	}

	private fun clearAll() {
		fieldString = ""
		if (!isClicked) { toFieldLiveData.value = "0"; prefs?.firstField = "" }
		else { toSecondFieldLiveData.value = "0"; prefs?.secondField = "" }
	}

	private fun removeLast() {
		if (!isClicked) {
			fieldString = toFieldLiveData.value.toString()
			fieldString = fieldString.dropLast(1)
			toFieldLiveData.value = fieldString
		} else {
			fieldString = toSecondFieldLiveData.value.toString()
			fieldString = fieldString.dropLast(1)
			toSecondFieldLiveData.value = fieldString
		}
	}

	private fun onClickMath(operation: FiguresClick) {
		fieldString += operation.type
		if (!isClicked) toFieldLiveData.value = fieldString
		else toSecondFieldLiveData.value = fieldString
	}
}