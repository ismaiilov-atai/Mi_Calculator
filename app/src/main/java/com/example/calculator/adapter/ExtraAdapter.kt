package com.example.calculator.adapter

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.databinding.ItemExtraCalculatingViewBinding
import com.example.calculator.ui.extra.ExtraModel

class ExtraAdapter : RecyclerView.Adapter<ExtraAdapter.ExtraViewHolder>() {

    var listModel: ArrayList<ExtraModel> = ArrayList()

    private var listener: OnItemClickListener? = null

    fun addOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int = listModel.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtraViewHolder {
        return ExtraViewHolder(ItemExtraCalculatingViewBinding.inflate(LayoutInflater.from(parent.context),parent,false), listener)
    }

    override fun onBindViewHolder(holder: ExtraViewHolder, position: Int) {
        holder.onBind(listModel[position])
    }

    interface OnItemClickListener {
        fun onClick(item: ExtraModel)
    }

    class ExtraViewHolder(private val binding: ItemExtraCalculatingViewBinding, private val listener: OnItemClickListener?) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: ExtraModel) {
            binding.itemLayout.setOnClickListener { listener?.onClick(model) }
            binding.itemImageExCalculate.setImageResource(model.icon)
            binding.itemTxtExCalculate.setText(model.description)
        }
    }
}