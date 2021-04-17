package com.example.calculator.ui.area

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.calculator.R
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityAreaBinding
import com.example.calculator.ui.dialogs.area.AreaUnitDialog

class AreaActivity : BaseActivity<ActivityAreaBinding, AreaViewModel>(ActivityAreaBinding::inflate, AreaViewModel::class.java), View.OnClickListener, AreaUnitDialog.OnItemClickListener {

	override fun setupView() {
		super.setupView()
		viewModel.fieldChange( AreaViewModel.Type.FIRST )
		binding.valueUnitSecondArea.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
		binding.valueUnitArea.setTextColor(ContextCompat.getColor(this, R.color.purple_500))

		binding.areaArrowBackArea.setOnClickListener { finish() }
		binding.firstUnitDropDownArea.setOnClickListener { showDialog(binding.areaLayout,AreaViewModel.Type.FIRST) }
		binding.secondUnitDropDownArea.setOnClickListener { showDialog(binding.areaLayout,AreaViewModel.Type.SECOND) }

		binding.valueUnitArea.setOnClickListener {
			viewModel.fieldChange( AreaViewModel.Type.FIRST )
			binding.valueUnitSecondArea.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
			binding.valueUnitArea.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
		}

		binding.valueUnitSecondArea.setOnClickListener {
			viewModel.fieldChange(AreaViewModel.Type.SECOND)
			binding.valueUnitSecondArea.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
			binding.valueUnitArea.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
		}

		binding.figuresZeroArea.setOnClickListener(this)
		binding.figuresOneArea.setOnClickListener(this)
		binding.figuresTwoArea.setOnClickListener(this)
		binding.figuresThreeArea.setOnClickListener(this)
		binding.figuresFourArea.setOnClickListener(this)
		binding.figuresFiveArea.setOnClickListener(this)
		binding.figuresSixArea.setOnClickListener(this)
		binding.figuresSevenNormalArea.setOnClickListener(this)
		binding.figuresEightArea.setOnClickListener(this)
		binding.figuresNineArea.setOnClickListener(this)
		binding.figuresDotArea.setOnClickListener(this)
		binding.areaClearBtn.setOnClickListener(this)
		binding.areaDeleteBtn.setOnClickListener(this)

		observer()

	}

	private fun observer() {
		viewModel.firstUnitPickerLiveData.observe(this) { binding.firstUnitDropDownArea.text = viewModel.inputConverter(it); binding.descriptionUnitArea.text = it }
		viewModel.secondUnitPickerLiveData.observe(this) { binding.secondUnitDropDownArea.text = viewModel.inputConverter(it); binding.descriptionUnitSecondArea.text = it }

		viewModel.firstFieldLiveData.observe(this ) { binding.valueUnitArea.text = it }
		viewModel.secondFieldLiveData.observe(this ) { binding.valueUnitSecondArea.text = it }
	}

	private fun showDialog(viewGroup: ViewGroup, type: AreaViewModel.Type){
		val dialog = AreaUnitDialog(viewGroup,type)
		dialog.listener = this
		dialog.show(supportFragmentManager.beginTransaction(), "unitPicker $type")
	}

	override fun itemClickListener(areaUnit: String, type: AreaViewModel.Type) {
		viewModel.setDropDownUnit(areaUnit,type)
	}

	override fun onClick(v: View?) {
		viewModel.onButtonClick(v?.id) }
}