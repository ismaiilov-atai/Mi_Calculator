package com.example.calculator.ui.bmi


import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.calculator.R
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityBMIBinding
import com.example.calculator.ui.dialogs.bmi.BMIGoDialogs
import com.example.calculator.ui.dialogs.bmi.BMIHeight
import com.example.calculator.ui.dialogs.bmi.BMIWeight
import com.example.calculator.utils.Constants

class BMIActivity : BaseActivity<ActivityBMIBinding,BMIViewModel>(ActivityBMIBinding::inflate,BMIViewModel::class.java),View.OnClickListener, BMIHeight.OnUnitClickListener, BMIWeight.OnUnitClickListener {

	
	override fun setupView() {
		super.setupView()
		viewModel.setField(BMIViewModel.ChosenType.FIRST)
		binding.valueUnitSecondBmi.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
		binding.valueUnitBmi.setTextColor(ContextCompat.getColor(this, R.color.purple_500))

		binding.bmiArrowBack.setOnClickListener{ finish() }
		binding.firstUnitDropDownBmi.setOnClickListener {
			val dialog =  BMIWeight(binding.bmiLayout)
			dialog.listener = this
			dialog.show(supportFragmentManager.beginTransaction(),"unitWidthPick")
		}
		binding.secondUnitDropDownBmi.setOnClickListener {
			val dialog = BMIHeight(binding.bmiLayout)
			dialog.listener = this
			dialog.show(supportFragmentManager.beginTransaction(), "unitPicker")
		}

		binding.bmiGoBtn.setOnClickListener {
			val dialogs = BMIGoDialogs(binding.bmiLayout)
			val bundle = Bundle()
			bundle.putString(Constants.KEY_BMI_RESULT, viewModel.math())
			dialogs.arguments = bundle
			dialogs.show(supportFragmentManager.beginTransaction(),"goDialog")
		}

		binding.figuresZeroBmi.setOnClickListener(this)
		binding.figuresOneBmi.setOnClickListener(this)

		binding.figuresTwoBmi.setOnClickListener(this)
		binding.figuresThreeBmi.setOnClickListener(this)
		binding.figuresFourBmi.setOnClickListener(this)
		binding.figuresFiveBmi.setOnClickListener(this)
		binding.figuresSixBmi.setOnClickListener(this)
		binding.figuresSevenNormalBmi.setOnClickListener(this)
		binding.figuresEightBmi.setOnClickListener(this)
		binding.figuresNineBmi.setOnClickListener(this)
		binding.figuresDotBmi.setOnClickListener(this)
		binding.bmiClearBtn.setOnClickListener(this)
		binding.bmiRemoveBtn.setOnClickListener(this)

		binding.valueUnitBmi.setOnClickListener {
			viewModel.setField(BMIViewModel.ChosenType.FIRST)
			binding.valueUnitSecondBmi.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
			binding.valueUnitBmi.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
		}
		binding.valueUnitSecondBmi.setOnClickListener {
			viewModel.setField(BMIViewModel.ChosenType.SECOND)
			binding.valueUnitSecondBmi.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
			binding.valueUnitBmi.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
		}

		observers()
	}

	private fun observers() {
		viewModel.firstFieldLiveData.observe(this) { binding.valueUnitBmi.text = it }
		viewModel.secondFieldLiveData.observe(this) { binding.valueUnitSecondBmi.text = it}

		viewModel.unitWeightLiveData.observe(this) { binding.descriptionUnitBmi.text = it}
		viewModel.unitHeightLiveData.observe(this) { binding.descriptionUnitSecondBmi.text = it}
	}

	override fun onClick(v: View?) { viewModel.clickButton(v?.id) }

	override fun unitHeightClickListener(typeHeight: String) { viewModel.setHeight(typeHeight) }
	override fun unitWeightClickListener(typeWeigh: String) { viewModel.setWeight(typeWeigh) }

}