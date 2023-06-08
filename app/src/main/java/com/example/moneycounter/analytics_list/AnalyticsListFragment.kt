package com.example.moneycounter.analytics_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentAnalyticsListBinding
import com.example.moneycounter.features.analytics.AnalyticsFragment
import com.example.moneycounter.model.entity.ui.Analytics
import com.example.moneycounter.model.entity.ui.MoneyType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnalyticsListFragment(val moneyType: MoneyType) : BaseFragment<FragmentAnalyticsListBinding>(), AnalyticsListContract {

    @Inject
    lateinit var presenter: AnalyticsListPresenter
    private val adapter: AnalyticsListAdapter by lazy { AnalyticsListAdapter(moneyType, context) }

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
        setupOnBackPressed()
    }

    /**
     * Contract
     */

    override fun setRecycleData(list : MutableList<Analytics>){
        adapter.setData(list)
    }

    override fun setNoDataMessage(title: String) {
        binding.tvNoFinances.isVisible = true
        binding.tvNoFinances.text = title
    }

    override fun getAnalyticsMoneyType(): MoneyType = moneyType

    override fun openHomeFragment() {
        AnalyticsFragment.backClick()
    }

    /**
     * Help fun-s
     */

    private fun setupOnBackPressed(){
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    presenter.onBackClicked()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun setupRecycler() {
        val manager = LinearLayoutManager(context)
        binding.rcAnalyticsList.adapter = adapter
        binding.rcAnalyticsList.layoutManager = manager

        val dividerItemDecoration = com.example.moneycounter.ui.adapter.
        DividerItemDecoration(binding.rcAnalyticsList.context, 62)
        binding.rcAnalyticsList.addItemDecoration(dividerItemDecoration)
    }

}

