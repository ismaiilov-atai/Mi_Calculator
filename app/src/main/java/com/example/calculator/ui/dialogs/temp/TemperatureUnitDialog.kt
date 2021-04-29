package com.example.calculator.ui.dialogs.temp


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.example.calculator.databinding.TempUnitDialogBinding
import com.example.calculator.ui.dialogs.base.BaseDialog

class TemperatureUnitDialog(private val viewGroup: ViewGroup) : BaseDialog<TempUnitDialogBinding,TemperatureDialogViewModel>(TempUnitDialogBinding::inflate,TemperatureDialogViewModel::class.java) {

	var listener: TemperatureClickListener? = null

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.47).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	override fun setupUI() {
		super.setupUI()
		binding.celsius.setOnClickListener { listener?.temperatureUnitClick ( getTextView(it) ) }
		binding.fahrenheit.setOnClickListener { listener?.temperatureUnitClick ( getTextView(it) ) }
		binding.kelvin.setOnClickListener { listener?.temperatureUnitClick ( getTextView(it) ) }
		binding.rankine.setOnClickListener { listener?.temperatureUnitClick ( getTextView(it) ) }
		binding.reaumur.setOnClickListener { listener?.temperatureUnitClick ( getTextView(it) ) }

		binding.cancelTempDialog.setOnClickListener { dismiss() }
	}

	private fun getTextView (viewText: View ): String {
		viewText as TextView
		dismiss()
		return viewText.text.toString()
	}

	interface TemperatureClickListener { fun temperatureUnitClick ( typeUnit: String ) }
}