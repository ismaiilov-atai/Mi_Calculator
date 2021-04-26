package com.example.calculator.ui.dialogs.currency

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.model.Currencies
import com.example.calculator.model.Rates
import com.example.calculator.network.Retrofit
import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyDialogViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

	val currenciesLiveData: MutableLiveData<ArrayList<String>> = MutableLiveData()

	val listCurrencies: ArrayList<String> = ArrayList()
	val listFetchAll: HashMap<String, Double> = HashMap()



	fun loadCurrencies(apiKey: String) {

//		Retrofit.instance().fetchAll(apiKey).enqueue(object : Callback<Rates> {
//			override fun onResponse(call: Call<Rates>, response: Response<Rates>) {
//
//				response.body()?.results?.USD?.let { listFetchAll["AFN"] = it }
//				response.body()?.results?.RUB?.let { listFetchAll["ALL"] = it }
//				response.body()?.results?.AED?.let { listFetchAll["AED"] = it }
//				response.body()?.results?.KGS?.let { listFetchAll["AMD"] = it }
//				response.body()?.results?.EUR?.let { listFetchAll["ANG"] = it }
//			}
//
//			override fun onFailure(call: Call<Rates>, t: Throwable) {
//				Log.e("TAG1234", "onFailure: ${t.localizedMessage}")
//			}
//		})

		event?.showProgress()
		Retrofit.instance().fetchAllCurrencies(apiKey).enqueue(object : Callback<JsonElement> {
			override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
				response.body()?.let {
					event?.hideProgress()
					val gson = Gson()
					val myCurrencies = gson.fromJson(it, Currencies::class.java)

					myCurrencies?.currencies?.USD?.let { value -> listCurrencies.add(value) }
					myCurrencies?.currencies?.RUB?.let { value -> listCurrencies.add(value) }
					myCurrencies?.currencies?.KGS?.let { value -> listCurrencies.add(value) }
					myCurrencies?.currencies?.EUR?.let { value -> listCurrencies.add(value) }

					currenciesLiveData.value = listCurrencies
					Log.e("test12331", "onResponse: ${listCurrencies.size}" )
				}
			}

			override fun onFailure(call: Call<JsonElement>, t: Throwable) {
				Log.e("test124", "onFailure: ${t.localizedMessage}")
				event?.hideProgress()
			}
		})
	}




}