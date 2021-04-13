package com.example.calculator.ui.currency

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityCurrencyBinding
import com.example.calculator.ui.dialogs.currency.CurrencyUnitDialog

class CurrencyActivity : BaseActivity<ActivityCurrencyBinding,CurrencyViewBinding>(ActivityCurrencyBinding::inflate,CurrencyViewBinding::class.java) {

	override fun setupView() {
		super.setupView()
		binding.currencyArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownCurrency.setOnClickListener {
			CurrencyUnitDialog(binding.currencyLayout).show(supportFragmentManager.beginTransaction(),"currency1")
		}
		binding.secondUnitDropDownCurrency.setOnClickListener {
			CurrencyUnitDialog(binding.currencyLayout).show(supportFragmentManager.beginTransaction(),"currency2")
		}
		binding.thirdUnitDropDownCurrency.setOnClickListener {
			CurrencyUnitDialog(binding.currencyLayout).show(supportFragmentManager.beginTransaction(),"currency3")
		}

	}
}