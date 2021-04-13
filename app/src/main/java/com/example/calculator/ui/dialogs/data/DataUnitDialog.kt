package com.example.calculator.ui.dialogs.data

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.example.calculator.R
import com.example.calculator.databinding.DataUnitLayoutBinding
import com.example.calculator.ui.dialogs.base.BaseDialog

class DataUnitDialog(private val viewGroup: ViewGroup) : BaseDialog<DataUnitLayoutBinding,DataUnitViewModel>(DataUnitLayoutBinding::inflate,DataUnitViewModel::class.java){

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.55).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params

	}

	override fun setupUI() {
		super.setupUI()
		binding.cancelBmiDialog.setOnClickListener{ dismiss() }

	}
}