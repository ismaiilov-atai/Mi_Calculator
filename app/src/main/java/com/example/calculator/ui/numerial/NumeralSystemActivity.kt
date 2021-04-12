package com.example.calculator.ui.numerial

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityNumeralSystemBinding
import com.example.calculator.ui.dialogs.numeral.NumeralDialog

class NumeralSystemActivity : BaseActivity<ActivityNumeralSystemBinding, NumeralViewModel>(ActivityNumeralSystemBinding::inflate, NumeralViewModel::class.java) {

	override fun setupView() {
		super.setupView()
		binding.numeralArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownNumeral.setOnClickListener { NumeralDialog(binding.numeralLayout).show(supportFragmentManager.beginTransaction(), "numeral") }
		binding.secondUnitDropDownNumeral.setOnClickListener { NumeralDialog(binding.numeralLayout).show(supportFragmentManager.beginTransaction(), "numeralSecond") }
	}
}