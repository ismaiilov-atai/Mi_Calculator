package com.example.calculator.ui.dialogs

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.calculator.databinding.SpinnerDatePickerBinding
import java.util.*

class CastomDatePicker(var type: Type) : DialogFragment(), OnDateSetListener {

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

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = SpinnerDatePickerBinding.inflate(inflater,container,false)
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
		listener?.onDateSet(type, year, month, dayOfMonth)
	}
}