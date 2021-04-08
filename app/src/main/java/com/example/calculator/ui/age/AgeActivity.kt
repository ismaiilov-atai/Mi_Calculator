package com.example.calculator.ui.age

import androidx.core.content.ContextCompat
import com.example.calculator.R
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityAgeBinding
import com.example.calculator.ui.dialogs.CastomDatePicker

class AgeActivity : BaseActivity<ActivityAgeBinding, AgeViewModel>(ActivityAgeBinding::inflate, AgeViewModel::class.java), CastomDatePicker.OnDateSetTypeListener {

	override fun setupView() {
		super.setupView()

		setupUI()
		observers()
	}

	private fun observers() {
		viewModel.birthDayLiveDate.observe(this) {
			binding.birthdayDate.text = it
		}

		viewModel.currentDayLiveDate.observe(this) {
			binding.currentDate.text = it
		}

		viewModel.birthdayCalendarLiveDate.observe(this) {

		}

		viewModel.dateCalendarLiveDate.observe(this) {
			binding.ageCalculationYearsFigures.text
		}
	}

	private fun setupUI() {
		binding.birthdayDate.setOnClickListener {
			showDatePickerDialog(CastomDatePicker.Type.DateOfBirth)
			binding.birthdayDate.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
			binding.currentDate.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
		}

		binding.currentDate.setOnClickListener {
			showDatePickerDialog(CastomDatePicker.Type.ToDay)
			binding.birthdayDate.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
			binding.currentDate.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
		}

		binding.ageArrowBack.setOnClickListener { finish() }
	}

	private fun showDatePickerDialog(type: CastomDatePicker.Type) {
		val newFragment = CastomDatePicker(type)
		newFragment.listener = this
		newFragment.show(supportFragmentManager, "datePicker")
	}

	override fun onDateSet(type: CastomDatePicker.Type, year: Int, month: Int, dayOfMonth: Int) {
		when (type) {
			CastomDatePicker.Type.DateOfBirth -> {
				viewModel.setDateOfBirth(year, month, dayOfMonth)
			}

			CastomDatePicker.Type.ToDay -> {
				viewModel.setToDay(year, month, dayOfMonth)
			}
		}
	}
}
