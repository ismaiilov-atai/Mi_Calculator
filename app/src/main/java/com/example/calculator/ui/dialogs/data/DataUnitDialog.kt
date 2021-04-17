package com.example.calculator.ui.dialogs.data

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import com.example.calculator.R
import com.example.calculator.databinding.DataUnitLayoutBinding
import com.example.calculator.ui.dataconvert.DataConverterViewModel
import com.example.calculator.ui.dialogs.base.BaseDialog

class DataUnitDialog(private val viewGroup: ViewGroup, private val type: DataConverterViewModel.Type) : BaseDialog<DataUnitLayoutBinding,DataUnitViewModel>(DataUnitLayoutBinding::inflate,DataUnitViewModel::class.java){

	var listener: OnDataPickListener? = null

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.55).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	override fun setupUI() {
		super.setupUI()
		binding.cancelBmiDialog.setOnClickListener{ dismiss() }
		binding.byteB.setOnClickListener { getTextValue(it,type) }
		binding.kilobyteKb.setOnClickListener { getTextValue(it,type)}
		binding.megabyteMb.setOnClickListener { getTextValue(it,type)}
		binding.gigabyteGb.setOnClickListener { getTextValue(it,type)}
		binding.terabyteTb.setOnClickListener { getTextValue(it,type)}
		binding.petabyte.setOnClickListener { getTextValue(it,type)}
	}

	private fun getTextValue (textView: View, type: DataConverterViewModel.Type) {
		textView as TextView
		listener?.pickDataUnitListener(textView.text.toString(), type)
		dismiss()
	}

	interface OnDataPickListener {
		fun pickDataUnitListener(dataUnit: String, type: DataConverterViewModel.Type)
	}
}