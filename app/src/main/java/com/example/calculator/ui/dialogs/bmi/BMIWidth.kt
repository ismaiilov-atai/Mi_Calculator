package com.example.calculator.ui.dialogs.bmi

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.example.calculator.R
import com.example.calculator.databinding.BmiWidthLayoutBinding
import com.example.calculator.ui.dialogs.base.BaseDialog

class BMIWidth(private var viewGroup: ViewGroup): BaseDialog<BmiWidthLayoutBinding,BMIViewModel>(BmiWidthLayoutBinding::inflate,BMIViewModel::class.java) {

	override fun setupUI() {
		super.setupUI()
		binding.cancelBmiDialog.setOnClickListener{ dismiss() }
		binding.kilograms.setOnClickListener {

		}

		binding.pounds.setOnClickListener {

		}
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

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		dialog?.window?.attributes?.windowAnimations = R.style.dialogAnimation
	}


}