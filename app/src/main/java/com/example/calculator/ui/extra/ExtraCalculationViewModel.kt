package com.example.calculator.ui.extra

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.example.calculator.MainActivity
import com.example.calculator.MainViewModel
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.ui.age.AgeActivity
import com.example.calculator.ui.area.AreaActivity

class ExtraCalculationViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

    var listMutableLiveData = MutableLiveData<ArrayList<ExtraModel>>()

    fun loadData(context: Context) {
        listMutableLiveData.value = ArrayList(listOf(
            ExtraModel(R.drawable.ic_age, R.string.extra_age, Intent(context,                  AgeActivity::class.java)),
            ExtraModel(R.drawable.ic_area,R.string.extra_area, Intent(context,                 AreaActivity::class.java)),

            ExtraModel(R.drawable.ic_bmi,R.string.extra_bmi, Intent(context,                   MainActivity::class.java)),
            ExtraModel(R.drawable.ic_mb_lte,R.string.extra_data, Intent(context,               MainActivity::class.java)),
            ExtraModel(R.drawable.ic_date,R.string.extra_Date, Intent(context,                 MainActivity::class.java)),
            ExtraModel(R.drawable.ic_discount,R.string.extra_discount, Intent(context,         MainActivity::class.java)),
            ExtraModel(R.drawable.ic_length,R.string.extra_length, Intent(context,             MainActivity::class.java)),
            ExtraModel(R.drawable.ic_mass,R.string.extra_mass, Intent(context,                 MainActivity::class.java)),
            ExtraModel(R.drawable.ic_num_system,R.string.extra_numeral_system, Intent(context, MainActivity::class.java)),
            ExtraModel(R.drawable.ic_speed,R.string.extra_speed, Intent(context,               MainActivity::class.java)),
            ExtraModel(R.drawable.ic_temperature,R.string.extra_temperature, Intent(context,   MainActivity::class.java)),
            ExtraModel(R.drawable.ic_time,R.string.extra_time, Intent(context,                 MainActivity::class.java)),
            ExtraModel(R.drawable.ic_volume,R.string.extra_volume, Intent(context,             MainActivity::class.java)))
        )
    }
}