package com.example.calculator.ui.dialogs.investment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.NumberPicker
import com.example.calculator.R
import com.example.calculator.databinding.InvestCustomLayoutBinding
import com.example.calculator.ui.dialogs.base.BaseDialog
import com.example.calculator.ui.investment.InvestmentViewModel

class InvestmentDialog(private var viewGroup: ViewGroup) : BaseDialog <InvestCustomLayoutBinding,InvestmentViewModel>(InvestCustomLayoutBinding::inflate,InvestmentViewModel::class.java ) {

	var listener: OnValueDataCarryInvestListener? = null
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
		binding.numberPickOkInvest.setOnClickListener {
			listener?.valueCarryListener(binding.yearPicker.value.toString(),binding.monthsPickerInvest.value.toString())
			dismiss()
		}
		binding.numberPickCancelInvest.setOnClickListener {
			listener?.valueCarryListener("0","0")
			dismiss()
		}
	}

	private fun pickerMission() {
		binding.yearPicker.apply { numberPickerValue(this, resources.getStringArray(R.array.yearsList)) }
		binding.monthsPickerInvest.apply { numberPickerValue(this, resources.getStringArray(R.array.monthsList)) }
	}

	private fun numberPickerValue(numberPick: NumberPicker, stringArray: Array<String>) {
		numberPick.minValue = 0
		numberPick.maxValue = (stringArray.size -1)
		numberPick.wrapSelectorWheel = false
		numberPick.displayedValues = stringArray
	}

	interface OnValueDataCarryInvestListener {
		fun valueCarryListener( year: String, month: String )
	}

}