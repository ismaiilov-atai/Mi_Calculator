package com.example.calculator.ui.dialogs.currency

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.example.calculator.databinding.CurrencyDialogBinding
import com.example.calculator.ui.dialogs.UnitAdapter
import com.example.calculator.ui.dialogs.base.BaseDialog

class CurrencyUnitDialog(private val viewGroup: ViewGroup) : BaseDialog<CurrencyDialogBinding,CurrencyViewModel>(CurrencyDialogBinding::inflate,CurrencyViewModel::class.java) {

	var adapter: UnitAdapter? = null
	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.84).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	override fun setupUI() {
		super.setupUI()
		adapter = UnitAdapter()
		binding.currencyRecycler.adapter = adapter
		binding.cancelUnitCurrency.setOnClickListener { dismiss() }
		observe()
	}

	private fun observe() {

	}
}