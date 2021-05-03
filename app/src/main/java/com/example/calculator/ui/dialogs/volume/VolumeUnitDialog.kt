package com.example.calculator.ui.dialogs.volume

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.example.calculator.databinding.VolumeUnitDialogBinding
import com.example.calculator.ui.dialogs.UnitAdapter
import com.example.calculator.ui.dialogs.base.BaseDialog

class VolumeUnitDialog(private val viewGroup: ViewGroup): BaseDialog<VolumeUnitDialogBinding,VolumeDialogViewModel>(VolumeUnitDialogBinding::inflate,VolumeDialogViewModel::class.java) {

	var adapter: UnitAdapter? = null
	var listener: VolumeClickListener? = null

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.65).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	override fun setupUI() {
		super.setupUI()
		viewModel.loadData()
		binding.cancelUnitDialogVolume.setOnClickListener { dismiss() }
		adapter = UnitAdapter()
		binding.unitRecyclerVolume.adapter = adapter
		adapter?.addItemClickListener(object : UnitAdapter.OnItemClickListener {
			override fun onClick(item: String) {
				listener?.unitClickListener(item)
				dismiss()
			}
		})
		observe()
	}

	private fun observe() {
		viewModel.volumeUnitLiveData.observe(this) { adapter?.listUnit = it }
	}

	interface VolumeClickListener {
		fun unitClickListener(unitValue: String)
	}

}