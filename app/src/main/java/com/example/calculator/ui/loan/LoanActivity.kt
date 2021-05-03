package com.example.calculator.ui.loan

import android.text.*
import android.text.InputFilter.LengthFilter
import android.util.Log
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityLoanBinding
import com.example.calculator.ui.dialogs.loan.LoanDialog

class LoanActivity : BaseActivity<ActivityLoanBinding, LoanViewModel>(
	ActivityLoanBinding::inflate,
	LoanViewModel::class.java
),
	LoanDialog.OnValueDataCarryListener {

	override fun setupView() {
		super.setupView()


		binding.loanArrowBack.setOnClickListener { finish() }

		binding.principalEdit.apply {
			this.addTextChangedListener(object : TextWatcher {
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
				override fun afterTextChanged(s: Editable?) {
					viewModel.setValueCalculation(
						s.toString(), id
					)
				}
			})
		}
		binding.interestEdit.apply {
			this.addTextChangedListener(object : TextWatcher {
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

				}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

				override fun afterTextChanged(s: Editable?) {
					viewModel.setValueCalculation(s.toString(), id)
				}
			})
		}
		binding.regularCalculationBtn.setOnClickListener { viewModel.letsSee() }
		binding.tenureHolder.setOnClickListener { showDialog() }

		observers()
	}

	private fun showDialog () {
		val dialog = LoanDialog(binding.loanGeneral)
			dialog.listener = this
			dialog.show(supportFragmentManager.beginTransaction(), "ooooo")
	}

	private fun observers() {
		viewModel.termLiveData.observe(this) { binding.loanValue.text = it }
	}

	override fun valueCarryListener(year: String, month: String) { viewModel.setTermsLiveData(year, month) }
}

