package com.example.calculator.ui.volume

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityVolumeBinding
import com.example.calculator.ui.dialogs.volume.VolumeUnitDialog

class VolumeActivity : BaseActivity<ActivityVolumeBinding,VolumeViewModel>(ActivityVolumeBinding::inflate,VolumeViewModel::class.java) {

	override fun setupView() {
		super.setupView()
		binding.volumeArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownVolume.setOnClickListener { VolumeUnitDialog(binding.volumeLayout).show(supportFragmentManager.beginTransaction(),"volumeUnit") }
		binding.secondUnitDropDownVolume.setOnClickListener { VolumeUnitDialog(binding.volumeLayout).show(supportFragmentManager.beginTransaction(),"volumeUnit2") }
	}

}