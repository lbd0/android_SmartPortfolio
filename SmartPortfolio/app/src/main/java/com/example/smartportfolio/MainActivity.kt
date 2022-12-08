package com.example.smartportfolio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.smartportfolio.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)

        val adapter = MyFragmentPagerAdapter(this)
        binding.mainViewpager.adapter = adapter
        TabLayoutMediator(binding.mainTabs, binding.mainViewpager) {tab, position ->
            when(position) {
                0 -> tab.text = "Infomation"
                1 -> tab.text = "Resume"
                2 -> tab.text = "Portfolio"
            }
        }.attach()

    }
}

class MyFragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    val fragments : List<Fragment>
    init {
        fragments = listOf(MainInfoFragment(), MainResumeFragment(), MainPortfolioFragment())
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment = fragments[position]
}