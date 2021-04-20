package com.example.calculator.ui.dialogs.bmi

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.example.calculator.databinding.BmiWidthLayoutBinding
import com.example.calculator.ui.dialogs.base.BaseDialog

class BMIWeight(private var viewGroup: ViewGroup): BaseDialog<BmiWidthLayoutBinding,BMIViewModel>(BmiWidthLayoutBinding::inflate,BMIViewModel::class.java) {

	var listener: OnUnitClickListener? = null

	override fun setupUI() {
		super.setupUI()
		binding.cancelBmiDialog.setOnClickListener{ dismiss() }
		binding.kilograms.setOnClickListener {
			listener?.unitWeightClickListener(getTextView(it))
		}

		binding.pounds.setOnClickListener { listener?.unitWeightClickListener(getTextView(it)) }
	}

	interface OnUnitClickListener {
		fun unitWeightClickListener ( typeWeigh: String)
	}

	private fun getTextView (textView: View): String {
		textView as TextView
		dismiss()
		return textView.text.toString()
	}

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.29).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}
}