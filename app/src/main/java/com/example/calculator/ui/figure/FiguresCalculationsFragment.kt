package com.example.calculator.ui.figure

import android.animation.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calculator.adapter.FiguresAdapter
import com.example.calculator.base.BaseFragment
import com.example.calculator.databinding.FragmentFiguresCalculationsBinding
import com.example.calculator.utils.setVisibility
import kotlinx.coroutines.launch

class FiguresCalculationsFragment : BaseFragment<FragmentFiguresCalculationsBinding, FiguresCalculationViewModel>(FragmentFiguresCalculationsBinding::inflate, FiguresCalculationViewModel::class.java), View.OnClickListener {

	private lateinit var adapter: FiguresAdapter

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupUI()
		observer()

		viewModel.loadDataFromHistory(activity?.intent)
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

		viewModel.layoutLiveData.observe(viewLifecycleOwner) {
			if (it) {
				val fadeOut = ObjectAnimator.ofFloat(binding.gridMore, "alpha", 0f, 1f)
				fadeOut.duration = 200

				val fadeIn = ObjectAnimator.ofFloat(binding.gridNormal, "alpha", 1f, 0f)
				fadeIn.duration = 200

				fadeIn.addListener(object : Animator.AnimatorListener {
					override fun onAnimationStart(animation: Animator?) {}
					override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
						binding.gridNormal.setVisibility(!it)
						binding.gridMore.setVisibility(it)
						fadeOut.start()
					}

					override fun onAnimationEnd(animation: Animator?) {}
					override fun onAnimationCancel(animation: Animator?) {}
					override fun onAnimationRepeat(animation: Animator?) {}
				})

				fadeIn.start()

			} else {
				val fadeOut = ObjectAnimator.ofFloat(binding.gridNormal, "alpha", 0f, 1f)
				fadeOut.duration = 200

				val fadeIn = ObjectAnimator.ofFloat(binding.gridMore, "alpha", 1f, 0f)
				fadeIn.duration = 200

				fadeIn.addListener(object : Animator.AnimatorListener {
					override fun onAnimationStart(animation: Animator?) {}
					override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
						binding.gridNormal.setVisibility(!it)
						binding.gridMore.setVisibility(it)
						fadeOut.start()
					}

					override fun onAnimationEnd(animation: Animator?) {}
					override fun onAnimationCancel(animation: Animator?) {}
					override fun onAnimationRepeat(animation: Animator?) {}
				})

				fadeIn.start()

			}

			if (!binding.gridMore.isVisible) {
				Handler(Looper.getMainLooper()).postDelayed({
					binding.figuresTransform.rotation = 180f
					binding.figuresTransform.animate().setDuration(350).rotationBy(180f)
				}, 500)
			} else {
				Handler(Looper.getMainLooper()).postDelayed({
					binding.figuresTransformNormal.rotation = 180f
					binding.figuresTransformNormal.animate().setDuration(350).rotationBy(180f)
				}, 500)
			}
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

		binding.figuresClearNormal.setOnClickListener(this)
		binding.figuresDeleteNormal.setOnClickListener(this)

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

		binding.figuresZeroNormal.setOnClickListener(this)
		binding.figuresOneNormal.setOnClickListener(this)
		binding.figuresTwoNormal.setOnClickListener(this)
		binding.figuresThreeNormal.setOnClickListener(this)
		binding.figuresFourNormal.setOnClickListener(this)
		binding.figuresFiveNormal.setOnClickListener(this)
		binding.figuresSixNormal.setOnClickListener(this)
		binding.figuresSevenNormal.setOnClickListener(this)
		binding.figuresEightNormal.setOnClickListener(this)
		binding.figuresNineNormal.setOnClickListener(this)

		//operations
		binding.figuresPresantage.setOnClickListener(this)
		binding.figuresDivide.setOnClickListener(this)
		binding.figuresMultiple.setOnClickListener(this)
		binding.figuresMinus.setOnClickListener(this)
		binding.figuresPlus.setOnClickListener(this)
		binding.figuresDot.setOnClickListener(this)

		binding.figuresPresantageNormal.setOnClickListener(this)
		binding.figuresDivideNormal.setOnClickListener(this)
		binding.figuresMultipleNormal.setOnClickListener(this)
		binding.figuresMinusNormal.setOnClickListener(this)
		binding.figuresPlusNormal.setOnClickListener(this)
		binding.figuresDotNormal.setOnClickListener(this)

		//extra
		binding.figuresSecondD.setOnClickListener(this)

		binding.figuresDeg.setOnClickListener(this)
		binding.figuresSin.setOnClickListener(this)
		binding.figuresCos.setOnClickListener(this)
		binding.figuresTan.setOnClickListener(this)

		binding.figuresTransform.setOnClickListener(this)
		binding.figuresTransformNormal.setOnClickListener(this)

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
		binding.figuresEqualNormal.setOnClickListener(this)
	}

	override fun onClick(v: View?) {
		viewModel.onButtonClick(v?.id)
	}
}