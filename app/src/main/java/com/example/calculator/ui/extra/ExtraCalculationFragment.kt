package com.example.calculator.ui.extra

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.calculator.adapter.ExtraAdapter
import com.example.calculator.base.BaseFragment
import com.example.calculator.databinding.FragmentExtraCalculationBinding

class ExtraCalculationFragment : BaseFragment<FragmentExtraCalculationBinding, ExtraCalculationViewModel>(FragmentExtraCalculationBinding::inflate, ExtraCalculationViewModel::class.java) {

    private val adapter: ExtraAdapter = ExtraAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { viewModel.loadData(it) }
        setupUI()
    }

    private fun setupUI() {
        binding.recyclerExtra.adapter = adapter

        adapter.addOnItemClickListener(object : ExtraAdapter.OnItemClickListener {
            override fun onClick(item: ExtraModel) {
                context?.startActivity(item.intent)
            }
        })

        viewModel.listMutableLiveData.observe(viewLifecycleOwner) {
            adapter.listModel = it
            adapter.notifyDataSetChanged()
        }
    }
}