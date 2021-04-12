package com.example.calculator.ui.age

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.example.calculator.R
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityAgeBinding
import com.example.calculator.ui.dialogs.date.CustomDatePicker
import com.example.calculator.ui.share.AgeShareActivity
import java.io.ByteArrayOutputStream

class AgeActivity : BaseActivity<ActivityAgeBinding, AgeViewModel>(ActivityAgeBinding::inflate, AgeViewModel::class.java), CustomDatePicker.OnDateSetTypeListener {

	override fun setupView() {
		super.setupView()

		setupUI()
		observers()
	}

	@SuppressLint("SetTextI18n")
	private fun observers() {
		viewModel.birthDayLiveDate.observe(this) {
			binding.birthdayDate.text = it
		}

		viewModel.currentDayLiveDate.observe(this) {
			binding.currentDate.text = it
		}

		viewModel.birthdayCalendarLiveDate.observe(this) {
			binding.ageCalculationYearsFigures.text = it.years.toString()
			binding.ageMonthsTxt.text = "${it.months} month"
			binding.ageDaysLived.text = "${it.days} ${getString(R.string.days)}"
		}

		viewModel.dateCalendarLiveDate.observe(this) {
			binding.summaryYearFigure.text = it.years.toString()
			binding.summaryDaysFigure.text = it.days.toString()
			binding.summaryMonthFigure.text = it.months.toString()
			binding.summaryHoursFigure.text = it.hours.toString()
			binding.summaryWeeksFigure.text = it.weeks.toString()
			binding.summaryMinutesFigure.text = it.minutes.toString()
		}
	}

	private fun setupUI() {

		binding.birthdayDate.setOnClickListener {
			showDatePickerDialog(CustomDatePicker.Type.DateOfBirth)

			binding.birthdayDate.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
			binding.currentDate.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
		}

		binding.currentDate.setOnClickListener {
			showDatePickerDialog(CustomDatePicker.Type.ToDay)

			binding.birthdayDate.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
			binding.currentDate.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
		}

		binding.ageArrowBack.setOnClickListener { finish() }

		binding.shareBtn.setOnClickListener {
			val intent = Intent(this, AgeShareActivity::class.java)
			intent.putExtra("image", createDateBitmap())
			startActivity(intent)
		}
	}

	private fun createDateBitmap(): ByteArray {
		val bitmap = Bitmap.createBitmap(binding.dateLayout.width, binding.dateLayout.height, Bitmap.Config.RGB_565)
		val canvas = Canvas(bitmap)
		binding.dateLayout.draw(canvas)

		val stream = ByteArrayOutputStream()
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
		return stream.toByteArray()
	}

	private fun showDatePickerDialog(type: CustomDatePicker.Type) {
		val datePicker = CustomDatePicker(type)
		datePicker.listener = this
		datePicker.show(supportFragmentManager, "datePicker")
	}

	override fun onDateSet(type: CustomDatePicker.Type, year: Int, month: Int, dayOfMonth: Int) {
		when (type) {
			CustomDatePicker.Type.DateOfBirth -> {
				viewModel.setDateOfBirth(year, month, dayOfMonth)
			}

			CustomDatePicker.Type.ToDay -> {
				viewModel.setToDay(year, month, dayOfMonth)
			}

		}
	}
}
