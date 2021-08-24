package com.example.moneycounter.features.analytics_list

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
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
        initListeners()
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

    override fun getAnalyticsMoneyType(): MoneyType = moneyType


    private var isByDescending: Boolean = true
    override fun reverseSortImage() {

        val toValue = if (isByDescending) 180f else 0f
        ValueAnimator.ofFloat(binding.ivAnalyticsListSort.rotation, toValue).apply {
            duration = 420
            addUpdateListener {
                binding.ivAnalyticsListSort.rotation = it.animatedValue as Float
            }
            start()
        }


        isByDescending = !isByDescending
    }

    /**
     * Help fun-s
     */


    private fun initListeners(){
        binding.ivAnalyticsListSort.setOnClickListener {
            presenter.onSortViewClicked()
        }
    }

    private fun setupRecycler() {
        if(getAnalyticsMoneyType() == MoneyType.INCOME) {
            binding.ivAnalyticsListSort.imageTintList =
                ColorStateList.valueOf(requireContext().getColor(R.color.dark_blue))
        }else{
            binding.ivAnalyticsListSort.imageTintList =
                ColorStateList.valueOf(requireContext().getColor(R.color.red))
        }

        val manager = LinearLayoutManager(context)
        binding.rcAnalyticsList.adapter = adapter
        binding.rcAnalyticsList.layoutManager = manager

        val dividerItemDecoration = com.example.moneycounter.ui.adapter.
        DividerItemDecoration(binding.rcAnalyticsList.context)
        binding.rcAnalyticsList.addItemDecoration(dividerItemDecoration)
    }

}

