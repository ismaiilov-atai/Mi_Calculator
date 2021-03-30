package com.example.calculator.ui.figure


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.databinding.ItemFigureSolvedViewBinding

class FiguresAdapter : RecyclerView.Adapter<FiguresAdapter.FigureViewHolder>() {

     var solvedList:ArrayList<SolvedModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FigureViewHolder {
        return FigureViewHolder(ItemFigureSolvedViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FigureViewHolder, position: Int) {
        holder.onBind(solvedList[position])
    }

    override fun getItemCount() :Int = solvedList.size

    fun addItem(item:SolvedModel){
        solvedList.add(item)
        Log.e("TAG", "addItem: ${solvedList.size}")
        notifyDataSetChanged()
    }

    class FigureViewHolder(private val binding:ItemFigureSolvedViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: SolvedModel) {
                binding.figuresTextView.text = item.firsText
                binding.figuresTxtresultView.text = item.resultText
        }

    }
}