package com.example.calculator.ui.results

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityResultsBinding
import com.example.calculator.ui.share.AgeShareActivity
import com.example.calculator.utils.Constants
import java.io.ByteArrayOutputStream

class ResultsActivity : BaseActivity<ActivityResultsBinding,ResultsViewModel>(ActivityResultsBinding::inflate,ResultsViewModel::class.java) {

	override fun setupView() {
		super.setupView()
		if(intent?.getDoubleExtra(Constants.TOTAL_PAYMENT, 0.0) != 0.0) {
			binding.txtDrawableLeft.text = "Total principal"
			binding.txtDrawableRight.text = "Total interest"
			getFromLoan()
		} else if (intent?.getDoubleExtra(Constants.YEAR_INVES,0.0) != 0.0) {
			binding.totalPaymentAmountResults.visibility = View.INVISIBLE
			binding.totalPaymentTxtResults.visibility = View.INVISIBLE
			binding.txtAmountResult.text = "Total value"
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
			intent.getDoubleExtra(Constants.YEAR_INVES, 0.0),
			intent.getDoubleExtra(Constants.MONTH_INVES, 0.0)
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
		observeFromResult()
		observeFromInvest()

	}

	private fun observeFromInvest() {
		viewModel.futureValueLiveData.observe(this) { binding.amountUsdResults.text = it.toString() }
		viewModel.interestValueLiveData.observe(this) { binding.investAmountTxt.text = it.toString() }

	}

	private fun observeFromResult() {
		viewModel.principalLiveData.observe(this) { binding.investAmountTxt.text = it.toInt().toString() }
		viewModel.emiLiveData .observe(this) { binding.amountUsdResults.text = "$${it.toString().take(5)}" }
		viewModel.interestLiveData.observe(this) { binding.interestAmountTxt.text = it.toInt().toString() }
		viewModel.percentageProgressLiveData.observe(this) { binding.progressResults.progress = it }
		viewModel.totalPaymentLiveData.observe(this) { binding.totalPaymentAmountResults.text = it.toInt().toString() }
		viewModel.yearLiveData.observe(this) { binding.txtYearResult.text = "${it.toInt()} years"}
		viewModel.monthLiveData.observe(this) { binding.txtMonthResult.text = "${it.toInt()} months"}
	}

	private fun getFromLoan() {
		viewModel.setValueFromLoan(
			intent.getDoubleExtra(Constants.INTEREST, 0.0),
			intent.getDoubleExtra(Constants.EMI, 0.0),
			intent.getDoubleExtra(Constants.TOTAL_PAYMENT, 0.0),
			intent.getDoubleExtra(Constants.PRINCIPAL, 0.0),
			intent.getDoubleExtra(Constants.YEAR,0.0),
			intent.getDoubleExtra(Constants.MONTH,0.0)
		)
	}



}