package com.example.calculator.ui.dialogs.length

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.example.calculator.databinding.LengthUnitPickViewBinding
import com.example.calculator.ui.dialogs.UnitAdapter
import com.example.calculator.ui.dialogs.base.BaseDialog

class LengthUnitDialog(private val viewGroup: ViewGroup): BaseDialog<LengthUnitPickViewBinding,LengthDialogViewModel>(LengthUnitPickViewBinding::inflate,LengthDialogViewModel::class.java){

	var adapter: UnitAdapter? = null
	var listener: LengthClickListener? = null

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.52).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	override fun setupUI() {
		super.setupUI()
		adapter = UnitAdapter()
		viewModel.loadData()
		binding.lengthRecycler.adapter = adapter
		adapter?.addItemClickListener(object : UnitAdapter.OnItemClickListener {
			override fun onClick(item: String) {
				listener?.onLengthClickListener(item)
				dismiss()
			}

		})

		binding.cancelUnitLength.setOnClickListener{ dismiss() }
		observe()
	}

	private fun observe() {
		viewModel.lengthUnitList.observe(this){
			adapter?.listUnit = it
		}
	}

	interface LengthClickListener {
		fun onLengthClickListener(lengthUnit: String)
	}
}