package com.example.calculator.ui.dialogs.bmi

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.example.calculator.databinding.BmiHeightLayoutBinding
import com.example.calculator.ui.dialogs.base.BaseDialog

class BMIHeight(private var viewGroup: ViewGroup): BaseDialog<BmiHeightLayoutBinding, BMIViewModel>(BmiHeightLayoutBinding::inflate,BMIViewModel::class.java) {

	var listener: OnUnitClickListener? = null

	override fun setupUI() {
		super.setupUI()
		binding.cancelHeightDialog.setOnClickListener{ dismiss() }
		binding.centimeters.setOnClickListener { listener?.unitHeightClickListener(getTextFromView(it)); dismiss() }
		binding.meters.setOnClickListener { listener?.unitHeightClickListener(getTextFromView(it)); dismiss() }
		binding.feet.setOnClickListener { listener?.unitHeightClickListener(getTextFromView(it)); dismiss() }
		binding.inches.setOnClickListener { listener?.unitHeightClickListener(getTextFromView(it)); dismiss() }
	}

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.40).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	private fun getTextFromView(textView: View): String {
		textView as TextView
		dismiss()
		return textView.text.toString()

	}

	interface OnUnitClickListener {
		fun unitHeightClickListener(typeHeight: String)
	}
}