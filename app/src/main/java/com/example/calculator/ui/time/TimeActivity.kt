package com.example.calculator.ui.time

import android.util.Log
import android.view.View
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityTimeBinding
import com.example.calculator.ui.dialogs.time.TimeUnitDialog
import com.example.calculator.utils.setPickedColor

class TimeActivity : BaseActivity<ActivityTimeBinding,TimeViewModel>(ActivityTimeBinding::inflate,TimeViewModel::class.java), TimeUnitDialog.OnTimeClickListener, View.OnClickListener {

	override fun setupView() {
		super.setupView()
		binding.valueUnitTime.setPickedColor(binding.valueUnitTime, binding.valueUnitSecondTime)
		binding.timeArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownTime.setOnClickListener { showDialog(); viewModel.setTimeDropDown(TimeViewModel.TimeDropDownType.DROP_FIRST) }
		binding.secondUnitDropDownTime.setOnClickListener { showDialog(); viewModel.setTimeDropDown(TimeViewModel.TimeDropDownType.DROP_SECOND) }
		binding.valueUnitTime.setOnClickListener {
			it.setPickedColor(binding.valueUnitTime, binding.valueUnitSecondTime)
			viewModel.chosenField(TimeViewModel.FieldType.FIRST_FIELD)

		}
		binding.valueUnitSecondTime.setOnClickListener {
			it.setPickedColor(binding.valueUnitSecondTime, binding.valueUnitTime)
			viewModel.chosenField(TimeViewModel.FieldType.SECOND_FIELD)
		}
		binding.figuresZeroTime.setOnClickListener(this)
		binding.figuresOneTime.setOnClickListener(this)
		binding.figuresTwoTime.setOnClickListener(this)
		binding.figuresThreeTime.setOnClickListener(this)
		binding.figuresFourTime.setOnClickListener(this)
		binding.figuresFiveTime.setOnClickListener(this)
		binding.figuresSixTime.setOnClickListener(this)
		binding.figuresSevenTime.setOnClickListener(this)
		binding.figuresEightTime.setOnClickListener(this)
		binding.figuresNineTime.setOnClickListener(this)
		binding.figuresDotTime.setOnClickListener(this)
		binding.timeRemoveBtn.setOnClickListener(this)
		binding.timeClearBtn.setOnClickListener(this)

		observers()
	}

	private fun observers() {
		viewModel.firstFieldValueLiveData.observe(this) { binding.valueUnitTime.text = it }
		viewModel.secondFieldValueLiveData.observe(this) { binding.valueUnitSecondTime.text = it }

		viewModel.firstDropValueLiveData.observe(this) { binding.firstUnitDropDownTime.text = it }
		viewModel.secondDropValueLiveData.observe(this) { binding.secondUnitDropDownTime.text = it }

		viewModel.firstDescriptionValueLiveData.observe(this) { binding.timeTxtFirstDropDown.text = it }
		viewModel.secondDescriptionValueLiveData.observe(this) { binding.timeTxtSecondDropDown.text = it }
	}

	override fun timeUnitListener(timeUnit: String) { viewModel.setValues( timeUnit ) }

	private fun showDialog () {
		val dialog = TimeUnitDialog(binding.timeLayout)
			dialog.listener = this
			dialog.show(supportFragmentManager.beginTransaction(),"timeUnit")
	}

	override fun onClick(v: View?) { viewModel.eachClick(v?.id) }

}