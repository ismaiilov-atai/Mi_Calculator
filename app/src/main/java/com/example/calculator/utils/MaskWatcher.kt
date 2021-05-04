package com.example.calculator.utils

import android.text.Editable
import android.text.TextWatcher

class MaskWatcher(private var mask: String,var listener: EditValueCarryListener? = null): TextWatcher {
	private var isRunning = false

	override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
	override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

	override fun afterTextChanged(s: Editable?) {
		try {

			if (isRunning && s?.toString()?.length != null && s.toString().length > 4) {
				return
			}
			isRunning = true

			val editableLength: Int = if (s?.length != null) s.length else 0

			if (editableLength < mask.length) {
				if (mask.toCharArray()[editableLength] != '#') {
					s?.append(mask[editableLength])
				} else if (mask[editableLength - 1] != '#') {
					s?.insert(editableLength - 1, mask, editableLength - 1, editableLength)
				}
			}

			isRunning = false
			listener?.editValueListener(s.toString())
		} catch ( e: Exception ) {}
	}

	interface EditValueCarryListener { fun editValueListener(s: String) }
}