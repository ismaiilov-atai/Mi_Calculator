package com.example.calculator.ui.figure

import android.os.Bundle
import android.view.View
import com.example.calculator.databinding.FragmentFiguresCalculationsBinding
import com.example.calculator.base.BaseFragment

class FiguresCalculationsFragment :
    BaseFragment<FragmentFiguresCalculationsBinding, FiguresCalculationViewModel>(
        FragmentFiguresCalculationsBinding::inflate,
        FiguresCalculationViewModel::class.java
    ), View.OnClickListener {

    private lateinit var adapter: FiguresAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observer()
    }

    private fun observer() {
        viewModel.fieldLiveData.observe(this) {
            binding.textFigures.text = it
        }

        viewModel.resultLiveData.observe(this) {
            binding.textResult.text = it
        }

        viewModel.isEqualClicked.observe(this){
            if (it) binding.textResult.textSize = 50F
            else binding.textResult.textSize = 30F
        }
    }

    private fun setupUI() {
        adapter = FiguresAdapter()
        binding.recyclerView.adapter = adapter

        //clear delete
        binding.figuresAc.setOnClickListener(this)
        binding.figuresClear.setOnClickListener(this)

        // figures
        binding.figuresBtnZero.setOnClickListener(this)
        binding.figuresBtnOne.setOnClickListener(this)
        binding.figuresBtnTwo.setOnClickListener(this)
        binding.figuresBtnThree.setOnClickListener(this)
        binding.figuresBtnFour.setOnClickListener(this)
        binding.figuresBtnFive.setOnClickListener(this)
        binding.figuresBtnSix.setOnClickListener(this)
        binding.figuresBtnSeven.setOnClickListener(this)
        binding.figuresBtnEight.setOnClickListener(this)
        binding.figuresBtnNine.setOnClickListener(this)

        //operations
        binding.figuresPracentage.setOnClickListener(this)
        binding.figuresDivide.setOnClickListener(this)
        binding.figuresBtnMultiplication.setOnClickListener(this)
        binding.figuresBtnMinus.setOnClickListener(this)
        binding.figuresBtnPlus.setOnClickListener(this)
        binding.figuresBtnDot.setOnClickListener(this)

        //imageView
        binding.figuresBtnEqual.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        viewModel.onButtonClick(v?.id)
    }
}
