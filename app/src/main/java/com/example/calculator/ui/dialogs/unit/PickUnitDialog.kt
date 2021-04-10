package com.example.calculator.ui.dialogs.unit

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.calculator.R
import com.example.calculator.base.BaseFragment
import com.example.calculator.databinding.CustomUnitDialogBinding
import com.example.calculator.ui.dialogs.base.BaseDialog

class PickUnitDialog(private var viewGroup: ViewGroup) : BaseDialog<CustomUnitDialogBinding,UnitViewModel>(CustomUnitDialogBinding::inflate,UnitViewModel::class.java) {

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.7).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		dialog?.window?.attributes?.windowAnimations = R.style.dialogAnimation
	}
}