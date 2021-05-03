package com.example.calculator.ui.discount


import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class DiscountViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

	enum class ClickType( var viewId: Int, var viewType: String? = null ) {
		ZERO(R.id.figures_zero_discount, "0"),
		ONE(R.id.figures_one_discount, "1"),
		TWO(R.id.figures_two_discount, "2"),
		THREE(R.id.figures_three_discount, "3"),
		FOUR(R.id.figures_four_discount, "4"),
		FIVE(R.id.figures_five_discount, "5"),
		SIX(R.id.figures_six_discount, "6"),
		SEVEN(R.id.figures_seven_discount, "7"),
		EIGHT(R.id.figures_eight_discount, "8"),
		NINE(R.id.figures_nine_discount, "9"),
		DOT(R.id.figures_dot_discount, "."),
		AC(R.id.discount_clear_btn),
		CLEAR(R.id.discount_remove_btn);

		companion object { fun valueType( value: Int? ) = values().find { it.viewId == value } }
	}

	enum class FieldType {
		ORIGINAL_PRICE, DISCOUNT
	}

	var originalFieldLiveData: MutableLiveData<String> = MutableLiveData()
	var discountFieldLiveData: MutableLiveData<String> = MutableLiveData()
	var finalPriceFieldLiveData: MutableLiveData<String> = MutableLiveData()
	var youSavedFieldLiveData: MutableLiveData<String> = MutableLiveData()

	private var fieldType = FieldType.ORIGINAL_PRICE
	private var originalFieldString = "0"
	private var discountFieldString = "0"
	private var finalPrice = "0"
	private var youSaved = "0"

	fun eachClick ( viewId: Int? ) {
		val operation = ClickType.valueType(viewId)
		if ( operation?.viewType != null ) operationFunc(operation)
		else if ( operation?.viewType == null ) nonOperationFunc(operation?.viewId)

		dataSend()
		math()
	}

	private fun math() {
		val double = "0.$discountFieldString".toDouble()
		val offPrice = (originalFieldString.toDouble() * double).toString()
		finalPrice = ( originalFieldString.toDouble() - offPrice.toDouble()).toString()
		youSaved =  ( originalFieldString.toFloat() - finalPrice.toFloat()).toString()

		dataSend()
	}

	private fun operationFunc(viewValue: ClickType?) {
		if ( fieldType == FieldType.ORIGINAL_PRICE ) {
			if (originalFieldString == "0" && viewValue?.viewType != ClickType.DOT.viewType ) originalFieldString = ""
			else if (originalFieldString.contains(ClickType.DOT.viewType.toString()) && viewValue?.viewType == ClickType.DOT.viewType ) return

			originalFieldString += viewValue?.viewType

		} else if ( fieldType == FieldType.DISCOUNT && viewValue?.viewType != ClickType.DOT.viewType) {
			if ( discountFieldString == "0" && viewValue?.viewType != ClickType.DOT.viewType) discountFieldString  = ""
			else if (discountFieldString.contains(ClickType.DOT.viewType.toString()) && viewValue?.viewType == ClickType.DOT.viewType) return
			else if ( discountFieldString.length == 2 ) return

			discountFieldString += viewValue?.viewType
		}

		dataSend()
	}

	private fun dataSend() {
		if ( originalFieldString.isNotEmpty() ) { originalFieldLiveData.value = originalFieldString }

		if ( discountFieldString.isNotEmpty() ) { discountFieldLiveData.value = discountFieldString }

		if (finalPrice.isNotEmpty()) {
			if (finalPrice.takeLast(2) == ".0") finalPrice = finalPrice.dropLast(2)
			finalPriceFieldLiveData.value = finalPrice
		}

		if (youSaved.isNotEmpty()) {
			if (youSaved.takeLast(2) == ".0") youSaved = youSaved.dropLast(2)
			youSavedFieldLiveData.value = youSaved
		}

		if ( originalFieldString.isEmpty() ) {
			originalFieldString = "0"
			originalFieldLiveData.value = originalFieldString
		}

		if ( discountFieldString.isEmpty() ) {
			discountFieldString = "0"
			discountFieldLiveData.value = discountFieldString
		}
	}

	private fun nonOperationFunc(viewId: Int?) {
		when ( viewId ) {
			ClickType.AC.viewId -> clearAll()
			ClickType.CLEAR.viewId -> removeLast()
		}
	}

	private fun removeLast() {
		if (fieldType == FieldType.ORIGINAL_PRICE ) {
			if (originalFieldString.isNotEmpty() && originalFieldString != "0") {
				originalFieldString = originalFieldString.dropLast(1)
			} else if ( originalFieldString.isEmpty() ) { originalFieldString = "0" }
		} else if ( fieldType == FieldType.DISCOUNT ) {
			if (discountFieldString.isNotEmpty()) {
				discountFieldString = discountFieldString.dropLast(1)
			} else if (discountFieldString.isEmpty() ) {
				discountFieldString = "0"
			}
		}
	}

	private fun clearAll() {
		originalFieldString = "0"
		discountFieldString = "0"
	}

	fun setFieldType ( fieldType: FieldType ) { this.fieldType = fieldType }

}