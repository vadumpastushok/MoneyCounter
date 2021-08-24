package com.example.moneycounter.features.analytics_pager

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentAnalyticsPagerBinding
import com.example.moneycounter.features.analytics_list.AnalyticsListFragment
import com.example.moneycounter.model.entity.MoneyType


class AnalyticsPagerFragment : BaseFragment<FragmentAnalyticsPagerBinding>() {

    private val fragments = listOf(
        AnalyticsListFragment(MoneyType.INCOME),
        AnalyticsListFragment(MoneyType.COSTS)
    )

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAnalyticsPagerBinding {
        return FragmentAnalyticsPagerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
    }
    private fun setupPager(){
        val pagerAdapter = ScreenSlidePagerAdapter(childFragmentManager,fragments)
        binding.vpAnalytics.adapter = pagerAdapter
        binding.analyticsDotsIndicator.setViewPager(binding.vpAnalytics)

        binding.vpAnalytics.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageSelected(position: Int) {
                val context = context ?: return
                val indicatorColor = when (fragments[position].moneyType) {
                    MoneyType.INCOME -> context.getColor(R.color.light_blue)
                    MoneyType.COSTS -> context.getColor(R.color.red)
                }
                binding.analyticsDotsIndicator.selectedDotColor = indicatorColor
            }

        })
    }


    private inner class ScreenSlidePagerAdapter(fm: FragmentManager, val fragments: List<Fragment>) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ) {

        override fun getCount(): Int = fragments.size

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

    }
}