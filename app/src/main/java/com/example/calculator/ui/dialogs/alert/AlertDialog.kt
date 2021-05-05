package com.example.calculator.ui.dialogs.alert

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.example.calculator.databinding.AlertDialogBinding
import com.example.calculator.ui.dialogs.base.BaseDialog

class AlertDialog(private val viewGroup: ViewGroup): BaseDialog<AlertDialogBinding,AlertDialogViewModel>(AlertDialogBinding::inflate,AlertDialogViewModel::class.java) {
	var listener: CarryOnListener? = null

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
		binding.btnYes.setOnClickListener { listener?.clickListener(); dismiss() }
		binding.cancelAlertDialog.setOnClickListener { dismiss() }
	}

	interface CarryOnListener {
		fun clickListener()
	}
}