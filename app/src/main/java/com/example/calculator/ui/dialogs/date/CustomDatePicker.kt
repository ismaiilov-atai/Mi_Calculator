package com.example.calculator.ui.dialogs.date

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.calculator.R
import com.example.calculator.databinding.SpinnerDatePickerBinding
import java.util.*

class CustomDatePicker(var type: Type) : DialogFragment(), OnDateSetListener {

	interface OnDateSetTypeListener {
		fun onDateSet(type: Type, year: Int, month: Int, dayOfMonth: Int)
	}

	enum class Type {
		DateOfBirth,
		ToDay;
	}

	var binding: SpinnerDatePickerBinding? = null
	var listener: OnDateSetTypeListener? = null

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val calendar = Calendar.getInstance()
		val year = calendar.get(Calendar.YEAR)
		val month = calendar.get(Calendar.MONTH)
		val day = calendar.get(Calendar.DAY_OF_MONTH)

		return DatePickerDialog(requireContext(), this, year, month, day)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
		listener?.onDateSet(type, year, month, dayOfMonth)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		dialog?.window?.attributes?.windowAnimations = R.style.dialogAnimation
	}

}