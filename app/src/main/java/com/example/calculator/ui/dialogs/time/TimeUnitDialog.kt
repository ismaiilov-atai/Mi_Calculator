package com.example.calculator.ui.dialogs.time

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.example.calculator.R
import com.example.calculator.databinding.TimeUnitDialogBinding
import com.example.calculator.ui.dialogs.UnitAdapter
import com.example.calculator.ui.dialogs.base.BaseDialog

class TimeUnitDialog(private val viewGroup: ViewGroup): BaseDialog<TimeUnitDialogBinding,TimeViewModel>(TimeUnitDialogBinding::inflate,TimeViewModel::class.java) {

	var adapter: UnitAdapter? = null
	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.75).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	override fun setupUI() {
		super.setupUI()
		viewModel.loadData()
		binding.cancelUnitDialogTime.setOnClickListener { dismiss() }
		adapter = UnitAdapter()
		binding.unitRecyclerTime.adapter = adapter
		adapter?.addItemClickListener(object : UnitAdapter.OnItemClickListener {
			override fun onClick(item: String) {
				Log.e("TAG", "onClick: $item")
			}
		})
		observe()
	}

	private fun observe() {
		viewModel.timeMutableLiveData.observe(this) { adapter?.listUnit = it }

	}
}