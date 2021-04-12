package com.example.calculator.ui.length

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityLengthBinding
import com.example.calculator.ui.dialogs.length.LengthUnitDialog

class LengthActivity : BaseActivity<ActivityLengthBinding,LengthViewModel>(ActivityLengthBinding::inflate,LengthViewModel::class.java) {

	override fun setupView() {
		super.setupView()
		binding.lengthArrowBack.setOnClickListener { finish() }

		binding.firstUnitHolderLength.setOnClickListener {
			val style = supportFragmentManager.beginTransaction()
			LengthUnitDialog(binding.discountLayout).show(style, "unitLength")
		}
		binding.secondUnitHolderLength.setOnClickListener {
			val style = supportFragmentManager.beginTransaction()
			LengthUnitDialog(binding.discountLayout).show(style, "unitLength")
		}

	}

}