package com.example.calculator.ui.numerial

import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import java.lang.Exception
import java.math.BigInteger

class NumeralViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

	enum class TypeChosen {
		FIRST,SECOND
	}

	enum class FiguresClick(var id: Int?, var type: String? = null) {
		ZERO(R.id.figures_zero_numeral, "0"),
		ONE(R.id.figures_one_numeral, "1"),
		TWO(R.id.figures_two_numeral, "2"),
		THREE(R.id.figures_three_numeral, "3"),
		FOUR(R.id.figures_four_numeral, "4"),
		FIVE(R.id.figures_five_numeral, "5"),
		SIX(R.id.figures_six_numeral, "6"),
		SEVEN(R.id.figures_seven_numeral, "7"),
		EIGHT(R.id.figures_eight_numeral, "8"),
		NINE(R.id.figures_nine_numeral, "9"),
		DOT(R.id.figures_dot_numeral, "."),
		F(R.id.figures_f_numeral, "F"),
		E(R.id.figures_e_numeral, "E"),
		D(R.id.figures_d_numeral, "D"),
		C(R.id.figures_c_numeral, "C"),
		B(R.id.figures_b_numeral, "B"),
		A(R.id.figures_a_numeral, "A"),

		AC(R.id.figures_clear_numeral),
		DELETE(R.id.figures_delete_numeral);

		companion object {
			fun valueOf(value: Int?) = values().find { it.id == value }
		}
	}

	enum class PermissionType {
		BIN,OCT,DEC,HEX
	}

	enum class TypeField {
		FIRSTFIELD,SECONDFIELD
	}

	var chosenType = TypeChosen.FIRST
	var fieldType = TypeField.FIRSTFIELD

	var permissionType = PermissionType.BIN

	var firstDropDownValue: MutableLiveData<String> = MutableLiveData()
	var secondDropDownValue: MutableLiveData<String> = MutableLiveData()

	var firstFieldValue: MutableLiveData<String> = MutableLiveData()
	var secondFieldValue: MutableLiveData<String> = MutableLiveData()

	var firstFieldPermissionLiveData: MutableLiveData<PermissionType> = MutableLiveData()
	var secondFieldPermissionLiveData: MutableLiveData<PermissionType> = MutableLiveData()

	var firstPermissionReturnType = PermissionType.HEX
	var secondPermissionReturnType = PermissionType.BIN


    private var fieldOneString = "0"
	private var fieldTwoString = "0"

	fun clickButton(viewId: Int?) {
		val operation = FiguresClick.valueOf(viewId)
		if (operation?.type != null) {
			if (fieldType == TypeField.FIRSTFIELD) {
				if (fieldOneString == "0" && operation.type != FiguresClick.DOT.type ) { fieldOneString = "" }
				else if ( fieldOneString.contains(FiguresClick.DOT.type.toString()) &&  operation.type == FiguresClick.DOT.type ) { return }

				fieldOneString += operation.type
			} else if (fieldType == TypeField.SECONDFIELD) {
				if (fieldTwoString == "0" && operation.type != FiguresClick.DOT.type ) { fieldTwoString = "" }
				else if ( fieldTwoString.contains(FiguresClick.DOT.type.toString()) &&  operation.type == FiguresClick.DOT.type ) { return }

				fieldTwoString += operation.type
			}
		} else { operation?.let { onOperationClick(it) } }

		dataSend()
		math()
	}

	private fun math() {
		try {
		if (fieldType == TypeField.FIRSTFIELD){
			fieldTwoString = convertFromDecimal(fieldOneString,convertNumSystemRegex(firstPermissionReturnType),convertNumSystemRegex(secondPermissionReturnType))
		} else if (fieldType == TypeField.SECONDFIELD) {
			fieldOneString  =  convertFromDecimal(fieldTwoString,convertNumSystemRegex(secondPermissionReturnType),convertNumSystemRegex(firstPermissionReturnType))
		}
		} catch (e: Exception) {}
		dataSend()
	}

	private fun convertFromDecimal(fieldValue: String,redix: Int, numeralSys: Int): String {
		return BigInteger(fieldValue, redix).toString(numeralSys)
	}



	private fun dataSend() {
			if (fieldOneString.takeLast(2) == ".0" && fieldType == TypeField.SECONDFIELD) {
				fieldOneString = fieldOneString.dropLast(2)
			}

			if (fieldTwoString.takeLast(2) == ".0" && fieldType == TypeField.FIRSTFIELD) {
				fieldTwoString = fieldTwoString.dropLast(2)
			}

			if (fieldOneString.isNotEmpty()) {
				firstFieldValue.value = fieldOneString
			} else {
				firstFieldValue.value = "0"
			}

			if (fieldTwoString.isNotEmpty()) {
				secondFieldValue.value = fieldTwoString
			} else {
				secondFieldValue.value = "0"
			}

		if (fieldType == TypeField.FIRSTFIELD && fieldOneString.isEmpty()) {
			fieldTwoString = "0"
			secondFieldValue.value = fieldTwoString
		}

		if (fieldType == TypeField.SECONDFIELD && fieldTwoString.isEmpty()) {
			fieldOneString = "0"
			firstFieldValue.value = fieldOneString
		}
	}

	private fun onOperationClick(it: FiguresClick) {
		if (it == FiguresClick.DELETE ) removeLast()
		else if (it == FiguresClick.AC ) clearAll()
	}

	private fun removeLast() {
		if ( fieldType == TypeField.FIRSTFIELD ) fieldOneString = fieldOneString.dropLast(1)
		else if ( fieldType == TypeField.SECONDFIELD ) fieldTwoString = fieldTwoString.dropLast(1)
	}

	private fun clearAll() {
		fieldOneString = "0"
		fieldTwoString = "0"
	}

	fun setValueDropDown(dropDownValue: String) {
		 when (chosenType) {
			 TypeChosen.FIRST -> { firstDropDownValue.value = getLastInitials(dropDownValue) }
			 TypeChosen.SECOND -> { secondDropDownValue.value = getLastInitials(dropDownValue) }
		 }
	}

	private fun getLastInitials(requiredValue: String): String { return requiredValue.takeLast(3) }

	fun setType(typeChosen: TypeChosen) { this.chosenType = typeChosen }

	fun setField(fieldType: TypeField) { this.fieldType = fieldType }

	fun setPermission(permission: String) {
		when (permission) {
			"Binary BIN" -> permissionType = PermissionType.BIN
			"Octal OCT" -> permissionType = PermissionType.OCT
			"Decimal DEC" -> permissionType = PermissionType.DEC
			"Hexadecimal HEX" -> permissionType = PermissionType.HEX
		}
		if (fieldType == TypeField.FIRSTFIELD ) { firstFieldPermissionLiveData.value = permissionType.also { firstPermissionReturnType = it } }
		else if (fieldType == TypeField.SECONDFIELD) secondFieldPermissionLiveData.value = permissionType.also { secondPermissionReturnType  = it }

		fieldTwoString = "0"
		fieldOneString = "0"

		math()
	}

	private fun convertNumSystemRegex ( permission: PermissionType ): Int {
		return when ( permission ) {
			PermissionType.HEX -> 16
			PermissionType.DEC -> 10
			PermissionType.BIN -> 2
			PermissionType.OCT -> 8
		}
	}

	fun getPermissionType(fieldType: TypeField): PermissionType {
		return when (fieldType) {
			TypeField.FIRSTFIELD -> firstPermissionReturnType
			TypeField.SECONDFIELD -> secondPermissionReturnType
		}
	}
}