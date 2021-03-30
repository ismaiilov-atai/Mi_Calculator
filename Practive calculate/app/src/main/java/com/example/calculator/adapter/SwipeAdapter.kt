package com.example.calculator.adapter


import androidx.fragment.app.Fragment

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.calculator.MainActivity
import com.example.calculator.ui.extra.ExtraCalculationFragment
import com.example.calculator.ui.figure.FiguresCalculationsFragment


class SwipeAdapter(mainActivity: MainActivity) : FragmentStateAdapter(mainActivity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        lateinit var fragment: Fragment
        when (position) {
            0 -> fragment = FiguresCalculationsFragment()
            1 -> fragment = ExtraCalculationFragment()
            2 -> fragment = FiguresCalculationsFragment()
        }
        return fragment
    }
}