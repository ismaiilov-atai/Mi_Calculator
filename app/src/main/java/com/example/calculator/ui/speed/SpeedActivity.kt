package com.example.calculator.ui.speed

import android.view.View
import android.widget.TextView
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivitySpeedBinding
import com.example.calculator.ui.dialogs.speed.SpeedUnitDialog
import com.example.calculator.utils.setPickedColor

class SpeedActivity : BaseActivity<ActivitySpeedBinding,SpeedViewModel>(ActivitySpeedBinding::inflate,SpeedViewModel::class.java), View.OnClickListener
, SpeedUnitDialog.UnitClickListener {

	override fun setupView() {
		super.setupView()
		binding.valueUnitSpeed.setPickedColor(binding.valueUnitSpeed, binding.valueUnitSecondSpeed)
		binding.speedArrowBack.setOnClickListener { finish() }
		binding.firstUnitDropDownSpeed.setOnClickListener { showDialog(); viewModel.setDropDownType(SpeedViewModel.DropDownType.FIRSTDROP) }
		binding.secondUnitDropDownSpeed.setOnClickListener { showDialog(); viewModel.setDropDownType(SpeedViewModel.DropDownType.SECONDDROP) }
		binding.valueUnitSpeed.setOnClickListener {
			it.setPickedColor(binding.valueUnitSpeed, binding.valueUnitSecondSpeed)
			viewModel.setChosenField(SpeedViewModel.ChosenField.FIRST)
		}
		binding.valueUnitSecondSpeed.setOnClickListener {
			it.setPickedColor(binding.valueUnitSecondSpeed, binding.valueUnitSpeed)
			viewModel.setChosenField(SpeedViewModel.ChosenField.SECOND)
		}

		binding.figuresZeroSpeed.setOnClickListener(this)
		binding.figuresOneSpeed.setOnClickListener(this)
		binding.figuresTwoSpeed.setOnClickListener(this)
		binding.figuresThreeSpeed.setOnClickListener(this)
		binding.figuresFourSpeed.setOnClickListener(this)
		binding.figuresFiveSpeed.setOnClickListener(this)
		binding.figuresSixSpeed.setOnClickListener(this)
		binding.figuresSevenSpeed.setOnClickListener(this)
		binding.figuresEightSpeed.setOnClickListener(this)
		binding.figuresNineSpeed.setOnClickListener(this)
		binding.figuresDotSpeed.setOnClickListener(this)
		binding.speedClearBtn.setOnClickListener(this)
		binding.speedRemoveBtn.setOnClickListener(this)

		observer()
	}

	private fun showDialog(){
		val dialog = SpeedUnitDialog(binding.speedLayout)
		dialog.listener = this
		dialog.show(supportFragmentManager.beginTransaction(),"speedUnit")
	}

	private fun observer() {
		viewModel.firstFieldMutableLiveData.observe(this) { value ->
			binding.valueUnitSpeed.apply { textSizeOperation(this, value ) }
		}
		viewModel.secondFieldMutableLiveData.observe(this) { value ->
			binding.valueUnitSecondSpeed.apply { textSizeOperation(this, value) }
		}
		viewModel.firstDropDownLiveData.observe(this) { binding.firstUnitDropDownSpeed.text = it }
		viewModel.secondDropDownLiveData.observe(this) { binding.secondUnitDropDownSpeed.text = it }
		viewModel.firstDescriptionLiveData.observe(this) { binding.speedTxtFirstDropDown.text = it }
		viewModel.secondDescriptionLiveData.observe(this) { binding.speedTxtSecondDropDown.text = it }
	}

	private fun textSizeOperation ( textView: TextView, txtValue: String ) {
		if (textView.text.length > 22) textView.textSize = 16F else textView.textSize = 22F
		textView.text = txtValue
	}

	override fun onClick(v: View?) { viewModel.onBtnClick(v?.id) }

	override fun unitClickListener(speedUnit: String) { viewModel.setDropDownValue(speedUnit) }

}