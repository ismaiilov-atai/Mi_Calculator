package com.example.calculator.ui.dialogs.temp


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.example.calculator.databinding.TempUnitDialogBinding
import com.example.calculator.ui.dialogs.base.BaseDialog

class TemperatureUnitDialog(private val viewGroup: ViewGroup) : BaseDialog<TempUnitDialogBinding,TemperatureViewModel>(TempUnitDialogBinding::inflate,TemperatureViewModel::class.java) {

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
		binding.cancelTempDialog.setOnClickListener { dismiss() }

	}
}