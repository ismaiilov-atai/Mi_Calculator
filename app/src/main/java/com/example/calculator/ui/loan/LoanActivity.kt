package com.example.calculator.ui.loan

import android.content.Intent
import android.text.*
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityLoanBinding
import com.example.calculator.ui.dialogs.loan.LoanDialog
import com.example.calculator.ui.results.ResultsActivity
import com.example.calculator.utils.Constants
import com.example.calculator.utils.MaskWatcher

class LoanActivity : BaseActivity<ActivityLoanBinding, LoanViewModel>(ActivityLoanBinding::inflate, LoanViewModel::class.java),
	LoanDialog.OnValueDataCarryListener, MaskWatcher.EditValueCarryListener {

	override fun setupView() {
		super.setupView()


		binding.loanArrowBack.setOnClickListener { finish() }

		binding.principalEdit.apply {
			this.addTextChangedListener(object : TextWatcher {
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
				override fun afterTextChanged(s: Editable?) {
					viewModel.setValueCalculation(s.toString(), id)
				}
			})
		}

		binding.interestEdit.addTextChangedListener(MaskWatcher(".##",this))
		binding.tenureHolder.setOnClickListener { showDialog() }
		binding.regularCalculationBtn.setOnClickListener { sandToNext() }

		observers()
	}

	private fun sandToNext() {
		viewModel.letsDoMath()
		val intent = Intent(this, ResultsActivity::class.java)
		intent.putExtra(Constants.TOTAL_PAYMENT, viewModel.totalPayment)
		intent.putExtra(Constants.INTEREST, viewModel.interest)
		intent.putExtra(Constants.EMI, viewModel.emi)
		intent.putExtra(Constants.PRINCIPAL, viewModel.principalValue)
		intent.putExtra(Constants.YEAR, viewModel.year)
		intent.putExtra(Constants.MONTH, viewModel.month)
		startActivity(intent)
	}

	private fun showDialog () {
		val dialog = LoanDialog(binding.loanGeneral)
			dialog.listener = this
			dialog.show(supportFragmentManager.beginTransaction(), "loanDialog")
	}

	private fun observers() { viewModel.termLiveData.observe(this) { binding.loanValue.text = it } }
	override fun valueCarryListener(year: String, month: String) { viewModel.setTermsLiveData(year, month) }
	override fun editValueListener(s: String) { viewModel.setValueCalculation(s,binding.interestEdit.id) }
}

