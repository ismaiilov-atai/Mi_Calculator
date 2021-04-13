package com.example.calculator.ui.dialogs.bmi

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.example.calculator.R
import com.example.calculator.databinding.BmiHeightLayoutBinding
import com.example.calculator.ui.dialogs.base.BaseDialog

class BMIHeight(private var viewGroup: ViewGroup): BaseDialog<BmiHeightLayoutBinding, BMIViewModel>(BmiHeightLayoutBinding::inflate,BMIViewModel::class.java) {

	override fun setupUI() {
		super.setupUI()
		binding.cancelHeightDialog.setOnClickListener{ dismiss() }
		binding.centimeters.setOnClickListener {

		}

		binding.meters.setOnClickListener {

		}

		binding.feet.setOnClickListener {

		}

		binding.inches.setOnClickListener {
			it as TextView
			Log.e("test", "setupUI: ${it.text}")
		}

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
}