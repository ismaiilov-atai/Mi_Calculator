package com.example.calculator.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseFactory(var event: BaseViewModelEventListener) :ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BaseViewModelEventListener::class.java).newInstance(event)
    }
}