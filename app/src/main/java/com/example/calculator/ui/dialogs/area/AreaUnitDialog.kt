package com.example.calculator.ui.dialogs.area

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.example.calculator.databinding.CustomUnitDialogBinding
import com.example.calculator.ui.area.AreaViewModel
import com.example.calculator.ui.dialogs.UnitAdapter
import com.example.calculator.ui.dialogs.base.BaseDialog

class AreaUnitDialog(private var viewGroup: ViewGroup,private val type: AreaViewModel.Type ) : BaseDialog<CustomUnitDialogBinding,AreaUnitViewModel>(CustomUnitDialogBinding::inflate,AreaUnitViewModel::class.java) {

	var adapter: UnitAdapter? = null
	var listener: OnItemClickListener? = null

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.60).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	override fun setupUI() {
		super.setupUI()
		binding.cancelUnitDialog.setOnClickListener { dismiss() }
		adapter = UnitAdapter()
		binding.unitRecycler.adapter = adapter
		viewModel.loadData()
		adapter?.addItemClickListener(object : UnitAdapter.OnItemClickListener {
			override fun onClick(item: String) {
				listener?.itemClickListener(item,type)
				dismiss()

			}
		})
		observe()
	}

	private fun observe(){
		viewModel.unitListLiveData.observe(this){
			adapter?.listUnit = it
		}
	}

	interface OnItemClickListener {
		fun itemClickListener(areaUnit: String, type: AreaViewModel.Type)
	}
}