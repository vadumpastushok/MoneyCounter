package com.example.moneycounter.features.analytics_pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentAnalyticsPagerBinding
import com.example.moneycounter.features.analytics_list.AnalyticsListFragment
import com.example.moneycounter.model.entity.ui.MoneyType


class AnalyticsPagerFragment : BaseFragment<FragmentAnalyticsPagerBinding>(), AnalyticsPagerContract {

    private val fragments = listOf(
        AnalyticsListFragment(MoneyType.INCOME),
        AnalyticsListFragment(MoneyType.COSTS)
    )
    private val presenter by lazy { AnalyticsPagerPresenter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAnalyticsPagerBinding {
        return FragmentAnalyticsPagerBinding.inflate(inflater, container, false)
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        setupPager()
        initListeners()
    }

    /**
     * Contract
     */

    override fun setIndicatorColor(@ColorRes indicatorColor: Int){
        binding.analyticsDotsIndicator.selectedDotColor = context?.getColor(indicatorColor) ?: return
    }

    /**
     * Help fun-s
     */

    private fun setupPager(){
        val pagerAdapter = ScreenSlidePagerAdapter(childFragmentManager,fragments)
        binding.vpAnalytics.adapter = pagerAdapter
        binding.analyticsDotsIndicator.setViewPager(binding.vpAnalytics)
    }


    private fun initListeners(){
        binding.vpAnalytics.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(position: Int) {
                presenter.onPageSelected(fragments, position)
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