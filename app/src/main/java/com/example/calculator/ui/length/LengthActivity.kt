package com.example.calculator.ui.length

import android.util.Log
import android.view.View
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityLengthBinding
import com.example.calculator.ui.dialogs.length.LengthUnitDialog
import com.example.calculator.utils.setPickedColor

class LengthActivity : BaseActivity<ActivityLengthBinding,LengthViewModel>(ActivityLengthBinding::inflate, LengthViewModel::class.java),
	LengthUnitDialog.LengthClickListener, View.OnClickListener {

	override fun setupView() {
		super.setupView()
		binding.valueUnitLength.setPickedColor(binding.valueUnitLength, binding.valueUnitSecondLength )
		binding.lengthArrowBack.setOnClickListener { finish() }
		binding.valueUnitLength.setOnClickListener {
			viewModel.setLengthFieldType(LengthViewModel.FieldType.FIRST_FIELD)
			it.setPickedColor(binding.valueUnitLength, binding.valueUnitSecondLength )
		}
		binding.valueUnitSecondLength.setOnClickListener {
			viewModel.setLengthFieldType(LengthViewModel.FieldType.SECOND_FIELD)
			it.setPickedColor( binding.valueUnitSecondLength, binding.valueUnitLength)
		}
		binding.firstUnitDropDownLength.setOnClickListener {
			showDialog()
			viewModel.setLengthDropDownType(LengthViewModel.LengthDropDownType.DROP_FIRST)
		}
		binding.secondUnitDropDownLength.setOnClickListener {
			showDialog()
			viewModel.setLengthDropDownType(LengthViewModel.LengthDropDownType.DROP_SECOND)
		}

		binding.figuresZeroLength.setOnClickListener(this)
		binding.figuresOneLength.setOnClickListener(this)
		binding.figuresTwoLength.setOnClickListener(this)
		binding.figuresThreeLength.setOnClickListener(this)
		binding.figuresFourLength.setOnClickListener(this)
		binding.figuresFiveLength.setOnClickListener(this)
		binding.figuresSixLength.setOnClickListener(this)
		binding.figuresSevenLength.setOnClickListener(this)
		binding.figuresEightLength.setOnClickListener(this)
		binding.figuresNineLength.setOnClickListener(this)
		binding.figuresDotLength.setOnClickListener(this)
		binding.lengthRemoveBtn.setOnClickListener(this)
		binding.lengthClearBtn.setOnClickListener(this)

		observers()
	}

	private fun observers() {
		viewModel.firstFieldValueLiveData.observe(this) { binding.valueUnitLength.text = it }
		viewModel.secondFieldValueLiveData.observe(this) { binding.valueUnitSecondLength.text = it }
		viewModel.firstDescriptionValueLiveData.observe(this) { binding.lengthTxtFirstDropDown.text = it }
		viewModel.secondDescriptionValueLiveData.observe(this) { binding.lengthTxtSecondDropDown.text = it }
		viewModel.firstDropValueLiveData.observe(this) { binding.firstUnitDropDownLength.text = it}
		viewModel.secondDropValueLiveData.observe(this) { binding.secondUnitDropDownLength.text = it}
	}

	private fun showDialog () {
		val style = supportFragmentManager.beginTransaction()
		val dialog = LengthUnitDialog(binding.lengthLayout)
		dialog.listener = this
		dialog.show(style, "unitLength")
	}

	override fun onLengthClickListener(lengthUnit: String) { viewModel.setValues(lengthUnit) }

	override fun onClick(v: View?) { viewModel.eachClick( v?.id ) }

}