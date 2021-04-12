package com.example.calculator.ui.area

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityAreaBinding
import com.example.calculator.ui.dialogs.area.AreaUnitDialog

class AreaActivity : BaseActivity<ActivityAreaBinding, AreaViewModel>(ActivityAreaBinding::inflate, AreaViewModel::class.java){



	override fun setupView() {
		super.setupView()
		binding.areaArrowBackArea.setOnClickListener { finish() }
		binding.firstUnitDropDownArea.setOnClickListener {
			AreaUnitDialog(binding.areaLayout).show(supportFragmentManager.beginTransaction(), "unitPicker")
		}
		binding.secondUnitDropDownArea.setOnClickListener {
			AreaUnitDialog(binding.areaLayout).show(supportFragmentManager.beginTransaction(), "unitPicker")
		}

	}
}