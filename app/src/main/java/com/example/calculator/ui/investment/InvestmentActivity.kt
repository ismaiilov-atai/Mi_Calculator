package com.example.calculator.ui.investment


import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityInvestmentBinding
import com.example.calculator.ui.dialogs.investment.InvestmentDialog
import com.example.calculator.ui.results.ResultsActivity
import com.example.calculator.utils.Constants
import com.example.calculator.utils.MaskWatcher

class InvestmentActivity : BaseActivity<ActivityInvestmentBinding,InvestmentViewModel>(ActivityInvestmentBinding::inflate, InvestmentViewModel::class.java),
InvestmentDialog.OnValueDataCarryInvestListener, MaskWatcher.EditValueCarryListener {

	override fun setupView() {
		super.setupView()
		binding.investArrowBack.setOnClickListener { finish() }
		binding.durationHolder.setOnClickListener { showDialog() }

		binding.totalEdit.apply {
			this.addTextChangedListener(object : TextWatcher {
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
				override fun afterTextChanged(s: Editable?) { viewModel.setValueCalculation(s.toString()) }
			})
		}

		binding.interestEditInvestment.addTextChangedListener(MaskWatcher(".##",this))
		binding.investmentCalculationBtn.setOnClickListener {
			viewModel.letsDoMath()
			val intent = Intent(this,ResultsActivity::class.java)
			intent.putExtra(Constants.TOTAL_VALUE, viewModel.futureValue)
			intent.putExtra(Constants.INVESTMENT, viewModel.principalValue)
			intent.putExtra(Constants.YEAR_INVES, viewModel.year)
			intent.putExtra(Constants.MONTH_INVES, viewModel.month)
			startActivity(intent)
		}

		binding.groupId.setOnCheckedChangeListener { group, checkedId ->
				if (checkedId == binding.oneTimeRadiobtn.id) viewModel.setInvestType( InvestmentViewModel.InvestmentType.ONE_TIME )
			    else viewModel.setInvestType( InvestmentViewModel.InvestmentType.RECURRING )
		}

		observers()
	}

	private fun observers() {
		viewModel.termLiveData.observe(this) { binding.durationValue.text = it }
	}

	fun showDialog () {
		val dialog = InvestmentDialog(binding.investmentLayout)
		dialog.listener = this
		dialog.show(supportFragmentManager.beginTransaction(),"invest")
	}

	override fun valueCarryListener(year: String, month: String) { viewModel.setTermsLiveData( year, month ) }

	override fun editValueListener(s: String) { viewModel.setInterestValue(s) }

}
