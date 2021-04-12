package com.example.calculator.ui.bmi


import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityBMIBinding
import com.example.calculator.ui.dialogs.bmi.BMIWidth
import com.example.calculator.ui.dialogs.bmi.BMIHeight

class BMIActivity : BaseActivity<ActivityBMIBinding,BMIViewModel>(ActivityBMIBinding::inflate,BMIViewModel::class.java) {

	
	override fun setupView() {
		super.setupView()
		binding.bmiArrowBack.setOnClickListener{ finish() }
		binding.firstUnitDropDownBmi.setOnClickListener{ BMIWidth(binding.bmiLayout).show(supportFragmentManager.beginTransaction(), "unitPicker") }
		binding.secondUnitDropDownBmi.setOnClickListener{ BMIHeight(binding.bmiLayout).show(supportFragmentManager.beginTransaction(), "unitPicker") }
	}
}