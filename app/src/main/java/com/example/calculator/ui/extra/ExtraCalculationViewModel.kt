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
import com.example.calculator.ui.bmi.BMIActivity
import com.example.calculator.ui.dataconvert.DataConverterActivity
import com.example.calculator.ui.date.DateActivity
import com.example.calculator.ui.discount.DiscountActivity
import com.example.calculator.ui.length.LengthActivity
import com.example.calculator.ui.mass.MassActivity
import com.example.calculator.ui.numerial.NumeralSystemActivity
import com.example.calculator.ui.speed.SpeedActivity
import com.example.calculator.ui.temperature.TemperatureActivity
import com.example.calculator.ui.time.TimeActivity
import com.example.calculator.ui.volume.VolumeActivity

class ExtraCalculationViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

    var listMutableLiveData = MutableLiveData<ArrayList<ExtraModel>>()

    fun loadData(context: Context) {
        listMutableLiveData.value = ArrayList(listOf(
            ExtraModel(R.drawable.ic_age, R.string.extra_age, Intent(context,                  AgeActivity::class.java)),
            ExtraModel(R.drawable.ic_area,R.string.extra_area, Intent(context,                 AreaActivity::class.java)),
            ExtraModel(R.drawable.ic_bmi,R.string.extra_bmi, Intent(context,                   BMIActivity::class.java)),
            ExtraModel(R.drawable.ic_mb_lte,R.string.extra_data, Intent(context,               DataConverterActivity::class.java)),
            ExtraModel(R.drawable.ic_date,R.string.extra_Date, Intent(context,                 DateActivity::class.java)),
            ExtraModel(R.drawable.ic_discount,R.string.extra_discount, Intent(context,         DiscountActivity::class.java)),
            ExtraModel(R.drawable.ic_length,R.string.extra_length, Intent(context,             LengthActivity::class.java)),
            ExtraModel(R.drawable.ic_mass,R.string.extra_mass, Intent(context,                 MassActivity::class.java)),
            ExtraModel(R.drawable.ic_num_system,R.string.extra_numeral_system, Intent(context, NumeralSystemActivity::class.java)),
            ExtraModel(R.drawable.ic_speed,R.string.extra_speed, Intent(context,               SpeedActivity::class.java)),
            ExtraModel(R.drawable.ic_temperature,R.string.extra_temperature, Intent(context,   TemperatureActivity::class.java)),
            ExtraModel(R.drawable.ic_time,R.string.extra_time, Intent(context,                 TimeActivity::class.java)),
            ExtraModel(R.drawable.ic_volume,R.string.extra_volume, Intent(context,             VolumeActivity::class.java)))
        )
    }
}