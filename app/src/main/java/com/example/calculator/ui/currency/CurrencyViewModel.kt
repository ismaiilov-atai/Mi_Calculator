package com.example.calculator.ui.currency

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.model.Currencies
import com.example.calculator.model.Rates
import com.example.calculator.network.Retrofit
import com.example.calculator.utils.Constants
import okhttp3.internal.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

	enum class ClickType (var clickId: Int, var clickType: String? = null) {
		ZERO(R.id.figures_zero_currency,"0"),
		ONE(R.id.figures_one_currency,"1"),
		TWO(R.id.figures_two_currency,"2"),
		THREE(R.id.figures_three_currency,"3"),
		FOUR(R.id.figures_four_currency,"4"),
		FIVE(R.id.figures_five_currency,"5"),
		SIX(R.id.figures_six_currency,"6"),
		SEVEN(R.id.figures_seven_currency,"7"),
		EIGHT(R.id.figures_eight_currency,"8"),
		NINE(R.id.figures_nine_currency,"9"),
		DOT(R.id.figures_dot_currency,"."),
		AC(R.id.currency_clear_btn),
		REMOVE(R.id.currency_remove_btn);

		companion object {
			fun typeOf (clickId: Int? ) = values().find { it.clickId == clickId }
		}
	}

	private var RUB: Double? = null
	private var KGS: Double? = null

	fun loadRates(apiKey: String) {
		Retrofit.instance().fetchAll(apiKey).enqueue(object : Callback<Rates> {
			override fun onResponse(call: Call<Rates>, response: Response<Rates>) {
				response.body()?.results?.USD?.let { listFetchAll["USD"] = it }
				response.body()?.results?.RUB?.let { listFetchAll["RUB"] = it }
				response.body()?.results?.KGS?.let { listFetchAll["KGS"] = it }
				response.body()?.results?.EUR?.let { listFetchAll["EUR"] = it }

				firstFieldValue = listFetchAll["USD"]
				secondFieldValue = listFetchAll["KGS"]
				thirdFieldValue = listFetchAll["EUR"]

				RUB = listFetchAll["RUB"]
				KGS = listFetchAll["KGS"]

				firstUnitDropLiveData.value = chosenCurrency("United States Dollar")
				secondUnitDropLiveData.value  = chosenCurrency( "Kyrgystani Som")
				thirdUnitDropLiveData.value  = chosenCurrency( "Euro")

			}

			override fun onFailure(call: Call<Rates>, t: Throwable) { }
		})

	}

	enum class ChosenType {
		FIRST,SECOND,THIRD
	}

	enum class DropPicked {
		FIRSTDROP,SECONDDROP,THIRTDROP
	}

	private var chosenType = ChosenType.FIRST
	private var dropPicked = DropPicked.FIRSTDROP

	private var firstField = "0"
	private var secondField = "0"
	private var thirdField = "0"

	private var firstFieldValue: Double? = null
	private var secondFieldValue: Double? = null
	private var thirdFieldValue: Double? = null

	val listFetchAll: HashMap<String, Double> = HashMap()

	var firstFieldLiveData: MutableLiveData<String> = MutableLiveData()
	var secondFieldLiveData: MutableLiveData<String> = MutableLiveData()
	var thirdFieldLiveData: MutableLiveData<String> = MutableLiveData()

	var firsUnitDescription: MutableLiveData<String> = MutableLiveData()
	var secondUnitDescription: MutableLiveData<String> = MutableLiveData()
	var thirdUnitDescription: MutableLiveData<String> = MutableLiveData()

	var firstUnitDropLiveData: MutableLiveData<String> = MutableLiveData()
	var secondUnitDropLiveData: MutableLiveData<String> = MutableLiveData()
	var thirdUnitDropLiveData: MutableLiveData<String> = MutableLiveData()

	fun onButtonClick ( viewId: Int? ) {
		val operation = ClickType.typeOf(viewId)
		if ( operation?.clickType != null ) { putClickValue(operation) }
		else { nonOperationClick(operation) }
	}

	private fun putClickValue(operation: ClickType) {
		 when(chosenType) {
		 	 ChosenType.FIRST -> {
		 	 	if (firstField == "0" && operation.clickType != ClickType.DOT.clickType) { firstField = "" }
				else if (firstField.contains(ClickType.DOT.clickType.toString()) && operation.clickType == ClickType.DOT.clickType) { return }

				 firstField += operation.clickType
				 try {
					 secondField = mathChosen(firstField, secondFieldValue!!)
					 thirdField = mathChosen(firstField, thirdFieldValue!!)
				 } catch (e: Exception) {}
		 	 }

			 ChosenType.SECOND -> {
				 if (secondField == "0" && operation.clickType != ClickType.DOT.clickType) { secondField = "" }
				 else if (secondField.contains(ClickType.DOT.clickType.toString()) && operation.clickType == ClickType.DOT.clickType) { return }

				 secondField += operation.clickType

				 try {
					 firstField = mathChosen(secondField, firstFieldValue!!)
					 thirdField = mathChosen(secondField, thirdFieldValue!!)
				 } catch (e: Exception) { }
			 }

			 ChosenType.THIRD -> {
				 if (thirdField == "0" && operation.clickType != ClickType.DOT.clickType) { thirdField = "" }
				 else if (thirdField.contains(ClickType.DOT.clickType.toString()) && operation.clickType == ClickType.DOT.clickType) { return }

				 thirdField += operation.clickType

				 try {
					 firstField = mathChosen(thirdField, firstFieldValue!!)
					 secondField = mathChosen(thirdField, secondFieldValue!!)
				 } catch (e: Exception) {}
			 }
		 }

		dataSand()
	}

	private fun mathChosen(one: String, two: Double): String {
		return ((one.toDouble() * two) / listFetchAll["USD"]!!).toString()
	}

	private fun dataSand() {
		if (firstField.isNotEmpty()) { firstFieldLiveData.value = firstField }
		else firstField = "0".also { firstFieldLiveData.value = it }

		if (secondField.isNotEmpty()) { secondFieldLiveData.value = secondField }
		else secondField = "0".also { secondFieldLiveData.value = it }

		if (thirdField.isNotEmpty()) { thirdFieldLiveData.value = thirdField }
		else thirdField = "0".also { thirdFieldLiveData.value = it }
	}

	private fun nonOperationClick(operation: ClickType?) {
		if (operation?.clickId == ClickType.AC.clickId) { clearAll() }
		else if (operation?.clickId == ClickType.REMOVE.clickId) { removeLast () }

		dataSand()
	}

	private fun removeLast() {
		when (chosenType) {
			ChosenType.FIRST -> { firstField = firstField.dropLast(1) }
			ChosenType.SECOND -> { secondField = secondField.dropLast(1) }
			ChosenType.THIRD -> { thirdField = thirdField.dropLast(1) }
		}
	}

	private fun clearAll() {
		firstField = "0"
		secondField = "0"
		thirdField = "0"
	}

	fun setFieldType(chosenType: ChosenType) { this.chosenType = chosenType }

	fun setDropPicked(dropPicked: DropPicked){ this.dropPicked = dropPicked }

	fun setDropDownValue( valueSet: String ){
		try {
			when ( dropPicked ) {
				DropPicked.FIRSTDROP -> {
					firstUnitDropLiveData.value = chosenCurrency(valueSet)

					firstFieldValue = listFetchAll[chosenCurrency(valueSet)]

				}

				DropPicked.SECONDDROP -> {
					secondUnitDropLiveData.value  = chosenCurrency(valueSet)

					secondFieldValue = listFetchAll[chosenCurrency(valueSet)]

				}

				DropPicked.THIRTDROP -> {
					thirdUnitDropLiveData.value  = chosenCurrency(valueSet)

					thirdFieldValue = listFetchAll[chosenCurrency(valueSet)]

				}
			}
		} catch (e: Exception) { }
	}

	fun setDescription ( description: String ) {
		when ( dropPicked ) {
			DropPicked.FIRSTDROP -> { firsUnitDescription.value = description }
			DropPicked.SECONDDROP -> { secondUnitDescription.value = description }
			DropPicked.THIRTDROP -> { thirdUnitDescription.value = description }
		}
	}

	private fun chosenCurrency (notes: String ): String {
		return when (notes) {
			"Russian Ruble" -> return "RUB"
			"United States Dollar" -> return "USD"
			"Kyrgystani Som" -> return "KGS"
			"Euro" -> return "EUR"
			else -> "non"
		}
	}
}