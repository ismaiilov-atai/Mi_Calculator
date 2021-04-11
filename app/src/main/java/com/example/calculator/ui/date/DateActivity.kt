package com.example.calculator.ui.date

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityDateBinding

class DateActivity : BaseActivity<ActivityDateBinding,DateViewModel>(ActivityDateBinding::inflate,DateViewModel::class.java) {

	override fun setupView() {
		super.setupView()

		binding.dateArrowBack.setOnClickListener { finish() }

	}

}