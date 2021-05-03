package com.example.calculator.ui.dialogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.databinding.ItemUnitBinding

class UnitAdapter : RecyclerView.Adapter<UnitAdapter.UnitViewHolder>() {

	var listUnit: ArrayList<String> = ArrayList()
	private var listener: OnItemClickListener? = null


	interface OnItemClickListener{
		fun onClick(item: String)
	}

	fun addItemClickListener(listener: OnItemClickListener){
		this.listener = listener

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitViewHolder {
		return  UnitViewHolder(ItemUnitBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)

	}

	override fun onBindViewHolder(holder: UnitViewHolder, position: Int) {
		holder.onBind(listUnit[position])
	}

	override fun getItemCount(): Int = listUnit.size

	class UnitViewHolder(private val binding: ItemUnitBinding, private val listener: OnItemClickListener?) : RecyclerView.ViewHolder(binding.root) {
		fun onBind(unit: String) {
			binding.unitLayout.setOnClickListener{ listener?.onClick(unit) }
			binding.unitItem.text = unit
		}
	}
}