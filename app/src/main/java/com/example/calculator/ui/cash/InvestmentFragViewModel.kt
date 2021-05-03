package com.example.calculator.ui.cash

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.example.calculator.MainActivity
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.ui.currency.CurrencyActivity
import com.example.calculator.ui.extra.ExtraModel
import com.example.calculator.ui.investment.InvestmentActivity
import com.example.calculator.ui.loan.LoanActivity

class InvestmentFragViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {
	var listMutableLiveData = MutableLiveData<ArrayList<ExtraModel>>()

	fun loadData(context: Context) {
		listMutableLiveData.value = ArrayList(
			listOf (
				ExtraModel(R.drawable.ic_dollar_sing, R.string.currency, Intent(context, CurrencyActivity::class.java)),
				ExtraModel(R.drawable.ic_personal, R.string.loan, Intent(context,  LoanActivity::class.java)),
				ExtraModel(R.drawable.ic_profits, R.string.investment, Intent(context,         InvestmentActivity::class.java))
			)
		)
	}
}