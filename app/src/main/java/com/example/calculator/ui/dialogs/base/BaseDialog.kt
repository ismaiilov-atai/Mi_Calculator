package com.example.calculator.ui.dialogs.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding


open class BaseDialog<Binding: ViewBinding,VM: ViewModel>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> Binding,className: Class<VM>): DialogFragment() {

	private var _binding: Binding? = null
	val binding get() = _binding!!

	val viewModel: VM by lazy {
		ViewModelProvider(this).get(className)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_binding = inflate.invoke(LayoutInflater.from(context),container,false)
		return  binding.root
	}

}