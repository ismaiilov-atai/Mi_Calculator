package com.example.calculator.ui.numerial

import android.view.View
import android.widget.TextView
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityNumeralSystemBinding
import com.example.calculator.ui.dialogs.numeral.NumeralDialog
import com.example.calculator.utils.disable
import com.example.calculator.utils.enable
import com.example.calculator.utils.setPickedColor

class NumeralSystemActivity : BaseActivity<ActivityNumeralSystemBinding, NumeralViewModel>(ActivityNumeralSystemBinding::inflate, NumeralViewModel::class.java),
NumeralDialog.OnUnitClickListener,View.OnClickListener {

	 var hexadecimalDisableList: ArrayList<TextView>? = null
	 var decimalDisableList: ArrayList<TextView>? = null
	 var octalDisableList: ArrayList<TextView>? = null
	 var binaryDisableList: ArrayList<TextView>? = null

	override fun setupView() {
		super.setupView()
		binaryDisableList = arrayListOf(
			binding.figuresTwoNumeral,
			binding.figuresThreeNumeral,
			binding.figuresFourNumeral,
			binding.figuresFiveNumeral,
			binding.figuresSixNumeral,
			binding.figuresSevenNumeral,
			binding.figuresEightNumeral,
			binding.figuresNineNumeral,
			binding.figuresFNumeral,
			binding.figuresENumeral,
			binding.figuresDNumeral,
			binding.figuresCNumeral,
			binding.figuresBNumeral,
			binding.figuresANumeral)
		octalDisableList = arrayListOf (
			binding.figuresEightNumeral,
			binding.figuresNineNumeral,
			binding.figuresFNumeral,
			binding.figuresENumeral,
			binding.figuresDNumeral,
			binding.figuresCNumeral,
			binding.figuresBNumeral,
			binding.figuresANumeral
		)
		decimalDisableList = arrayListOf(
			binding.figuresFNumeral,
			binding.figuresENumeral,
			binding.figuresDNumeral,
			binding.figuresCNumeral ,
			binding.figuresBNumeral,
			binding.figuresANumeral
		)
		hexadecimalDisableList = arrayListOf (
			binding.figuresTwoNumeral,
			binding.figuresThreeNumeral,
			binding.figuresFourNumeral,
			binding.figuresFiveNumeral,
			binding.figuresSixNumeral,
			binding.figuresSevenNumeral,
			binding.figuresEightNumeral,
			binding.figuresNineNumeral,
			binding.figuresDotNumeral,
			binding.figuresFNumeral,
			binding.figuresENumeral,
			binding.figuresDNumeral,
			binding.figuresCNumeral,
			binding.figuresBNumeral,
			binding.figuresANumeral
		)

		binding.valueUnitNumeral.setPickedColor(binding.valueUnitNumeral,binding.valueUnitSecondNumeral)

		binding.numeralArrowBack.setOnClickListener { finish() }

		binding.firstUnitDropDownNumeral.setOnClickListener {
			viewModel.setField( NumeralViewModel.TypeField.FIRSTFIELD)
			it.setPickedColor(binding.valueUnitNumeral,binding.valueUnitSecondNumeral)
			dialogShow (); viewModel.setType(NumeralViewModel.TypeChosen.FIRST)
		}

		binding.secondUnitDropDownNumeral.setOnClickListener {
			viewModel.setField( NumeralViewModel.TypeField.SECONDFIELD)
			it.setPickedColor(binding.valueUnitSecondNumeral,binding.valueUnitNumeral)
			dialogShow (); viewModel.setType(NumeralViewModel.TypeChosen.SECOND)
		}

		binding.valueUnitNumeral.setOnClickListener {
			viewModel.setField( NumeralViewModel.TypeField.FIRSTFIELD)
			it.setPickedColor(binding.valueUnitNumeral,binding.valueUnitSecondNumeral)
			checkPermission(viewModel.getPermissionType(NumeralViewModel.TypeField.FIRSTFIELD))
		}

		binding.valueUnitSecondNumeral.setOnClickListener {
			viewModel.setField( NumeralViewModel.TypeField.SECONDFIELD)
			it.setPickedColor(binding.valueUnitSecondNumeral,binding.valueUnitNumeral)
			checkPermission(viewModel.getPermissionType(NumeralViewModel.TypeField.SECONDFIELD))
		}

		binding.figuresZeroNumeral.setOnClickListener(this)
		binding.figuresOneNumeral.setOnClickListener(this)
		binding.figuresTwoNumeral.setOnClickListener(this)
		binding.figuresThreeNumeral.setOnClickListener(this)
		binding.figuresFourNumeral.setOnClickListener(this)
		binding.figuresFiveNumeral.setOnClickListener(this)
		binding.figuresSixNumeral.setOnClickListener(this)
		binding.figuresSevenNumeral.setOnClickListener(this)
		binding.figuresEightNumeral.setOnClickListener(this)
		binding.figuresNineNumeral.setOnClickListener(this)
		binding.figuresDotNumeral.setOnClickListener(this)

		binding.figuresFNumeral.setOnClickListener(this)
		binding.figuresENumeral.setOnClickListener(this)
		binding.figuresDNumeral.setOnClickListener(this)
		binding.figuresCNumeral.setOnClickListener(this)
		binding.figuresBNumeral.setOnClickListener(this)
		binding.figuresANumeral.setOnClickListener(this)

		binding.figuresClearNumeral.setOnClickListener(this)
		binding.figuresDeleteNumeral.setOnClickListener(this)

		observe()
	}

	private fun observe() {
		viewModel.firstDropDownValue.observe(this) { binding.firstUnitDropDownNumeral.text = it }
		viewModel.secondDropDownValue.observe(this) { binding.secondUnitDropDownNumeral.text = it }

		viewModel.firstFieldValue.observe(this) { binding.valueUnitNumeral.text = it }
		viewModel.secondFieldValue.observe(this) { binding.valueUnitSecondNumeral.text = it }

		viewModel.firstFieldPermissionLiveData.observe(this) { checkPermission(it) }
		viewModel.secondFieldPermissionLiveData.observe(this) { checkPermission(it) }
	}

	var previous = NumeralViewModel.PermissionType.HEX
	private fun checkPermission (permissionType: NumeralViewModel.PermissionType) {
		when (previous) {
			NumeralViewModel.PermissionType.BIN -> binaryDisableList?.forEach { binaryList -> binaryList.enable() }
			NumeralViewModel.PermissionType.OCT -> octalDisableList?.forEach { octalList -> octalList.enable() }
			NumeralViewModel.PermissionType.DEC -> decimalDisableList?.forEach { decimalList -> decimalList.enable() }
			else -> hexadecimalDisableList?.forEach { hexList -> hexList.enable() }
		}

		when (permissionType) {
			NumeralViewModel.PermissionType.BIN -> {

				binaryDisableList?.let { binaryPermission(it) }

			}
			NumeralViewModel.PermissionType.OCT -> octalDisableList?.let { octalPermission(it) }
			NumeralViewModel.PermissionType.DEC -> decimalDisableList?.let { decimalPermission(it) }
			NumeralViewModel.PermissionType.HEX -> hexadecimalDisableList?.let { hexadecimalPermission(it) }
		}
		previous = permissionType
	}

	private fun hexadecimalPermission(hexadecimalDisableList: ArrayList<TextView>) { hexadecimalDisableList.forEach { hexList -> hexList.enable() } }
	private fun decimalPermission (decimalDisableList: ArrayList<TextView>) { decimalDisableList.forEach { it.disable() } }
	private fun octalPermission (octalDisableList: ArrayList<TextView>) { octalDisableList.forEach { it.disable() } }
	private fun binaryPermission (binaryPermission: ArrayList<TextView>) { binaryPermission.forEach { it.disable() } }

	private fun dialogShow () {
		val dialog =  NumeralDialog(binding.numeralLayout)
		dialog.listener = this
		dialog.show(supportFragmentManager.beginTransaction(), "numeral")
	}

	override fun unitClickListener(unitValue: String) {
		viewModel.setValueDropDown(unitValue)
		viewModel.setPermission(unitValue)
	}

	override fun onClick(v: View?) {
		viewModel.clickButton(v?.id)
	}
}