package com.example.calculator.ui.investment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityInvestmentBinding

class InvestmentActivity : BaseActivity<ActivityInvestmentBinding,InvestmentViewModel> (ActivityInvestmentBinding::inflate,InvestmentViewModel::class.java) {

	override fun setupView() {
		super.setupView()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

	}
}