package com.example.calculator.ui.dataconvert

import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.utils.Constants

class DataConverterViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

	enum class FiguresClick(var id: Int?, var type: String? = null) {
		ZERO(R.id.figures_zero_data, "0"),
		ONE(R.id.figures_one_data, "1"),
		TWO(R.id.figures_two_data, "2"),
		THREE(R.id.figures_three_data, "3"),
		FOUR(R.id.figures_four_data, "4"),
		FIVE(R.id.figures_five_data, "5"),
		SIX(R.id.figures_six_data, "6"),
		SEVEN(R.id.figures_seven_data, "7"),
		EIGHT(R.id.figures_eight_data, "8"),
		NINE(R.id.figures_nine_data, "9"),
		DOT(R.id.figures_dot_data,"."),
		AC(R.id.data_clear_btn),
		CLEAR(R.id.data_remove_btn_data);

		companion object {
			fun valueOf(value: Int?) = values().find { it.id == value }
		}
	}

	enum class Data(var type: String, var valueByte: Double?) {
		Byte("Byte B", 1.0),
		Kilobyte("Kilobyte KB", 1000.0),
		Megabyte("Megabyte MB", 1000000.0),
		Gigabyte("Gigabyte GB", 1e+9),
		Terabyte("Terabyte TB", 1e+12),
		Petabyte("Petabyte PB", 1e-6);

		companion object {
			fun valueOfType(value: String?) = values().find { it.type == value }
		}
	}

	enum class Type {
		ONE, TWO
	}

	var firstDropDownPickLiveData: MutableLiveData<String> = MutableLiveData()
	var secondDropDownPickLiveData: MutableLiveData<String> = MutableLiveData()

	var toFieldLiveData: MutableLiveData<String> = MutableLiveData()
	var toSecondFieldLiveData: MutableLiveData<String> = MutableLiveData()

	var firstUnitDescripLiveData: MutableLiveData<String> = MutableLiveData()
	var secondUnitDescripLiveData: MutableLiveData<String> = MutableLiveData()

	private var firstUnit: Data? = Data.Megabyte
	private var secondUnit: Data? = Data.Kilobyte

	private var fieldOneData: Double = 0.0
	private var fieldTwoData: Double = 0.0

	private var fieldOneString = "0"
	private var fieldTwoString = "0"

	private var type = Type.ONE

	fun onButtonClick(viewId: Int?) {
		val operation = FiguresClick.valueOf(viewId)

		if (operation?.type != null) {
			if (type == Type.ONE) {
				if (fieldOneString == "0" && operation.type != "." ) { fieldOneString = "" }

				fieldOneString += operation.type
			} else if (type == Type.TWO) {
				if (fieldTwoString == "0" && operation.type != "."  ) { fieldTwoString = "" }

				fieldTwoString += operation.type
			}
		} else {
			operation?.let { onOperationClick(it) }
		}

		sendData()
		math()
	}

	fun isFieldChanged(type: Type) {
		this.type = type

		math()
	}

	private fun math() {
		try {
			fieldOneData = (fieldOneString).toDouble() * firstUnit?.valueByte!!
			fieldTwoData = (fieldTwoString).toDouble() * secondUnit?.valueByte!!

			if (type == Type.ONE) {
				fieldTwoString = ((fieldOneData) / secondUnit?.valueByte!!).toString()
			} else if (type == Type.TWO) {
				fieldOneString = ((fieldTwoData) / firstUnit?.valueByte!!).toString()
			}

		} catch (e: Exception) {}

		sendData()
	}

	private fun sendData() {
		if (fieldOneString.takeLast(2) == ".0" && type == Type.TWO) {
			fieldOneString = fieldOneString.dropLast(2)
		}

		if (fieldTwoString.takeLast(2) == ".0" && type == Type.ONE) {
			fieldTwoString = fieldTwoString.dropLast(2)
		}

		if (fieldOneString.isNotEmpty()) {
			toFieldLiveData.value = fieldOneString
		} else {
			toFieldLiveData.value = "0"
		}

		if (fieldTwoString.isNotEmpty()) {
			toSecondFieldLiveData.value = fieldTwoString
		} else {
			toSecondFieldLiveData.value = "0"
		}

		if (type == Type.ONE && fieldOneString.isEmpty()) {
			fieldTwoString = "0"
			toSecondFieldLiveData.value = fieldTwoString
		}

		if (type == Type.TWO && fieldTwoString.isEmpty()) {
			fieldOneString = "0"
			toFieldLiveData.value = fieldOneString
		}
	}

	private fun onOperationClick(operation: FiguresClick) {
		when (operation) {
			FiguresClick.AC -> clearAll()
			FiguresClick.CLEAR -> removeLast()
			else -> return
		}
	}

	private fun clearAll() {
		fieldOneString = "0"
		fieldTwoString = "0"

		sendData()
	}

	private fun removeLast() {
		if (type == Type.ONE) {
			fieldOneString = fieldOneString.dropLast(1)
		} else if (type == Type.TWO) {
			fieldTwoString = fieldTwoString.dropLast(1)
		}
	}

	fun dropDownSet(unitValue: String, type: Type){
		if (type == Type.ONE) {
			firstUnitDescripLiveData.value = unitValue
			firstDropDownPickLiveData.value = convertString(unitValue)
			firstUnit = Data.valueOfType(unitValue)
		} else if (type == Type.TWO) {
			secondUnitDescripLiveData.value = unitValue
			secondDropDownPickLiveData.value = convertString(unitValue)
			secondUnit = Data.valueOfType(unitValue)
		}

		math()
	}

	private fun convertString(valueToConvert: String): String {
		return 	when (valueToConvert) {
				Constants.KEY_BYTE_B -> "B"
				Constants.KEY_KILOBYTE_KB -> "KB"
				Constants.KEY_MEGABYTE_MB -> "MB"
				Constants.KEY_GIGABYTE_GB -> "GB"
				Constants.KEY_TERABYTE_TB -> "TB"
				Constants.KEY_PETABYTE_PB -> "PB"
			else -> ""
		}
	}
}