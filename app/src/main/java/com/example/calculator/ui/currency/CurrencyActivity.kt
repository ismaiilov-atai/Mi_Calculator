package com.example.calculator.ui.currency

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.calculator.R
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityCurrencyBinding
import com.example.calculator.ui.dialogs.currency.CurrencyUnitDialog
import com.example.calculator.utils.Constants

class CurrencyActivity : BaseActivity<ActivityCurrencyBinding, CurrencyViewModel>(ActivityCurrencyBinding::inflate,CurrencyViewModel::class.java),
	View.OnClickListener, CurrencyUnitDialog.CurrencyClickListener {

	override fun setupView() {
		super.setupView()
		viewModel.loadRates(Constants.API_KEY)

		binding.currencyArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownCurrency.setOnClickListener { openDialog(CurrencyViewModel.DropPicked.FIRSTDROP) }
		binding.secondUnitDropDownCurrency.setOnClickListener { openDialog(CurrencyViewModel.DropPicked.SECONDDROP) }
		binding.thirdUnitDropDownCurrency.setOnClickListener { openDialog(CurrencyViewModel.DropPicked.THIRTDROP) }

		binding.valueIndicatorCurrency.setOnClickListener {
			viewModel.setFieldType(CurrencyViewModel.ChosenType.FIRST)
			it as TextView
			it.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
			viewModel.setDropPicked(CurrencyViewModel.DropPicked.FIRSTDROP)
			binding.secondValueIndicatorCurrency.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
			binding.thirdValueIndicatorCurrency.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
		}

		binding.secondValueIndicatorCurrency.setOnClickListener {
			viewModel.setFieldType(CurrencyViewModel.ChosenType.SECOND)
			it as TextView
			it.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
			viewModel.setDropPicked(CurrencyViewModel.DropPicked.SECONDDROP)
			binding.valueIndicatorCurrency.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
			binding.thirdValueIndicatorCurrency.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
		}

		binding.thirdValueIndicatorCurrency.setOnClickListener {
			viewModel.setFieldType(CurrencyViewModel.ChosenType.THIRD)
			it as TextView
			it.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
			binding.valueIndicatorCurrency.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
			binding.secondValueIndicatorCurrency.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
		}

		binding.figuresZeroCurrency.setOnClickListener(this)
		binding.figuresOneCurrency.setOnClickListener(this)
		binding.figuresTwoCurrency.setOnClickListener(this)
		binding.figuresThreeCurrency.setOnClickListener(this)
		binding.figuresFourCurrency.setOnClickListener(this)
		binding.figuresFiveCurrency.setOnClickListener(this)
		binding.figuresSixCurrency.setOnClickListener(this)
		binding.figuresSevenCurrency.setOnClickListener(this)
		binding.figuresNineCurrency.setOnClickListener(this)
		binding.figuresDotCurrency.setOnClickListener(this)
		binding.currencyClearBtn.setOnClickListener(this)
		binding.currencyRemoveBtn.setOnClickListener(this)
		observers()
	}

	private fun openDialog(dropPick: CurrencyViewModel.DropPicked) {
		val dialog = CurrencyUnitDialog(binding.currencyLayout)
		dialog.listener = this
		dialog.show(supportFragmentManager.beginTransaction(),"currency")
		viewModel.setDropPicked(dropPick)
	}

	private fun observers() {
		viewModel.firstFieldLiveData.observe(this) { binding.valueIndicatorCurrency.text = it }
		viewModel.secondFieldLiveData.observe(this) { binding.secondValueIndicatorCurrency.text = it }
		viewModel.thirdFieldLiveData.observe(this) { binding.thirdValueIndicatorCurrency.text = it }

		viewModel.firstUnitDropLiveData.observe(this) { binding.firstUnitDropDownCurrency.text = it }
		viewModel.secondUnitDropLiveData.observe(this) { binding.secondUnitDropDownCurrency.text = it }
		viewModel.thirdUnitDropLiveData.observe(this) { binding.thirdUnitDropDownCurrency.text = it }

		viewModel.firsUnitDescription.observe (this) { binding.valueDescriptionCurrency.text = it }
		viewModel.secondUnitDescription.observe (this) { binding.secondValueDescriptionCurrency.text = it }
		viewModel.thirdUnitDescription.observe (this) { binding.thirdValueDescriptionCurrency.text = it }
	}

	override fun onClick(v: View?) { viewModel.onButtonClick(v?.id) }

	override fun currencyClickListener(notes: String, position: Int) {
		viewModel.setDropDownValue(notes)
		viewModel.setDescription(notes)
	}
}