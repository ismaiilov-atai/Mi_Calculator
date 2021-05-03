package com.example.calculator.ui.dialogs.loan

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.NumberPicker
import com.example.calculator.R
import com.example.calculator.databinding.CustomNumberPickerBinding
import com.example.calculator.ui.dialogs.base.BaseDialog

class LoanDialog(private val viewGroup: ViewGroup): BaseDialog<CustomNumberPickerBinding, LoanDialogViewModel>(CustomNumberPickerBinding::inflate, LoanDialogViewModel::class.java) {

	var listener: OnValueDataCarryListener? = null
	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.45).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params

		pickerMission()
		btnOperations()

	}

	private fun btnOperations() {
		binding.numberPickOk.setOnClickListener {
			listener?.valueCarryListener(binding.yearPicker.value.toString(),binding.monthsPicker.value.toString())
			dismiss()
		}
		binding.numberPickCancel.setOnClickListener {
			listener?.valueCarryListener("0","0")
			dismiss()
		}
	}

	private fun pickerMission() {
		binding.yearPicker.apply { numberPickerValue(this, resources.getStringArray(R.array.yearsList)) }
		binding.monthsPicker.apply { numberPickerValue(this, resources.getStringArray(R.array.monthsList)) }
	}

	private fun numberPickerValue(numberPick: NumberPicker, stringArray: Array<String>) {
		numberPick.minValue = 0
		numberPick.maxValue = (stringArray.size -1)
		numberPick.wrapSelectorWheel = false
		numberPick.displayedValues = stringArray
	}

	interface OnValueDataCarryListener {
		fun valueCarryListener( year: String, month: String )
	}
}