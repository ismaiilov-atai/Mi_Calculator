package com.example.calculator.ui.temperature

import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class TemperatureViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

	enum class ClickType ( var viewId: Int,  var viewType: String? = null ) {
		ZERO(R.id.figures_zero_temp, "0"),
		ONE(R.id.figures_one_temp, "1"),
		TWO(R.id.figures_two_temp, "2"),
		THREE(R.id.figures_three_temp, "3"),
		FOUR(R.id.figures_four_temp, "4"),
		FIVE(R.id.figures_five_temp, "5"),
		SIX(R.id.figures_six_temp, "6"),
		SEVEN(R.id.figures_seven_temp, "7"),
		EIGHT(R.id.figures_eight_temp, "8"),
		NINE(R.id.figures_nine_temp, "9"),
		DOT(R.id.figures_dot_temp, "."),
		MINPLUS(R.id.figures_plus_minus_temp),
		AC(R.id.figures_ac_temp),
		CLEAR(R.id.figures_delete_temp);

		companion object { fun valueType (value: Int? ) = values().find { it.viewId == value } }
	}

	enum class Temperatures( var typeView: String ) {
		KELVIN ("Kelvin  K") {
			 override fun toKelvin(toKelvin: Double): Double {
				return toKelvin
			}
			 override fun fromKelvin(kelvin: Double): Double {
				return kelvin
			}
		},
		CELSIUS ("Celsius °C") {
			 override fun toKelvin(toKelvin: Double): Double {
				return toKelvin + 273.15
			}
			 override fun fromKelvin(kelvin: Double): Double {
				return kelvin - 273.15
			}
		},
		Rankine ("Rankine °R") {
			override fun toKelvin(toKelvin: Double): Double {
				return (toKelvin) * (5.0 / 9.0)
			}
			override fun fromKelvin(kelvin:Double): Double {
				return (kelvin * 1.8)
			}
		},
		Reaumur ("Reaumur °Ré") {
			override fun toKelvin(toKelvin: Double): Double {
				return (toKelvin / 0.80 ) + 273.15
			}
			override fun fromKelvin(kelvin:Double): Double {
				return (kelvin - 273.15) * 0.80
			}
		},
		FAHRENHEIT ("Fahrenheit °F") {
			 override fun toKelvin(toKelvin: Double): Double {
				return (toKelvin + 459.67) * (5.0 / 9.0)
			}
			 override fun fromKelvin(kelvin:Double): Double {
				return (kelvin * (9.0 / 5.0)) - 459.67
			}
		};

		 abstract fun toKelvin(toKelvin: Double): Double
		 abstract fun fromKelvin(kelvin: Double): Double

		fun convert(from: Double, to: Temperatures): Double {
			return to.fromKelvin(this.toKelvin(from))
		}
		companion object { fun tempType ( value: String ) = values().find { it.typeView == value } }
	}

	enum class FieldType {
		FIRST_FIELD,SECOND_FIELD
	}

	enum class DropDownType {
		FIRST_DROP,SECOND_DROP
	}

	private var isNegative = false
	private var isSecondNegative = false
	private var firstTempType = Temperatures.CELSIUS
	private var secondTempType = Temperatures.FAHRENHEIT

	private var  dropDownType = DropDownType.FIRST_DROP
	private var fieldType = FieldType.FIRST_FIELD

	var firstDropDownLiveData: MutableLiveData<String> = MutableLiveData()
	var secondDropDownLiveData: MutableLiveData<String> = MutableLiveData()

	var firstFieldValueLiveData: MutableLiveData<String> = MutableLiveData()
	var secondFieldValueLiveData: MutableLiveData<String> = MutableLiveData()

	var firstDescriptionLiveData: MutableLiveData<String> = MutableLiveData()
	var secondDescriptionLiveData: MutableLiveData<String> = MutableLiveData()

	private var firstStringField = "0"
	private var secondStringField = "0"

	fun eachClick ( viewId: Int? ) {
			val operation = ClickType.valueType(viewId)
			if (operation?.viewType != null) { operationClick(operation) }
			else if (operation?.viewType == null) { nonOperationType(operation?.viewId) }

		dataSand()
		math()
	}

	private fun math() {
		if (fieldType == FieldType.FIRST_FIELD)
			firstTempType.convert(firstStringField.toDouble(),secondTempType).let { secondStringField = it.toString() }
		else if ( fieldType == FieldType.SECOND_FIELD )
			secondTempType.convert(secondStringField.toDouble(),firstTempType).let { firstStringField = it.toString() }

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
			ClickType.MINPLUS.viewId -> minusAdd()
		}
	}

	private fun minusAdd() {
		if (fieldType == FieldType.FIRST_FIELD ) {
			if (!isNegative) {
				firstStringField =  "-$firstStringField"
				isNegative = true
			} else {
					firstStringField = firstStringField.removePrefix("-")
					isNegative = false
				}
		} else if ( fieldType == FieldType.SECOND_FIELD ) {
			if (!isSecondNegative) {
				secondStringField =  "-$secondStringField"
				isSecondNegative = true
			} else {
				secondStringField = secondStringField.removePrefix("-")
				isSecondNegative = false
			}
		}
		dataSand()
	}

	private fun removeLast() {
		if (fieldType == FieldType.FIRST_FIELD ) {
			if (firstStringField.isNotEmpty() && firstStringField != "0" &&  firstStringField != "-0") {
				firstStringField = firstStringField.dropLast(1)
			} else if ( firstStringField.isEmpty() || firstStringField == "-0") { firstStringField = "0" }
		} else if ( fieldType == FieldType.SECOND_FIELD ) {
			if (secondStringField.isNotEmpty() && secondStringField != "0" && secondStringField != "-0") {
				secondStringField = secondStringField.dropLast(1)
			} else if (secondStringField.isEmpty() || secondStringField == "-0" ) {
				secondStringField = "0"
			}
		}
	}

	private fun clearAll() {
	    firstStringField = "0"
		secondStringField = "0"
	}

	private fun dataSand() {
		if ( firstStringField.isNotEmpty() ) { firstFieldValueLiveData.value = firstStringField }
		if ( secondStringField.isNotEmpty() ) { secondFieldValueLiveData.value = secondStringField }

		if ( firstStringField.isEmpty() ) { firstStringField = "0"; firstFieldValueLiveData.value = firstStringField }
		if ( secondStringField.isEmpty()) { secondStringField = "0"; secondFieldValueLiveData.value = secondStringField }
	}

	fun chosenField(chosenField: FieldType) {
		fieldType = chosenField
		math()
	}
	fun chosenDropType (chosenDropType: DropDownType ) {
		dropDownType = chosenDropType
		math()
	}

	fun setDropDownValue ( dropValue: String ) {
		if ( dropDownType == DropDownType.FIRST_DROP ) {
			firstDropDownLiveData.value = dropValue.takeLast(3 )
			firstDescriptionLiveData.value = dropValue.dropLast(3)
			Temperatures.tempType(dropValue)?.let { it.let { firstTempType = it  } }

		} else if ( dropDownType == DropDownType.SECOND_DROP ) {
			secondDropDownLiveData.value = dropValue.takeLast(3 )
			secondDescriptionLiveData.value = dropValue.dropLast(3)
			Temperatures.tempType(dropValue)?.let { it.let { secondTempType = it  } }
		}

		math()
	}
}