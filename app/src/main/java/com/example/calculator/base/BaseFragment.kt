package com.example.calculator.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


abstract class BaseFragment<Binding: ViewBinding, VM: BaseViewModel>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> Binding, className: Class<VM>): Fragment(), BaseViewModelEventListener {

    private var _binding: Binding? = null
    val binding get() = _binding!!

    val job = Job()
    val uiScope = CoroutineScope(Main + job)

    val viewModel: VM by lazy {
        ViewModelProvider(this, BaseFactory(this)).get(className)
    }

    private val progressDialog: ACProgressFlower? by lazy {
        context?.let {
            return@lazy ACProgressFlower.Builder(it)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY)
                .bgColor(Color.LTGRAY)
                .build();
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showProgress() {
        uiScope.launch {
            progressDialog?.show()
        }
    }
    override fun hideProgress() {
        uiScope.launch {
            progressDialog?.hide()
        }
    }

}
