package com.example.calculator.network

import com.example.calculator.model.Currencies
import com.example.calculator.model.Rates
import com.google.gson.JsonElement
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FastForexAPI {

	@GET("/currencies")
	fun fetchAllCurrencies (
		@Query ("api_key") api_key: String
	) : Call<JsonElement>
	
	@GET("/fetch-all")
	fun fetchAll (
		@Query ("api_key") api_key: String
	) : Call<Rates>

}