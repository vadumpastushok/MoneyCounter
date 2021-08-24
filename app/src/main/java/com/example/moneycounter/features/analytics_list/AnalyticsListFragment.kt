package com.example.moneycounter.features.analytics_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentAnalyticsListBinding
import com.example.moneycounter.features.analytics_pager.AnalyticsListAdapter
import com.example.moneycounter.model.entity.Analytics
import com.example.moneycounter.model.entity.MoneyType

class AnalyticsListFragment(val moneyType: MoneyType) : BaseFragment<FragmentAnalyticsListBinding>() {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAnalyticsListBinding {
        return FragmentAnalyticsListBinding.bind(LayoutInflater.from(context).inflate(R.layout.fragment_analytics_list,container,false))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }
    private fun setupRecycler() {
        val data = mutableListOf(
            Analytics(R.drawable.category_icon_pet, R.color.yellow,R.string.category_title_pet,100),
            Analytics(R.drawable.category_icon_gift ,R.color.red,R.string.category_title_gift,100),
            Analytics(R.drawable.category_icon_games, R.color.light_blue,R.string.category_title_games,100),
            Analytics(R.drawable.category_icon_pet, R.color.yellow,R.string.category_title_pet,100),
            Analytics(R.drawable.category_icon_gift ,R.color.red,R.string.category_title_gift,100),
            Analytics(R.drawable.category_icon_games, R.color.light_blue,R.string.category_title_games,100),
            Analytics(R.drawable.category_icon_pet, R.color.yellow,R.string.category_title_pet,100),
            Analytics(R.drawable.category_icon_gift ,R.color.red,R.string.category_title_gift,100),
            Analytics(R.drawable.category_icon_games, R.color.light_blue,R.string.category_title_games,100),
            Analytics(R.drawable.category_icon_pet, R.color.yellow,R.string.category_title_pet,100),
            Analytics(R.drawable.category_icon_gift ,R.color.red,R.string.category_title_gift,100),
            Analytics(R.drawable.category_icon_games, R.color.light_blue,R.string.category_title_games,100),
            Analytics(R.drawable.category_icon_pet, R.color.yellow,R.string.category_title_pet,100),
            Analytics(R.drawable.category_icon_gift ,R.color.red,R.string.category_title_gift,100),
            Analytics(R.drawable.category_icon_games, R.color.light_blue,R.string.category_title_games,100),
            Analytics(R.drawable.category_icon_pet, R.color.yellow,R.string.category_title_pet,100),
            Analytics(R.drawable.category_icon_gift ,R.color.red,R.string.category_title_gift,100),
            Analytics(R.drawable.category_icon_games, R.color.light_blue,R.string.category_title_games,100),
        )
        val context = context ?: return
        val adapter= AnalyticsListAdapter(moneyType, context)
        val manager = LinearLayoutManager(context)
        binding.analyticsList.adapter = adapter
        binding.analyticsList.layoutManager = manager

        val dividerItemDecoration = com.example.moneycounter.ui.adapter.
        DividerItemDecoration(binding.analyticsList.context)
        binding.analyticsList.addItemDecoration(dividerItemDecoration)

        adapter.setData(data)
    }
}