package com.example.calculator.ui.share

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toUri
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityAgeShareBinding
import com.example.calculator.utils.Constants
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class AgeShareActivity : BaseActivity<ActivityAgeShareBinding, AgeShareViewModel>(ActivityAgeShareBinding::inflate, AgeShareViewModel::class.java) {

	override fun setupView() {
		super.setupView()
		var byteArray: ByteArray? = null

		when {
			intent.getByteArrayExtra(Constants.KEY_BITMAP) != null -> {
				byteArray = intent.getByteArrayExtra(Constants.KEY_BITMAP)
			}
			intent.getByteArrayExtra("image") != null -> {
				byteArray = intent.getByteArrayExtra("image")
			}
			intent.getByteArrayExtra("bitmap") != null -> {
				byteArray = intent.getByteArrayExtra("bitmap")
			}
			intent.getByteArrayExtra("imageLoan") != null -> {
				byteArray = intent.getByteArrayExtra("imageLoan")
			}
		}

		val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)

		binding.imageExt.setImageBitmap(bmp)

		binding.ageShareArrowBack.setOnClickListener {
			finish()
		}

		binding.telegram.setOnClickListener {
			val urlImage = saveMediaToStorage(bmp)

			urlImage?.let {
				val intent = Intent(Intent.ACTION_SEND)
				intent.type = "image/jpeg"
				intent.putExtra(Intent.EXTRA_STREAM, it)
				startActivity(intent)
			}
		}
	}

	private fun saveMediaToStorage(bitmap: Bitmap): Uri? {
		val filename = "${System.currentTimeMillis()}.jpg"
		var fos: OutputStream? = null
		var uri: Uri? = null

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			this.contentResolver?.also { resolver ->
				val contentValues = ContentValues().apply {
					put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
					put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
					put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
				}

				val imageUri: Uri? =
					resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

				uri = imageUri
				fos = imageUri?.let { resolver.openOutputStream(it) }
			}
		} else {
			val imagesDir =
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
			val image = File(imagesDir, filename)
			fos = FileOutputStream(image)

			uri = image.toUri()
		}

		fos?.use {
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
		}

		return uri
	}
}