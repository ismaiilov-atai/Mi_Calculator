package com.example.calculator.ui.mass

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityMassBinding
import com.example.calculator.ui.dialogs.mass.MassUnitDialog

class MassActivity : BaseActivity<ActivityMassBinding,MassViewModel>(ActivityMassBinding::inflate,MassViewModel::class.java) {

	override fun setupView() {
		super.setupView()
		binding.massArrowBack.setOnClickListener { finish() }
		binding.firstUnitHolderMass.setOnClickListener { MassUnitDialog(binding.massLayout).show(supportFragmentManager.beginTransaction(),"massUnit") }
		binding.secondUnitHolderMass.setOnClickListener { MassUnitDialog(binding.massLayout).show(supportFragmentManager.beginTransaction(),"massUnitSecond") }
	}

}