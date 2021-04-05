package com.example.calculator.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.database.HistoryItem
import com.example.calculator.databinding.ItemFigureSolvedViewBinding

class FiguresAdapter : RecyclerView.Adapter<FiguresAdapter.FigureViewHolder>() {

     var historyList: List<HistoryItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FigureViewHolder {
        return FigureViewHolder(ItemFigureSolvedViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FigureViewHolder, position: Int) {
        holder.onBind(historyList[position])
    }

    override fun getItemCount(): Int = historyList.size

    class FigureViewHolder(private val binding: ItemFigureSolvedViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: HistoryItem) {
                binding.figuresTextView.text = item.math
                binding.figuresTxtresultView.text = "= ${item.result}"
        }

    }
}