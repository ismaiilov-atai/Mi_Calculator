package com.example.calculator.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseActivity<B: ViewBinding, VM: BaseViewModel>(private var bindingInflate: (LayoutInflater) -> B, className: Class<VM>): AppCompatActivity(),BaseViewModelEventListener {

    protected lateinit var binding: B
    private var mViewModel: VM? = null

    protected val job = Job()
    protected val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    val viewModel: VM by lazy { ViewModelProvider(this,BaseFactory(this)).get(className) }

    private val progressDialog: ACProgressFlower? by lazy {
        applicationContext?.let {
            return@lazy ACProgressFlower.Builder(it)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY)
                .bgColor(Color.LTGRAY)
                .build();
        }
    }

    open fun setupView() {}

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