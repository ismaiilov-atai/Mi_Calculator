package com.example.calculator.ui.mass

import android.util.Log
import android.view.View
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityMassBinding
import com.example.calculator.ui.dialogs.mass.MassUnitDialog
import com.example.calculator.utils.setPickedColor

class MassActivity : BaseActivity<ActivityMassBinding,MassViewModel>(ActivityMassBinding::inflate,MassViewModel::class.java),MassUnitDialog.MassUnitClickListener, View.OnClickListener {

	override fun setupView() {
		super.setupView()
		binding.valueUnitMass.setPickedColor(binding.valueUnitMass, binding.valueUnitSecondMass)
		binding.massArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownMass.setOnClickListener { showDialog(); viewModel.setMassDropDownType(MassViewModel.MessDropDownType.FIRST_DROP) }
		binding.secondUnitDropDownMass.setOnClickListener { showDialog(); viewModel.setMassDropDownType(MassViewModel.MessDropDownType.SECOND_DROP) }

		binding.valueUnitMass.setOnClickListener { viewModel.setMassFieldType(MassViewModel.FieldType.FIRST_FIELD); it.setPickedColor(binding.valueUnitMass, binding.valueUnitSecondMass) }
		binding.valueUnitSecondMass.setOnClickListener { viewModel.setMassFieldType(MassViewModel.FieldType.SECOND_FIELD); it.setPickedColor( binding.valueUnitSecondMass, binding.valueUnitMass) }

		binding.figuresZeroMass.setOnClickListener(this)
		binding.figuresOneMass.setOnClickListener(this)
		binding.figuresTwoMass.setOnClickListener(this)
		binding.figuresThreeMass.setOnClickListener(this)
		binding.figuresFourMass.setOnClickListener(this)
		binding.figuresFiveMass.setOnClickListener(this)
		binding.figuresSixMass.setOnClickListener(this)
		binding.figuresSevenMass.setOnClickListener(this)
		binding.figuresEightMass.setOnClickListener(this)
		binding.figuresNineMass.setOnClickListener(this)
		binding.figuresDotMass.setOnClickListener(this)
		binding.massRemoveBtn.setOnClickListener(this)
		binding.massClearBtn.setOnClickListener(this)

		observers()
	}

	private fun observers() {
		viewModel.firstFieldValueLiveData.observe(this) { binding.valueUnitMass.text = it }
		viewModel.secondFieldValueLiveData.observe(this) { binding.valueUnitSecondMass.text = it }

		viewModel.firstMassDropValueLiveData.observe(this) { binding.firstUnitDropDownMass.text = it}
		viewModel.secondMassDropValueLiveData.observe(this) { binding.secondUnitDropDownMass.text = it}

		viewModel.firstMassDescriptionValueLiveData.observe(this) { binding.massTxtFirstDropDown.text = it }
		viewModel.secondMassDescriptionValueLiveData.observe(this) { binding.massTxtSecondDropDown.text = it }
	}

	private fun showDialog() {
		val dialog = MassUnitDialog(binding.massLayout)
			dialog.listener = this
			dialog.show(supportFragmentManager.beginTransaction(),"massUnit")
	}

	override fun unitPickClickListener(massUnit: String) { viewModel.setValues(massUnit) }

	override fun onClick(v: View?) { viewModel.eachClick(v?.id) }

}