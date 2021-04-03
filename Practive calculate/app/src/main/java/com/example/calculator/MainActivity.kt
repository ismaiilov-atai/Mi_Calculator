package com.example.calculator


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
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

    @SuppressLint("ClickableViewAccessibility")
    fun popupDisplay() : PopupWindow {
        val pop_binding = FigureMenuBinding.inflate(layoutInflater)
        val customView  = PopupWindow(applicationContext)
        val view = pop_binding.root

        customView.width = 380
        customView.height = 220

        customView.setBackgroundDrawable(null)
        customView.isOutsideTouchable = true
        customView.elevation = 80F
        customView.contentView = view

        val intent = Intent(this,ArchiveActivity::class.java)
        pop_binding.historyMenu.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            when (motionEvent.action){
                MotionEvent.ACTION_DOWN -> view.setBackgroundColor(ContextCompat.getColor(this,R.color.onHover))
                MotionEvent.ACTION_UP -> {
                    view.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
                    startActivity(intent)
                }
            }
            return@OnTouchListener true
        })

        return customView
    }

    private var math_value = ""
    private var math_result = ""

    override fun onResume() {
        super.onResume()
        if (intent.getStringExtra(ArchiveActivity.KEY_MATH)?.isNotEmpty() == true &&
            intent.getStringExtra(ArchiveActivity.KEY_RESULT)?.isNotEmpty() == true) {
                intent.getStringExtra(ArchiveActivity.KEY_MATH)?.let { math_value = it}
                intent.getStringExtra(ArchiveActivity.KEY_RESULT)?.let { math_result = it }
        }
    }

    fun getResultString(): String {
        return math_result
    }

    fun getMathString(): String {
        return math_value
    }


}