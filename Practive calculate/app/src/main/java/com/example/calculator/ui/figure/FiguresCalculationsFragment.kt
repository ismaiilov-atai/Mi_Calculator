package com.example.calculator.ui.figure

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calculator.MainActivity
import com.example.calculator.MainViewModel
import com.example.calculator.base.BaseFragment
import com.example.calculator.databinding.FragmentFiguresCalculationsBinding

class FiguresCalculationsFragment: BaseFragment<FragmentFiguresCalculationsBinding, FiguresCalculationViewModel>(FragmentFiguresCalculationsBinding::inflate, FiguresCalculationViewModel::class.java), View.OnClickListener {

    private lateinit var adapter: FiguresAdapter

//    private val sharedViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    private val sharedViewModel: MainViewModel by activityViewModels()

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
        binding.figuresClear.setOnClickListener(this)
        binding.figuresDelete.setOnClickListener(this)

        // figures
        binding.figuresZero.setOnClickListener(this)
        binding.figuresOne.setOnClickListener(this)
        binding.figuresTwo.setOnClickListener(this)
        binding.figuresThree.setOnClickListener(this)
        binding.figuresFour.setOnClickListener(this)
        binding.figuresFive.setOnClickListener(this)
        binding.figuresSix.setOnClickListener(this)
        binding.figuresSeven.setOnClickListener(this)
        binding.figuresEight.setOnClickListener(this)
        binding.figuresNine.setOnClickListener(this)

        //operations
        binding.figuresPresantage.setOnClickListener(this)
        binding.figuresDivide.setOnClickListener(this)
        binding.figuresMultiple.setOnClickListener(this)
        binding.figuresMinus.setOnClickListener(this)
        binding.figuresPlus.setOnClickListener(this)
        binding.figuresDot.setOnClickListener(this)

        //extra
        binding.figuresSecondD.setOnClickListener(this)

        binding.figuresDeg.setOnClickListener(this)
        binding.figuresSin.setOnClickListener(this)
        binding.figuresCos.setOnClickListener(this)
        binding.figuresTan.setOnClickListener(this)

        binding.figuresXY.setOnClickListener(this)
        binding.figuresLg.setOnClickListener(this)
        binding.figuresIn.setOnClickListener(this)
        binding.figuresLeftParenthesis.setOnClickListener(this)
        binding.figuresRightParenthesis.setOnClickListener(this)
        binding.figuresUnderX.setOnClickListener(this)
        binding.figuresXExclamation.setOnClickListener(this)
        binding.figuresOneDivideX.setOnClickListener(this)
        binding.figuresPConstant.setOnClickListener(this)
        binding.figuresE.setOnClickListener(this)

        //imageView
        binding.figuresEqual.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        viewModel.onButtonClick(v?.id)
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.resultDataFromClick.observe(requireActivity()){
            viewModel.resultLiveData.value = it
        }

        sharedViewModel.mathDataFromClick.observe(requireActivity()){
            viewModel.fieldLiveData.value = it
        }
    }
}