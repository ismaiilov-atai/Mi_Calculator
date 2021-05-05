package com.example.calculator.ui.dialogs.about

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.example.calculator.databinding.AboutDialogBinding
import com.example.calculator.ui.dialogs.base.BaseDialog

class AboutDialog(private val viewGroup: ViewGroup) : BaseDialog<AboutDialogBinding, AboutDialogViewModel>(AboutDialogBinding::inflate, AboutDialogViewModel::class.java){

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.50).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	override fun setupUI() {
		super.setupUI()
		binding.toDevoloper.setOnClickListener {
			val viewIntent = Intent("android.intent.action.VIEW", Uri.parse("https://github.com/AtaiDev"))
			startActivity(viewIntent)
		}
		binding.cancelAboutDialog.setOnClickListener { dismiss() }
	}

}