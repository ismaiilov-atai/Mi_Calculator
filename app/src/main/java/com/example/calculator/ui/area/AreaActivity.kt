package com.example.calculator.ui.area

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityAreaBinding
import com.example.calculator.ui.dialogs.unit.PickUnitDialog
import com.example.calculator.ui.dialogs.unit.UnitAdapter

class AreaActivity : BaseActivity<ActivityAreaBinding, AreaViewModel>(ActivityAreaBinding::inflate, AreaViewModel::class.java){

	override fun setupView() {
		super.setupView()
		binding.areaArrowBack.setOnClickListener { finish() }

		binding.firstUnitDropDown.setOnClickListener {
			val style = supportFragmentManager.beginTransaction()
			PickUnitDialog(binding.areaLayout).show(style, "unitPicker")
		}

		binding.secondUnitDropDown.setOnClickListener {
			val style = supportFragmentManager.beginTransaction()
			PickUnitDialog(binding.areaLayout).show(style, "unitPicker")
		}


	}

}