package com.example.calculator.ui.discount

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityDiscountBinding

class DiscountActivity : BaseActivity<ActivityDiscountBinding,DiscountViewModel>(ActivityDiscountBinding::inflate,DiscountViewModel::class.java){

	override fun setupView() {
		super.setupView()
		binding.discountArrowBack.setOnClickListener { finish() }

	}

}