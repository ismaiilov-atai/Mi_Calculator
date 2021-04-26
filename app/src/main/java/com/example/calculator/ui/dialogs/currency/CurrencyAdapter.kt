package com.example.calculator.ui.dialogs.currency

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.databinding.ItemUnitBinding

class CurrencyAdapter: RecyclerView.Adapter<CurrencyAdapter.UnitViewHolder>() {

		var listUnit: ArrayList<String> = ArrayList()
		private var listener: OnItemClickListener? = null

		interface OnItemClickListener {
			fun onClick(item: String, position: Int)
		}

		fun addItemClickListener(listener: OnItemClickListener){
			this.listener = listener
		}

		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitViewHolder {
			return  UnitViewHolder(ItemUnitBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
		}

		override fun onBindViewHolder(holder: UnitViewHolder, position: Int) {
			holder.onBind(listUnit[position], position)
		}

		override fun getItemCount(): Int = listUnit.size

		class UnitViewHolder(private val binding: ItemUnitBinding, private val listener: OnItemClickListener?) : RecyclerView.ViewHolder(binding.root) {
			fun onBind(unit: String, position: Int) {
				binding.unitItem.text = unit
				Log.e("TAG", "onBind: get out of here")
				binding.unitLayout.setOnClickListener{ listener?.onClick(unit, position) }
			}
		}
}