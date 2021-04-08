package com.example.calculator.ui.cash

import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.ui.extra.ExtraModel

class InvestmentViewModel(event: BaseViewModelEventListener): BaseViewModel(event) {

    val listModel: ArrayList<ExtraModel> = ArrayList(listOf())

//    ExtraModel(R.drawable.ic_dollar_sing,R.string.currency),
//    ExtraModel(R.drawable.ic_profits,R.string.investment),
//    ExtraModel(R.drawable.ic_personal,R.string.loan)
}