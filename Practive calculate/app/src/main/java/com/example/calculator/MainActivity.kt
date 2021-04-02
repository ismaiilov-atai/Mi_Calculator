package com.example.calculator


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.calculator.adapter.SwipeAdapter
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.databinding.FigureMenuBinding
import com.example.calculator.ui.archive.ArchiveActivity
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        viewPager = bindingMain.viewPager
        viewPager.adapter = SwipeAdapter(this)

        TabLayoutMediator(bindingMain.tabLayout, viewPager) { tab, position ->
            when(position){
                0 -> tab.icon = ContextCompat.getDrawable(applicationContext,R.drawable.ic_calculate)
                1 -> tab.icon = ContextCompat.getDrawable(applicationContext,R.drawable.ic_dashboard)
                2 -> tab.icon = ContextCompat.getDrawable(applicationContext,R.drawable.ic_cash)
            }
        }.attach()

        bindingMain.moreItem.setOnClickListener{
           popupDisplay().showAsDropDown(bindingMain.moreItem, -300,30)
        }
    }

    fun popupDisplay() : PopupWindow {
        val pop_binding = FigureMenuBinding.inflate(layoutInflater)
        val customView  = PopupWindow(applicationContext)
        val view = pop_binding.root

        customView.width = 350
        customView.height = 220

        customView.setBackgroundDrawable(null)
        customView.isOutsideTouchable = true
        customView.elevation = 80F
        customView.contentView = view

        pop_binding.historyText.setOnClickListener{
            val intent = Intent(this, ArchiveActivity::class.java)
            startActivity(intent)
        }

        return customView
    }

}