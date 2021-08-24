package com.example.moneycounter.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentHomeBinding
import com.example.moneycounter.features.analytics.AnalyticsFragment
import com.example.moneycounter.features.category.CategoryFragment
import com.example.moneycounter.model.entity.MoneyType
import com.ssynhtn.waveview.WaveView

class HomeFragment: BaseFragment<FragmentHomeBinding>(), HomeContract {

    private val presenter: HomePresenter by lazy { HomePresenter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        initListeners()
    }

    override fun onPause() {
        super.onPause()
        binding.homeWaveView.pauseAnimation()
    }

    override fun onResume() {
        super.onResume()
        binding.homeWaveView.resumeAnimation()
    }

    override fun setWaves(wavesList: MutableList<WaveView.WaveData>) {
        for (wave in wavesList){
            binding.homeWaveView.addWaveData(wave)
        }
    }

    override fun startWaves() {
        binding.homeWaveView.startAnimation()
    }

    override fun openCategoriesIncome() {
        CategoryFragment.start(findNavController(), MoneyType.INCOME)
    }

    override fun openCategoriesCosts() {
        CategoryFragment.start(findNavController(), MoneyType.COSTS)
    }

    override fun openCategoriesAnalytics() {
        AnalyticsFragment.start(findNavController())
    }

    /**
     * Help fun-s
     */

    private fun initListeners() {
        binding.buttonHomeIncome.setOnClickListener {
            presenter.onButtonIncomeClicked()
        }
        binding.buttonHomeCosts.setOnClickListener {
            presenter.onButtonCostsClicked()
        }
        binding.buttonHomeAnalytics.setOnClickListener {
            presenter.onButtonAnalyticsClicked()
        }
    }

    companion object {
        fun start(navController: NavController) {
            navController.navigate(R.id.action_to_home)
        }
    }
}