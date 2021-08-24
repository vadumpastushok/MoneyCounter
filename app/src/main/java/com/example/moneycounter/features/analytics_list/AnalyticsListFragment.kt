package com.example.moneycounter.features.analytics_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentAnalyticsListBinding
import com.example.moneycounter.model.entity.ui.Analytics
import com.example.moneycounter.model.entity.ui.MoneyType
import com.example.moneycounter.utils.RecycleDiffUtilCallback


class AnalyticsListFragment(val moneyType: MoneyType) : BaseFragment<FragmentAnalyticsListBinding>(), AnalyticsListContract {

    private val adapter: AnalyticsListAdapter by lazy { AnalyticsListAdapter(moneyType, context) }
    private val presenter by lazy { AnalyticsListPresenter() }
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAnalyticsListBinding {
        return FragmentAnalyticsListBinding.bind(LayoutInflater.from(context).inflate(R.layout.fragment_analytics_list,container,false))
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        setupRecycler()
    }

    /**
     * Contract
     */

    override fun setData(oldList : MutableList<Analytics>, newList : MutableList<Analytics>){
        val recycleDiffUtilCallback = RecycleDiffUtilCallback(oldList, newList)
        val productDiffResult = DiffUtil.calculateDiff(recycleDiffUtilCallback)
        adapter.setData(newList)
        productDiffResult.dispatchUpdatesTo(adapter)
    }

    override fun setNoDataMessage(title: String) {
        binding.tvNoFinances.isVisible = true
        binding.tvNoFinances.text = title
    }

    override fun getAnalyticsMoneyType(): MoneyType = moneyType


    /**
     * Help fun-s
     */

    private fun setupRecycler() {
        val manager = LinearLayoutManager(context)
        binding.rcAnalyticsList.adapter = adapter
        binding.rcAnalyticsList.layoutManager = manager

        val dividerItemDecoration = com.example.moneycounter.ui.adapter.
        DividerItemDecoration(binding.rcAnalyticsList.context)
        binding.rcAnalyticsList.addItemDecoration(dividerItemDecoration)
    }

}

