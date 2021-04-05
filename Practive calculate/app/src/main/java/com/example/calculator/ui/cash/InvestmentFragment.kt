package com.example.calculator.ui.cash

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.calculator.base.BaseFragment
import com.example.calculator.databinding.FragmentInvestmentBinding
import com.example.calculator.adapter.ExtraAdapter
import com.example.calculator.ui.extra.ExtraModel


class InvestmentFragment: BaseFragment<FragmentInvestmentBinding, InvestmentViewModel>(FragmentInvestmentBinding::inflate, InvestmentViewModel::class.java), ExtraAdapter.OnItemClickListener{

    val adapter = ExtraAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        adapter.listModel = viewModel.listModel
        binding.recyclerInvestment.adapter = adapter
        adapter.addOnItemClickListener(this)
    }

    override fun onClick(item: ExtraModel) {
        Toast.makeText(requireContext(),"${item.description}",Toast.LENGTH_SHORT).show()
    }

}