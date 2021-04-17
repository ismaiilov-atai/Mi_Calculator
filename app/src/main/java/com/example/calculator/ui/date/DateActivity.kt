package com.example.calculator.ui.date

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.example.calculator.R
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityDateBinding
import com.example.calculator.ui.dialogs.date.CustomDatePicker
import com.example.calculator.ui.share.AgeShareActivity
import java.io.ByteArrayOutputStream

class DateActivity : BaseActivity<ActivityDateBinding, DateViewModel>(ActivityDateBinding::inflate, DateViewModel::class.java), CustomDatePicker.OnDateSetTypeListener {

	override fun setupView() {
		super.setupView()

		binding.dateArrowBack.setOnClickListener { finish() }

		binding.birthdayDate.setOnClickListener {
			showDatePickerDialog(CustomDatePicker.Type.DateOfBirth)
		}

		binding.currentDate.setOnClickListener {
			showDatePickerDialog(CustomDatePicker.Type.ToDay)
		}

		binding.shareBtn.setOnClickListener {
			val intent = Intent(this, AgeShareActivity::class.java)
			intent.putExtra("bitmap", createDateBitmap())
			startActivity(intent)
		}
		observe()
	}

	private fun observe() {
		viewModel.fromLiveDate.observe(this) {
			binding.birthdayDate.text = it
			binding.dateFromIndicator.text = it
			binding.birthdayDate.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
			binding.currentDate.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
		}

		viewModel.currentLiveDate.observe(this) {
			binding.currentDate.text = it
			binding.dateToIndicator.text = it
			binding.birthdayDate.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
			binding.currentDate.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
		}

		viewModel.birthdayCalendarLiveDate.observe(this) {
			binding.dateYearIndicator.text = it.years.toString()
			binding.dateMonthIndicator.text = it.months.toString()
			binding.dateDaysIndicator.text = it.days.toString()
		}
	}

	private fun showDatePickerDialog(type: CustomDatePicker.Type) {
		val datePicker = CustomDatePicker(type)
		datePicker.listener = this
		datePicker.show(supportFragmentManager, "datePicker")
	}

	override fun onDateSet(type: CustomDatePicker.Type, year: Int, month: Int, dayOfMonth: Int) {
		when (type) {
			CustomDatePicker.Type.DateOfBirth -> {
				viewModel.setFromLiveDate(year, month, dayOfMonth)
			}

			CustomDatePicker.Type.ToDay -> {
				viewModel.setCurrentLiveDate(year, month, dayOfMonth)
			}
		}
	}

	private fun createDateBitmap(): ByteArray {
		val bitmap = Bitmap.createBitmap(
			binding.differenceLayout.width, binding.differenceLayout.height, Bitmap.Config.RGB_565
		)
		val canvas = Canvas(bitmap)
		binding.differenceLayout.draw(canvas)

		val stream = ByteArrayOutputStream()
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
		return stream.toByteArray()
	}

}