package com.example.calculator.model

import com.google.gson.annotations.SerializedName

data class Currencies (
	@SerializedName("currencies") var currencies : Notes,
	@SerializedName("ms") var ms : Int)