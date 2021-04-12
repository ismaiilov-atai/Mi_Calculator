package com.example.calculator.ui.dataconvert

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityDataConverterBinding
import com.example.calculator.ui.dialogs.data.DataUnitDialog

class DataConverterActivity : BaseActivity<ActivityDataConverterBinding, DataConverterViewModel>(ActivityDataConverterBinding::inflate, DataConverterViewModel::class.java) {

	override fun setupView() {
		super.setupView()
		binding.dataArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownData.setOnClickListener { DataUnitDialog(binding.dataLayout).show(supportFragmentManager.beginTransaction(), "dataUnit") }
		binding.secondUnitDropDownData.setOnClickListener { DataUnitDialog(binding.dataLayout).show(supportFragmentManager.beginTransaction(), "secondUnit") }
	}
}