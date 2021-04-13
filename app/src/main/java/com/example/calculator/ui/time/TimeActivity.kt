package com.example.calculator.ui.time

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityTimeBinding
import com.example.calculator.ui.dialogs.time.TimeUnitDialog

class TimeActivity : BaseActivity<ActivityTimeBinding,TimeViewModel>(ActivityTimeBinding::inflate,TimeViewModel::class.java) {

	override fun setupView() {
		super.setupView()
		binding.timeArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownTime.setOnClickListener {
			TimeUnitDialog(binding.timeLayout).show(supportFragmentManager.beginTransaction(),"timeUnit")
		}
		binding.secondUnitDropDownTime.setOnClickListener {
			TimeUnitDialog(binding.timeLayout).show(supportFragmentManager.beginTransaction(),"timeUnit")
		}
	}

}