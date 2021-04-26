package com.example.calculator.ui.dialogs.currency

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.example.calculator.databinding.CurrencyDialogBinding
import com.example.calculator.ui.dialogs.base.BaseDialog
import com.example.calculator.utils.Constants

class CurrencyUnitDialog(private val viewGroup: ViewGroup) : BaseDialog<CurrencyDialogBinding, CurrencyDialogViewModel>(CurrencyDialogBinding::inflate,CurrencyDialogViewModel::class.java),
	CurrencyAdapter.OnItemClickListener {

	var currencyAdapter: CurrencyAdapter? = null
	var listener: CurrencyClickListener? = null

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
		currencyAdapter = CurrencyAdapter()
		binding.currencyRecycler.apply { this.adapter = currencyAdapter }
		binding.cancelUnitCurrency.setOnClickListener { dismiss() }
		currencyAdapter?.addItemClickListener(this)
		observe()

		viewModel.loadCurrencies(Constants.API_KEY)
	}

	private fun observe() {
		viewModel.currenciesLiveData.observe (this) {
			currencyAdapter?.listUnit = it
			currencyAdapter?.notifyDataSetChanged()
		}
	}

	override fun onClick(item: String, position: Int) {
		listener?.currencyClickListener(item,position)
		dismiss()
	}

	interface CurrencyClickListener {
		fun currencyClickListener(notes: String, position: Int)
	}

}