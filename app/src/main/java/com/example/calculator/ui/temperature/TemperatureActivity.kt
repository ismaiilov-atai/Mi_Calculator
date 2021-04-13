package com.example.calculator.ui.temperature

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityTemperatureBinding
import com.example.calculator.ui.dialogs.temp.TemperatureUnitDialog

class TemperatureActivity : BaseActivity<ActivityTemperatureBinding, TemperatureViewModel>(ActivityTemperatureBinding::inflate, TemperatureViewModel::class.java) {

	override fun setupView() {
		super.setupView()
		binding.tempArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownTemp.setOnClickListener { TemperatureUnitDialog(binding.tempLayout).show(supportFragmentManager.beginTransaction(),"tempUnit") }
		binding.secondUnitDropDownTemp.setOnClickListener { TemperatureUnitDialog(binding.tempLayout).show(supportFragmentManager.beginTransaction(),"tempUnit") }
	}

}