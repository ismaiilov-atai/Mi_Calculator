package com.example.calculator.ui.archive

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.database.HistoryItem
import com.example.calculator.databinding.ArchiveItemBinding


class ArchiveAdapter: RecyclerView.Adapter<ArchiveAdapter.FigureViewHolder>() {

    var historyList: List<HistoryItem> = ArrayList()

    var listener: OnClickListener? = null

    fun addOnItemClick(listener: OnClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FigureViewHolder {
        return FigureViewHolder(ArchiveItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun onBindViewHolder(holder: FigureViewHolder, position: Int) {
        holder.onBind(historyList[position])

        when(position){
            historyList.lastIndex ->{
                holder.binding.figuresTextView.setTextColor(Color.BLACK)
                holder.binding.figuresTxtresultView.setTextColor(Color.BLACK)
                holder.binding.figuresTextView.textSize = 40f
                holder.binding.figuresTxtresultView.textSize = 40f
                holder.binding.archiveDivider.visibility = View.VISIBLE
            }
            (historyList.lastIndex - 1) -> {
                holder.binding.figuresTextView.setTextColor(Color.BLACK)
                holder.binding.figuresTxtresultView.setTextColor(Color.BLACK)
                holder.binding.figuresTextView.textSize = 40f
                holder.binding.figuresTxtresultView.textSize = 40f
            }
            else -> {
                holder.binding.figuresTextView.setTextColor(Color.parseColor("#B1AEAE"))
                holder.binding.figuresTxtresultView.setTextColor(Color.parseColor("#B1AEAE"))
                holder.binding.archiveDivider.visibility = View.GONE
                holder.binding.figuresTextView.textSize = 20f
                holder.binding.figuresTxtresultView.textSize = 20f
            }
        }
    }

    override fun getItemCount(): Int = historyList.size

    interface OnClickListener{
        fun onClickItem(item: HistoryItem)
    }

    class FigureViewHolder(val binding: ArchiveItemBinding, private val listener: OnClickListener? ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: HistoryItem) {
            binding.archiveItem.setOnClickListener { listener?.onClickItem(item) }
            binding.figuresTextView.text = item.math
            binding.figuresTxtresultView.text = "= ${item.result}"
        }
    }
}