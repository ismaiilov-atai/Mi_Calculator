package com.example.calculator

import androidx.lifecycle.MutableLiveData
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class MainViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

    val resultDataFromClick: MutableLiveData<String> = MutableLiveData()
    val mathDataFromClick: MutableLiveData<String> = MutableLiveData()

    val shouldShow: MutableLiveData<Boolean> = MutableLiveData()

    fun setResultFromClick(result: String, math: String){
        resultDataFromClick.value = result
        mathDataFromClick.value = math
    }

    fun setShouldShow(shouldShowValue: Boolean){
        shouldShow.value = shouldShowValue
    }
}