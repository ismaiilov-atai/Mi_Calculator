package com.example.calculator.ui.dialogs.bmi


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.calculator.R
import com.example.calculator.databinding.BmiResultViewBinding
import com.example.calculator.ui.dialogs.base.BaseDialog
import com.example.calculator.ui.share.AgeShareActivity
import com.example.calculator.utils.Constants
import java.io.ByteArrayOutputStream

class BMIGoDialogs(private val viewGroup: ViewGroup) : BaseDialog<BmiResultViewBinding, BMIViewModel>(BmiResultViewBinding::inflate, BMIViewModel::class.java) {

	override fun onResume() {
		super.onResume()
		val params: WindowManager.LayoutParams? = dialog?.window?.attributes
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		params?.width = viewGroup.width
		params?.height = (viewGroup.height * 0.70).toInt()
		params?.gravity = Gravity.BOTTOM
		dialog?.window?.attributes = params
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		dialog?.window?.attributes?.windowAnimations = R.anim.fragment_fast_out_extra_slow_in
	}

	override fun setupUI() {
		super.setupUI()
		binding.shareBtn.setOnClickListener {
			val intent = Intent(requireContext(), AgeShareActivity::class.java)
			intent.putExtra(Constants.KEY_BITMAP, viewToBitmap(
				binding.bitmapViewCreate, binding.bitmapViewCreate.width, binding.bitmapViewCreate.height)?.let { bitmap -> compressed(bitmap)
				})

			startActivity(intent)
		}

		when (arguments?.getString(Constants.KEY_BMI_RESULT)?.toFloat()?.let { setCategory(it) }) {
			Constants.UNDER_WEIGHT -> binding.categoryIndicator.setTextColor(ContextCompat.getColor(requireContext(), R.color.underweight_color))
			Constants.NORMAL -> binding.categoryIndicator.setTextColor(ContextCompat.getColor(requireContext(), R.color.normal_color))
			Constants.OVER_WEIGHT -> binding.categoryIndicator.setTextColor(ContextCompat.getColor(requireContext(), R.color.overweight_color))
		}
		binding.categoryIndicator.text = arguments?.getString(Constants.KEY_BMI_RESULT)?.toFloat()?.let { setCategory(it) }
		binding.bmiIndicatorResult.text = arguments?.getString(Constants.KEY_BMI_RESULT)
	}

	private fun setCategory(resultBmi: Float): String {
		when (resultBmi) {
			in 16.0..18.5 -> return Constants.UNDER_WEIGHT
			in 18.5..25.0 -> return Constants.NORMAL
			in 25.0..40.0 -> return Constants.OVER_WEIGHT
		}
		return "out of range"
	}

	private fun compressed(bitmap: Bitmap): ByteArray {
		val stream = ByteArrayOutputStream()
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
		return stream.toByteArray()
	}

	private fun viewToBitmap(view: View, width: Int, height: Int): Bitmap? {
		val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
		val canvas = Canvas(bitmap)
		view.draw(canvas)
		return bitmap
	}

}