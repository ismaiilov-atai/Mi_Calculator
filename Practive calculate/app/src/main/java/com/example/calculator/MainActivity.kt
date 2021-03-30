package com.example.calculator


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.calculator.adapter.SwipeAdapter
import com.example.calculator.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    var bindingMain: ActivityMainBinding? = null
    private lateinit var swipeAdapter: SwipeAdapter
    private lateinit var viewPager: ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain?.root)
        viewPager = bindingMain?.viewPager!!
        viewPager.adapter = createAdapter()
        val tabLayout = bindingMain?.tabLayout!!

        TabLayoutMediator(tabLayout,viewPager){ tab, position->
            when(position){
                0 -> tab.icon = ContextCompat.getDrawable(applicationContext,R.drawable.ic_calculate)
                1 -> tab.icon = ContextCompat.getDrawable(applicationContext,R.drawable.ic_dashboard)
                2 -> tab.icon = ContextCompat.getDrawable(applicationContext,R.drawable.ic_cash)
            }
        }.attach()

    }

    private fun createAdapter() : SwipeAdapter{
        swipeAdapter = SwipeAdapter(this)
        return swipeAdapter
    }

}