package com.example.calculator.ui.results

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.View
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityResultsBinding
import com.example.calculator.ui.share.AgeShareActivity
import com.example.calculator.utils.Constants
import java.io.ByteArrayOutputStream

class ResultsActivity : BaseActivity<ActivityResultsBinding,ResultsViewModel>(ActivityResultsBinding::inflate,ResultsViewModel::class.java) {

	var isFromInvest = false
	override fun setupView() {
		super.setupView()
		binding.resultsArrowBack.setOnClickListener { finish() }
		if(intent?.getDoubleExtra(Constants.TOTAL_PAYMENT, 0.0) != 0.0) {
			binding.txtDrawableLeft.text = "Total principal"
			binding.txtDrawableRight.text = "Total interest"
			isFromInvest = false
			getFromLoan()
		} else if (intent?.getDoubleExtra(Constants.TOTAL_VALUE,0.0) != 0.0) {
			binding.totalPaymentAmountResults.visibility = View.INVISIBLE
			binding.totalPaymentTxtResults.visibility = View.INVISIBLE
			binding.txtAmountResult.text = "Total value"
			binding.txtDrawableLeft.text = "Total investment"
			binding.txtDrawableRight.text = "Total interest"
			isFromInvest = true
			getFromInvest()
		}

		binding.shareBtnResult.setOnClickListener {
			val intent = Intent(this, AgeShareActivity::class.java)
			intent.putExtra("imageLoan", createDateBitmap())
			startActivity(intent)
		}

		observers()
	}

	private fun getFromInvest() {
		viewModel.setValueFromInvest (
			intent.getDoubleExtra(Constants.TOTAL_VALUE, 0.0),
			intent.getDoubleExtra(Constants.INVESTMENT, 0.0),
			intent.getIntExtra(Constants.YEAR_INVES, 0),
			intent.getIntExtra(Constants.MONTH_INVES, 0)
		)
	}

	private fun createDateBitmap(): ByteArray {
		val bitmap = Bitmap.createBitmap(binding.amountHolderResult.width, binding.amountHolderResult.height, Bitmap.Config.RGB_565)
		val canvas = Canvas(bitmap)
		binding.amountHolderResult.draw(canvas)

		val stream = ByteArrayOutputStream()
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
		return stream.toByteArray()
	}

	@SuppressLint("SetTextI18n")
	private fun observers() {
		if (!isFromInvest)observeFromResult()
		else observeFromInvest()
	}

	@SuppressLint("SetTextI18n")
	private fun observeFromInvest() {
		viewModel.futureValueLiveData.observe(this) { binding.amountUsdResults.text = "$$it"}
		viewModel.interestValueLiveData.observe(this) { binding.interestAmountTxt.text = it.toInt().toString() }
		viewModel.principalLiveData.observe(this) { binding.investAmountTxt.text = it.toInt().toString() }
		viewModel.yearLiveData.observe(this) { binding.txtYearResult.text = "${it.toInt()} years"}
		viewModel.monthLiveData.observe(this) { binding.txtMonthResult.text = "${it.toInt()} months"}
		viewModel.percentageProgressLiveData.observe(this) { binding.progressResults.progress =  it}

	}

	@SuppressLint("SetTextI18n")
	private fun observeFromResult() {
		viewModel.principalLiveData.observe(this) { binding.investAmountTxt.text = it.toInt().toString() }
		viewModel.emiLiveData.observe(this) { binding.amountUsdResults.text = "$$it" }
		viewModel.interestLiveData.observe(this) { binding.interestAmountTxt.text = it.toInt().toString() }
		viewModel.percentageProgressLiveData.observe(this) { binding.progressResults.progress = it }
		viewModel.totalPaymentLiveData.observe(this) { binding.totalPaymentAmountResults.text = "$$it" }
		viewModel.yearLiveData.observe(this) { binding.txtYearResult.text = "$it years"}
		viewModel.monthLiveData.observe(this) { binding.txtMonthResult.text = "$it months"}
	}

	private fun getFromLoan() {
		viewModel.setValueFromLoan(
			intent.getDoubleExtra(Constants.INTEREST, 0.0),
			intent.getDoubleExtra(Constants.EMI, 0.0),
			intent.getDoubleExtra(Constants.TOTAL_PAYMENT, 0.0),
			intent.getDoubleExtra(Constants.PRINCIPAL, 0.0),
			intent.getIntExtra(Constants.YEAR,0),
			intent.getIntExtra(Constants.MONTH,0)
		)
	}

}