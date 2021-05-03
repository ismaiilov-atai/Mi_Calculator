package com.example.calculator.ui.investment


import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityInvestmentBinding
import com.example.calculator.ui.dialogs.loan.LoanDialog

class InvestmentActivity() : BaseActivity<ActivityInvestmentBinding,InvestmentViewModel>(ActivityInvestmentBinding::inflate, InvestmentViewModel::class.java) {

	override fun setupView() {
		super.setupView()
		binding.investArrowBack.setOnClickListener { finish() }
		binding.durationHolder.setOnClickListener { LoanDialog(binding.investmentLayout).show ( supportFragmentManager.beginTransaction(),"calculate") }

	}

}
