package com.example.moneycounter.features.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentAnalyticsBinding

class AnalyticsFragment : BaseFragment<FragmentAnalyticsBinding>(), AnalyticsContract {

    private val presenter:AnalyticsPresenter by lazy { AnalyticsPresenter() }
    private lateinit var navController: NavController

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAnalyticsBinding {
        return FragmentAnalyticsBinding.inflate(inflater, container, false)
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        setupBottomBar()
        initListeners()
    }




    override fun openLastFragment(){
        findNavController().popBackStack()
    }

    override fun openGraphPage(){
        binding.MCToolbarAnalytics.setupTitleText(R.string.title_analytics)
    }

    override fun openListPage(){
        binding.MCToolbarAnalytics.setupTitleText(R.string.home_text_income)
    }

    /**
     * Help fun-s
     */

    private fun setupBottomBar(){
        val host: NavHostFragment = childFragmentManager.findFragmentById(R.id.nav_analytics_fragment) as NavHostFragment? ?: return
        navController = host.navController
        binding.bottomBar.setupWithNavController(navController)
    }

    private fun initListeners(){
        binding.MCToolbarAnalytics.setBackButtonClickListener { presenter.onBackButtonClicked() }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.graphFragment -> {
                    presenter.onGraphPageSelected()
                }
                R.id.analyticsListFragment -> {
                    presenter.onListPageSelected()
                }
            }
        }
    }



    companion object {
        fun start(navController: NavController) {
            navController.navigate(R.id.action_to_analytics)
        }
    }
}
