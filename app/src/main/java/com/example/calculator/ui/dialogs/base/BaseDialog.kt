package com.example.calculator.ui.dialogs.base


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.example.calculator.R
import com.example.calculator.base.BaseFactory
import com.example.calculator.base.BaseViewModelEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseDialog<Binding: ViewBinding,VM: ViewModel>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> Binding,className: Class<VM>): DialogFragment(),BaseViewModelEventListener {

	private var _binding: Binding? = null
	val binding get() = _binding!!

	private val job = Job()
	private val uiScope = CoroutineScope(Dispatchers.Main + job)

	val viewModel: VM by lazy {
		ViewModelProvider(this,BaseFactory(this)).get(className)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupUI()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_binding = inflate.invoke(LayoutInflater.from(context),container,false)
		return  binding.root
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		dialog?.window?.attributes?.windowAnimations = R.style.dialogAnimation
	}



	private val progressDialog: ACProgressFlower? by lazy {
		requireContext().let {
			return@lazy ACProgressFlower.Builder(it)
				.direction(ACProgressConstant.DIRECT_CLOCKWISE)
				.themeColor(Color.WHITE)
				.fadeColor(Color.DKGRAY)
				.bgColor(Color.LTGRAY)
				.build();
		}
	}


	open fun setupUI(){}

	override fun showProgress() {
		uiScope.launch { progressDialog?.show() }
	}

	override fun hideProgress() {
		uiScope.launch { progressDialog?.hide() }
	}

}