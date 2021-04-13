package com.example.calculator.ui.speed

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivitySpeedBinding
import com.example.calculator.ui.dialogs.speed.SpeedUnitDialog

class SpeedActivity : BaseActivity<ActivitySpeedBinding,SpeedViewModel>(ActivitySpeedBinding::inflate,SpeedViewModel::class.java) {

	override fun setupView() {
		super.setupView()
		binding.speedArrowBack.setOnClickListener { finish() }
		binding.firstUnitHolderSpeed.setOnClickListener { SpeedUnitDialog(binding.speedLayout).show(supportFragmentManager.beginTransaction(),"speedUnit") }
		binding.secondUnitHolderSpeed.setOnClickListener { SpeedUnitDialog(binding.speedLayout).show(supportFragmentManager.beginTransaction(),"speedUnit") }

	}

}