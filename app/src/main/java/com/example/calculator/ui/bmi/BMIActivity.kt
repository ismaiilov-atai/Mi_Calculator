package com.example.calculator.ui.bmi


import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityBMIBinding
import com.example.calculator.ui.dialogs.bmi.BMIWidth
import com.example.calculator.ui.dialogs.bmi.BMIHeight
import com.example.calculator.utils.adjustVisibility
import com.example.calculator.utils.setVisibility

class BMIActivity : BaseActivity<ActivityBMIBinding,BMIViewModel>(ActivityBMIBinding::inflate,BMIViewModel::class.java) {

	
	override fun setupView() {
		super.setupView()
		binding.bmiArrowBack.setOnClickListener{ finish() }
		binding.firstUnitDropDownBmi.setOnClickListener{ BMIWidth(binding.bmiLayout).show(supportFragmentManager.beginTransaction(), "unitPicker") }
		binding.secondUnitDropDownBmi.setOnClickListener{ BMIHeight(binding.bmiLayout).show(supportFragmentManager.beginTransaction(), "unitPicker") }
		binding.bmiGoBtn.setOnClickListener { binding.bmiGridResult.adjustVisibility(true); binding.bmiGridContainer.adjustVisibility(false)}
	}
}