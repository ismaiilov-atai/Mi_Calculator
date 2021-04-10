package com.example.calculator.ui.dialogs

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.calculator.R
import com.example.calculator.databinding.CustomUnitDialogBinding

class PickUnitDialog : DialogFragment() {

	private var dBinding: CustomUnitDialogBinding? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		dBinding = CustomUnitDialogBinding.inflate(LayoutInflater.from(context),container,false)
		return dBinding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupView()
	}

	private fun setupView() {
			dBinding?.cancelUnitDialog?.setOnClickListener{
				dismiss()
			}
	}

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		params?.width = WindowManager.LayoutParams.MATCH_PARENT
		params?.height = 1850
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		dialog?.window?.attributes?.windowAnimations = R.style.dialogAnimation

	}

}