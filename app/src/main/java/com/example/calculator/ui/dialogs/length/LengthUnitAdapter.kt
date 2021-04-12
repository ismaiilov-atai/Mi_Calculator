package com.example.calculator.ui.dialogs.length

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.databinding.ItemUnitBinding

class LengthUnitAdapter : RecyclerView.Adapter<LengthUnitAdapter.LengthUnitHolder>() {


	var listUnit: ArrayList<String> = ArrayList()
	private var listener: OnItemClickListener? = null

	fun addItemClickListener(listener: OnItemClickListener){
		this.listener = listener
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LengthUnitHolder {
		return LengthUnitHolder(ItemUnitBinding.inflate(LayoutInflater.from(parent.context),parent,false),listener)
	}

	override fun onBindViewHolder(holder: LengthUnitHolder, position: Int) {
			holder.onBind(listUnit[position])
	}

	override fun getItemCount(): Int = listUnit.size

	class LengthUnitHolder(private var binding: ItemUnitBinding,private val listener: OnItemClickListener?) : RecyclerView.ViewHolder(binding.root) {

		fun onBind(item: String) {
			binding.unitItem.text = item
			binding.unitLayout.setOnClickListener{ listener?.itemClickListener(item) }
		}

	}

	interface OnItemClickListener {
		fun itemClickListener(item: String)
	}
}