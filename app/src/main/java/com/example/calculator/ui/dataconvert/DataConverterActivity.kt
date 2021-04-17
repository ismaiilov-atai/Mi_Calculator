package com.example.calculator.ui.dataconvert


import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.calculator.R
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityDataConverterBinding
import com.example.calculator.ui.dialogs.data.DataUnitDialog


class DataConverterActivity : BaseActivity<ActivityDataConverterBinding, DataConverterViewModel>(ActivityDataConverterBinding::inflate, DataConverterViewModel::class.java), View.OnClickListener
, DataUnitDialog.OnDataPickListener{

	override fun setupView() {
		super.setupView()
		viewModel.isFieldChanged(DataConverterViewModel.Type.ONE)
		binding.valueUnitSecondData.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
		binding.valueUnitData.setTextColor(ContextCompat.getColor(this, R.color.purple_500))

		binding.dataArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownData.setOnClickListener { showDialog(binding.dataLayout,DataConverterViewModel.Type.ONE) }
		binding.secondUnitDropDownData.setOnClickListener { showDialog(binding.dataLayout,DataConverterViewModel.Type.TWO) }

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
		binding.figuresDotData.setOnClickListener(this)

		binding.dataClearBtn.setOnClickListener(this)
		binding.dataRemoveBtnData.setOnClickListener(this)

		binding.valueUnitData.setOnClickListener {
			viewModel.isFieldChanged(DataConverterViewModel.Type.ONE)
			binding.valueUnitSecondData.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
			binding.valueUnitData.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
		}

		binding.valueUnitSecondData.setOnClickListener {
			viewModel.isFieldChanged(DataConverterViewModel.Type.TWO)
			binding.valueUnitData.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
			binding.valueUnitSecondData.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
		}

		observe()
	}

	private fun observe() {
		viewModel.toFieldLiveData.observe(this) { binding.valueUnitData.text = it }
		viewModel.toSecondFieldLiveData.observe(this) { binding.valueUnitSecondData.text = it }
		viewModel.firstDropDownPickLiveData.observe(this) { binding.firstUnitDropDownData.text = it }
		viewModel.secondDropDownPickLiveData.observe(this) { binding.secondUnitDropDownData.text = it }
		viewModel.firstUnitDescripLiveData.observe (this) { binding.descriptionUnitData.text = it }
		viewModel.secondUnitDescripLiveData.observe (this) { binding.descriptionUnitSecondData.text = it }
	}

	override fun onClick(v: View?) {
		viewModel.onButtonClick(v?.id)
	}

	private fun showDialog(viewGroup: ViewGroup, type: DataConverterViewModel.Type) {
		val dialog = DataUnitDialog(viewGroup,type)
		dialog.listener = this
		dialog.show(supportFragmentManager.beginTransaction(),"dialog $type")
	}

	override fun pickDataUnitListener(dataUnit: String, type: DataConverterViewModel.Type) {
		viewModel.dropDownSet(dataUnit,type)
	}
}