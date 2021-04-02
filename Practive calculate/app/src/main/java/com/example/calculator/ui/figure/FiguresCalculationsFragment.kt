package com.example.calculator.ui.figure

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calculator.base.BaseFragment
import com.example.calculator.databinding.FragmentFiguresCalculationsBinding

class FiguresCalculationsFragment :
    BaseFragment<FragmentFiguresCalculationsBinding, FiguresCalculationViewModel>(FragmentFiguresCalculationsBinding::inflate, FiguresCalculationViewModel::class.java), View.OnClickListener {

    private lateinit var adapter: FiguresAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observer()

        viewModel.loadHistory()
    }

    private fun observer() {
        viewModel.fieldLiveData.observe(viewLifecycleOwner) {
            binding.textFigures.text = it
        }

        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            binding.textResult.text = it
        }

        viewModel.equalClickedAnimationLiveData.observe(viewLifecycleOwner) {
            if (it) {
                showResultAnimation(30F, 47F, 400, binding.textResult)
                showResultAnimation(47F, 30F, 400, binding.textFigures)
            } else {
                binding.textResult.textSize = 30F
                binding.textFigures.textSize = 47F
            }
        }

        viewModel.historyLiveData.observe(viewLifecycleOwner) {
            adapter.historyList = it
            adapter.notifyDataSetChanged()
            binding.recyclerView.scrollToPosition(it.size)
        }
    }

    private fun showResultAnimation(startSize: Float, endSize: Float, animationDuration: Long, view: TextView) {
        val animator = ValueAnimator.ofFloat(startSize, endSize)
        animator.duration = animationDuration
        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            view.textSize = animatedValue
        }

        animator.start()
    }

    private fun setupUI() {
        adapter = FiguresAdapter()
        (binding.recyclerView.layoutManager as? LinearLayoutManager)?.stackFromEnd = false
        (binding.recyclerView.layoutManager as? LinearLayoutManager)?.reverseLayout = true
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