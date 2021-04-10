package com.example.calculator.ui.area

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityAreaBinding
import com.example.calculator.ui.dialogs.PickUnitDialog

class AreaActivity : BaseActivity<ActivityAreaBinding, AreaViewModel>(ActivityAreaBinding::inflate, AreaViewModel::class.java) {

	override fun setupView() {
		super.setupView()
		binding.areaArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDown.setOnClickListener {
			PickUnitDialog()
				.show(supportFragmentManager, "unitPicker")
		}
	}

}