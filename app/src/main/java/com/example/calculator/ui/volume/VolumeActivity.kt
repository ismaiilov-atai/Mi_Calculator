package com.example.calculator.ui.volume

import android.util.Log
import android.view.View
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityVolumeBinding
import com.example.calculator.ui.dialogs.volume.VolumeUnitDialog
import com.example.calculator.utils.setPickedColor

class VolumeActivity : BaseActivity<ActivityVolumeBinding,VolumeViewModel>(ActivityVolumeBinding::inflate,VolumeViewModel::class.java),
	VolumeUnitDialog.VolumeClickListener, View.OnClickListener {

	override fun setupView() {
		super.setupView()
		binding.valueUnitVolume.setPickedColor(binding.valueUnitVolume,binding.valueUnitSecondVolume)
		binding.volumeArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownVolume.setOnClickListener {
			showDialog()
			viewModel.setVolumeDropDownType(VolumeViewModel.VolumeDropDownType.FIRST_DROP)
		}
		binding.secondUnitDropDownVolume.setOnClickListener {
			showDialog()
			viewModel.setVolumeDropDownType(VolumeViewModel.VolumeDropDownType.SECOND_DROP)
		}

		binding.valueUnitVolume.setOnClickListener {
			viewModel.setVolumeFieldType( VolumeViewModel.FieldType.FIRST_FIELD )
			it.setPickedColor(binding.valueUnitVolume,binding.valueUnitSecondVolume)
		}
		binding.valueUnitSecondVolume.setOnClickListener {
			viewModel.setVolumeFieldType( VolumeViewModel.FieldType.SECOND_FIELD )
			it.setPickedColor(binding.valueUnitSecondVolume,binding.valueUnitVolume)
		}

		binding.figuresZeroVolume.setOnClickListener(this)
		binding.figuresOneVolume.setOnClickListener(this)
		binding.figuresTwoVolume.setOnClickListener(this)
		binding.figuresThreeVolume.setOnClickListener(this)
		binding.figuresFourVolume.setOnClickListener(this)
		binding.figuresFiveVolume.setOnClickListener(this)
		binding.figuresSixVolume.setOnClickListener(this)
		binding.figuresSevenVolume.setOnClickListener(this)
		binding.figuresEightVolume.setOnClickListener(this)
		binding.figuresNineVolume.setOnClickListener(this)
		binding.figuresDotVolume.setOnClickListener(this)
		binding.volumeRemoveBtn.setOnClickListener(this)
		binding.volumeClearBtn.setOnClickListener(this)

		observers()
	}

	private fun observers() {
		viewModel.firstFieldValueLiveData.observe(this) { binding.valueUnitVolume.text = it }
		viewModel.secondFieldValueLiveData.observe(this) { binding.valueUnitSecondVolume.text = it }
		viewModel.firstVolumeDropValueLiveData.observe(this) { binding.firstUnitDropDownVolume.text = it }
		viewModel.secondVolumeDropValueLiveData.observe(this) { binding.secondUnitDropDownVolume.text = it }
		viewModel.firstVolumeDescriptionValueLiveData.observe(this) { binding.volumeTxtFirstDropDown.text = it }
		viewModel.secondVolumeDescriptionValueLiveData.observe(this) { binding.volumeTxtSecondDropDown.text = it }
	}

	override fun unitClickListener(unitValue: String) { viewModel.setValues(unitValue) }

	private fun showDialog () {
		val dialog = VolumeUnitDialog(binding.volumeLayout)
			dialog.listener = this
			dialog.show(supportFragmentManager.beginTransaction(),"volumeUnit2")
	}

	override fun onClick(v: View?) { viewModel.eachClick(v?.id) }



}