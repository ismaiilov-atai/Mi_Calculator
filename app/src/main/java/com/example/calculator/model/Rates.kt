package com.example.calculator.model

import com.google.gson.annotations.SerializedName

class Rates {

	@SerializedName("base") var base: String? = null
	@SerializedName("updated") var updated: String? = null
	@SerializedName("results") var results: RatesItem? = null
	@SerializedName("ms") var ms: Int? = null

}