package com.example.calculator.ui.cash

import android.os.Bundle
import android.view.View
import com.example.calculator.base.BaseFragment
import com.example.calculator.databinding.FragmentInvestmentBinding
import com.example.calculator.adapter.ExtraAdapter
import com.example.calculator.ui.extra.ExtraModel


class InvestmentFragment: BaseFragment<FragmentInvestmentBinding, InvestmentFragViewModel>(FragmentInvestmentBinding::inflate, InvestmentFragViewModel::class.java) {

    val adapter = ExtraAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { viewModel.loadData(it) }
        setupUI()
        observe()
    }

    private fun observe() {
        viewModel.listMutableLiveData.observe( viewLifecycleOwner ) {
            adapter.listModel = it
        }
    }

    private fun setupUI() {
        binding.recyclerInvestment.adapter = adapter
        adapter.addOnItemClickListener(object : ExtraAdapter.OnItemClickListener{
            override fun onClick(item: ExtraModel) {
                context?.startActivity(item.intent)
            }
        })
    }

}