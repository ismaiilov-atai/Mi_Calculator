package com.example.calculator.network

import com.example.calculator.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Retrofit {

	companion object {

		fun instance(): FastForexAPI {
			val okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
				.writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build()


			val retrofit = Retrofit.Builder()
				.addConverterFactory(GsonConverterFactory.create())
				.baseUrl(Constants.BASE_URL)
				.client(okHttpClient)
				.build()

			return retrofit.create(FastForexAPI::class.java)
		}

	}
}