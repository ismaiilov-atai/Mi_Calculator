package com.example.calculator.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.calculator.ui.extra.ExtraCalculationFragment
import com.example.calculator.ui.figure.FiguresCalculationsFragment
import com.example.calculator.ui.cash.InvestmentFragment

class SwipeAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FiguresCalculationsFragment()
            1 -> ExtraCalculationFragment()
            2 -> InvestmentFragment()
            else -> Fragment()
        }
    }

}