package com.example.calculator.ui.dataconvert

import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.calculator.R
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityDataConverterBinding
import com.example.calculator.ui.dialogs.data.DataUnitDialog
import com.example.calculator.utils.App.Companion.prefs

class DataConverterActivity : BaseActivity<ActivityDataConverterBinding, DataConverterViewModel>(ActivityDataConverterBinding::inflate, DataConverterViewModel::class.java), View.OnClickListener {

	override fun setupView() {
		super.setupView()
		binding.dataArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownData.setOnClickListener {
			DataUnitDialog(binding.dataLayout).show(
				supportFragmentManager.beginTransaction(), "dataUnit")
		}
		binding.secondUnitDropDownData.setOnClickListener {
			DataUnitDialog(binding.dataLayout).show(supportFragmentManager.beginTransaction(), "secondUnit")
		}

		binding.figuresZeroData.setOnClickListener(this)
		binding.figuresOneData.setOnClickListener(this)
		binding.figuresTwoData.setOnClickListener(this)
		binding.figuresThreeData.setOnClickListener(this)
		binding.figuresFourData.setOnClickListener(this)
		binding.figuresFiveData.setOnClickListener(this)
		binding.figuresSixData.setOnClickListener(this)
		binding.figuresSevenData.setOnClickListener(this)
		binding.figuresEightData.setOnClickListener(this)
		binding.figuresNineData.setOnClickListener(this)

		binding.dataClearBtn.setOnClickListener(this)
		binding.dataRemoveBtnData.setOnClickListener(this)

		binding.valueUnitData.setOnClickListener {
			prefs?.secondField = binding.valueUnitSecondData.text.toString()
			viewModel.isFieldChanged(false)
			binding.valueUnitSecondData.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
			binding.valueUnitData.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
		}
		binding.valueUnitSecondData.setOnClickListener {
			prefs?.firstField = binding.valueUnitData.text.toString()
			viewModel.isFieldChanged(true)
			binding.valueUnitData.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
			binding.valueUnitSecondData.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
		}

		observe()
	}

	private fun observe() {
		viewModel.toFieldLiveData.observe(this) {
			binding.valueUnitData.text = it
		}

		viewModel.toSecondFieldLiveData.observe(this) {
			binding.valueUnitSecondData.text = it
		}
	}

	override fun onClick(v: View?) {
		viewModel.onButtonClick(v?.id)
	}

	override fun onDestroy() {
		super.onDestroy()
		prefs?.firstField = ""
		prefs?.secondField = ""
	}

}