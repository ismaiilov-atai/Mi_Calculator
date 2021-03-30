package com.example.calculator.ui.figure


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.databinding.ItemFigureSolvedViewBinding

class FiguresAdapter : RecyclerView.Adapter<FiguresAdapter.FigureViewHolder>() {

     var historyList :ArrayList<HistoryModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FigureViewHolder {
        return FigureViewHolder(ItemFigureSolvedViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FigureViewHolder, position: Int) {
        holder.onBind(historyList[position])
    }

    override fun getItemCount() :Int = historyList.size


    class FigureViewHolder(private val binding: ItemFigureSolvedViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: HistoryModel) {
                binding.figuresTextView.text = item.math
                binding.figuresTxtresultView.text = item.result
        }

    }
}