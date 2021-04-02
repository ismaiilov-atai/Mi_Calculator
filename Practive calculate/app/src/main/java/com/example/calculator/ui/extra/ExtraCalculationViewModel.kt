package com.example.calculator.ui.extra

import android.content.Context
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener

class ExtraCalculationViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

        var listModel: ArrayList<ExtraModel> = ArrayList(listOf(
            ExtraModel(R.drawable.ic_age, R.string.extra_age),
            ExtraModel(R.drawable.ic_area,R.string.extra_area),
            ExtraModel(R.drawable.ic_bmi,R.string.extra_bmi),
            ExtraModel(R.drawable.ic_mb_lte,R.string.extra_data),
            ExtraModel(R.drawable.ic_date,R.string.extra_Date),
            ExtraModel(R.drawable.ic_discount,R.string.extra_discount),
            ExtraModel(R.drawable.ic_length,R.string.extra_length),
            ExtraModel(R.drawable.ic_mass,R.string.extra_mass),
            ExtraModel(R.drawable.ic_num_system,R.string.extra_numeral_system),
            ExtraModel(R.drawable.ic_speed,R.string.extra_speed),
            ExtraModel(R.drawable.ic_temperature,R.string.extra_temperature),
            ExtraModel(R.drawable.ic_time,R.string.extra_time),
            ExtraModel(R.drawable.ic_volume,R.string.extra_volume))
        )
}