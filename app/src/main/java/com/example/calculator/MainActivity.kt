package com.example.calculator


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.calculator.adapter.SwipeAdapter
import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.databinding.FigureMenuBinding
import com.example.calculator.ui.archive.ArchiveActivity
import com.example.calculator.ui.dialogs.about.AboutDialog
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(ActivityMainBinding::inflate, MainViewModel::class.java) {

    private lateinit var viewPager: ViewPager2

    override fun setupView() {
        super.setupView()
        viewPager = binding.viewPager
        viewPager.adapter = SwipeAdapter(this)

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.icon =
                    ContextCompat.getDrawable(applicationContext, R.drawable.ic_calculate)
                1 -> tab.icon =
                    ContextCompat.getDrawable(applicationContext, R.drawable.ic_dashboard)
                2 -> tab.icon = ContextCompat.getDrawable(applicationContext, R.drawable.ic_cash)
            }
        }.attach()

        binding.moreItem.setOnClickListener { popupDisplay().showAsDropDown(binding.moreItem, -250, -10) }
        binding.closeScreen.setOnClickListener { Toast.makeText(applicationContext,"Not completed yet ",Toast.LENGTH_SHORT).show() }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun popupDisplay(): PopupWindow {
        val pop_binding = FigureMenuBinding.inflate(layoutInflater)
        val customView = PopupWindow(applicationContext)
        val view = pop_binding.root

        customView.width = 380
        customView.height = 220

        customView.setBackgroundDrawable(null)
        customView.isOutsideTouchable = true
        customView.elevation = 80F
        customView.contentView = view

        val intent = Intent(this, ArchiveActivity::class.java)
        pop_binding.historyMenu.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> view.setBackgroundColor(ContextCompat.getColor(this, R.color.onHover))
                MotionEvent.ACTION_UP -> {
                    startActivity(intent)
                    view.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                    customView.dismiss()
                }
            }
            return@OnTouchListener true
        })

        //about menuItem
        pop_binding.aboutMenu.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> view.setBackgroundColor(ContextCompat.getColor(this, R.color.onHover))
                MotionEvent.ACTION_UP -> {
                    AboutDialog(binding.mainLayout).show(supportFragmentManager.beginTransaction(),"about")
                    view.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                    customView.dismiss()
                }
            }
            return@OnTouchListener true
        })

        return customView
    }
}