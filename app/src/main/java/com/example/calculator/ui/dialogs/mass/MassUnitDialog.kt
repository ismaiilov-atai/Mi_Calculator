package com.example.calculator.ui.dialogs.mass

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.example.calculator.databinding.MassUnitPickerBinding
import com.example.calculator.ui.dialogs.UnitAdapter
import com.example.calculator.ui.dialogs.base.BaseDialog

class MassUnitDialog (private val viewGroup: ViewGroup) : BaseDialog<MassUnitPickerBinding,MassDialogViewModel>(MassUnitPickerBinding::inflate,MassDialogViewModel::class.java) {

	var adapter: UnitAdapter? = null
	var listener: MassUnitClickListener? = null

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.45).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	override fun setupUI() {
		super.setupUI()
		binding.cancelUnitMass.setOnClickListener{ dismiss() }
		viewModel.loadData()
		adapter = UnitAdapter()
		adapter?.addItemClickListener(object : UnitAdapter.OnItemClickListener {
			override fun onClick(item: String) {
				listener?.unitPickClickListener(item)
				dismiss()
			}
		})
		binding.massRecycler.adapter = adapter
		observe()
	}

	private fun observe() {
		viewModel.massLiveData.observe(this){
			adapter?.listUnit = it
		}
	}

	interface MassUnitClickListener {
		fun unitPickClickListener(massUnit: String)
	}

}