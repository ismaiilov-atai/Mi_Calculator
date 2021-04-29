package com.example.calculator.ui.temperature

import android.util.Log
import android.view.View
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityTemperatureBinding
import com.example.calculator.ui.dialogs.temp.TemperatureUnitDialog
import com.example.calculator.utils.setPickedColor

class TemperatureActivity : BaseActivity<ActivityTemperatureBinding, TemperatureViewModel>(ActivityTemperatureBinding::inflate, TemperatureViewModel::class.java)
,TemperatureUnitDialog.TemperatureClickListener, View.OnClickListener {

	override fun setupView() {
		super.setupView()
		binding.valueUnitTemp.setPickedColor( binding.valueUnitTemp,binding.valueUnitSecondTemp )
		binding.tempArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownTemp.setOnClickListener {
			showDialog ()
			viewModel.chosenDropType(TemperatureViewModel.DropDownType.FIRST_DROP)
		}

		binding.secondUnitDropDownTemp.setOnClickListener {
			showDialog ()
			viewModel.chosenDropType(TemperatureViewModel.DropDownType.SECOND_DROP)
		}

		binding.valueUnitTemp.setOnClickListener {
			viewModel.chosenField(TemperatureViewModel.FieldType.FIRST_FIELD)
			it.setPickedColor( binding.valueUnitTemp,binding.valueUnitSecondTemp )
		}

		binding.valueUnitSecondTemp.setOnClickListener {
			viewModel.chosenField(TemperatureViewModel.FieldType.SECOND_FIELD)
			it.setPickedColor(binding.valueUnitSecondTemp, binding.valueUnitTemp )
		}

		binding.figuresZeroTemp.setOnClickListener(this)
		binding.figuresOneTemp.setOnClickListener(this)
		binding.figuresTwoTemp.setOnClickListener(this)
		binding.figuresThreeTemp.setOnClickListener(this)
		binding.figuresFourTemp.setOnClickListener(this)
		binding.figuresFiveTemp.setOnClickListener(this)
		binding.figuresSixTemp.setOnClickListener(this)
		binding.figuresSevenTemp.setOnClickListener(this)
		binding.figuresEightTemp.setOnClickListener(this)
		binding.figuresNineTemp.setOnClickListener(this)
		binding.figuresDotTemp.setOnClickListener(this)
		binding.figuresAcTemp.setOnClickListener(this)
		binding.figuresDeleteTemp.setOnClickListener(this)
		binding.figuresPlusMinusTemp.setOnClickListener(this)

		observe()

	}

	private fun observe() {
		viewModel.firstFieldValueLiveData.observe(this ) { binding.valueUnitTemp.text = it }
		viewModel.secondFieldValueLiveData.observe(this ) { binding.valueUnitSecondTemp.text = it }
		viewModel.firstDropDownLiveData.observe(this ) { binding.firstUnitDropDownTemp .text = it }
		viewModel.secondDropDownLiveData.observe(this ) { binding.secondUnitDropDownTemp.text = it }

		viewModel.firstDescriptionLiveData.observe(this) { binding.tempTxtFirstDropDown.text = it }
		viewModel.secondDescriptionLiveData.observe(this) { binding.tempTxtSecondDropDown.text = it }
	}

	override fun temperatureUnitClick(typeUnit: String) { viewModel.setDropDownValue( typeUnit ) }

	private fun showDialog () {
		val dialog = TemperatureUnitDialog(binding.tempLayout)
		dialog.listener = this
		dialog.show(supportFragmentManager.beginTransaction(),"tempUnit")
	}

	override fun onClick(v: View?) {
		viewModel.eachClick(v?.id)
	}

}