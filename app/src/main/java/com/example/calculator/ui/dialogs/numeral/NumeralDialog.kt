package com.example.calculator.ui.dialogs.numeral

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.example.calculator.R
import com.example.calculator.databinding.NumeralDialogBinding
import com.example.calculator.ui.dialogs.base.BaseDialog

class NumeralDialog(private val viewGroup: ViewGroup) : BaseDialog<NumeralDialogBinding,NumeralViewModel>(NumeralDialogBinding::inflate,NumeralViewModel::class.java) {


	var listener: OnUnitClickListener? = null

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.40).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	override fun setupUI() {
		super.setupUI()
		binding.cancelNumericDialog.setOnClickListener{ dismiss() }
		binding.binary.setOnClickListener { listener?.unitClickListener(getViewText(it))}
		binding.octal.setOnClickListener { listener?.unitClickListener( getViewText(it)) }
		binding.decimal.setOnClickListener { listener?.unitClickListener( getViewText(it)) }
		binding.hexadecimal.setOnClickListener { listener?.unitClickListener( getViewText(it)) }
	}

	private fun getViewText(view: View): String {
		view as TextView
		dismiss()
		return  view.text.toString()
	}

	interface OnUnitClickListener {
		fun unitClickListener(unitValue: String)
	}
}