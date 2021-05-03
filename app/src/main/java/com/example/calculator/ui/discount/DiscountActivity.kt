package com.example.calculator.ui.discount

import android.annotation.SuppressLint
import android.view.View
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityDiscountBinding
import com.example.calculator.utils.setPickedColor

class DiscountActivity : BaseActivity<ActivityDiscountBinding,DiscountViewModel>(ActivityDiscountBinding::inflate,DiscountViewModel::class.java),
	View.OnClickListener {

	override fun setupView() {
		super.setupView()
		binding.valueUnitDiscount.setPickedColor( binding.valueUnitDiscount,binding.valueUnitSecondDiscount )
		binding.discountArrowBack.setOnClickListener { finish() }

		binding.valueUnitDiscount.setOnClickListener {
			viewModel.setFieldType(DiscountViewModel.FieldType.ORIGINAL_PRICE)
			it.setPickedColor( binding.valueUnitDiscount,binding.valueUnitSecondDiscount )
		}
		binding.valueUnitSecondDiscount.setOnClickListener {
			viewModel.setFieldType(DiscountViewModel.FieldType.DISCOUNT)
			it.setPickedColor( binding.valueUnitSecondDiscount, binding.valueUnitDiscount )
		}

		binding.figuresZeroDiscount.setOnClickListener(this)
		binding.figuresOneDiscount.setOnClickListener(this)
		binding.figuresTwoDiscount.setOnClickListener(this)
		binding.figuresThreeDiscount.setOnClickListener(this)
		binding.figuresFourDiscount.setOnClickListener(this)
		binding.figuresFiveDiscount.setOnClickListener(this)
		binding.figuresSixDiscount.setOnClickListener(this)
		binding.figuresSevenDiscount.setOnClickListener(this)
		binding.figuresEightDiscount.setOnClickListener(this)
		binding.figuresNineDiscount.setOnClickListener(this)
		binding.figuresDotDiscount.setOnClickListener(this)
		binding.discountClearBtn.setOnClickListener(this)
		binding.discountRemoveBtn.setOnClickListener(this)

		observers()

	}

	@SuppressLint("SetTextI18n")
	private fun observers() {
		viewModel.originalFieldLiveData.observe(this ) { binding.valueUnitDiscount.text = it }
		viewModel.discountFieldLiveData.observe(this ) { binding.valueUnitSecondDiscount.text = it }
		viewModel.finalPriceFieldLiveData.observe(this ) { binding.valueUnitThirdDiscount.text = it }
		viewModel.youSavedFieldLiveData.observe(this ) { binding.discountShowResult.text = "You saved $it" }
	}

	override fun onClick(v: View?) {
		viewModel.eachClick(v?.id)
	}

}