package com.example.calculator.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel(var event:BaseViewModelEventListener) :ViewModel(){
}