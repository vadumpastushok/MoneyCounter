package com.example.moneycounter.features.analytics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentAnalyticsBinding
import com.example.moneycounter.ui.custom.MCToolbar


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
        toolbar = binding.MCToolbarAnalytics
    }

    /**
     * Contract
     */

    override fun openLastFragment(){
        findNavController().popBackStack()
    }

    override fun setTitleText(text: String){
        binding.MCToolbarAnalytics.setupTitleText(text)
    }

    private fun setupBottomBar(){
        val host: NavHostFragment = childFragmentManager.findFragmentById(R.id.nav_analytics_fragment) as NavHostFragment? ?: return
        navController = host.navController
        binding.bottomBar.setupWithNavController(navController)
    }

    private fun initListeners(){
        binding.MCToolbarAnalytics.setBackButtonClickListener {
            presenter.onBackButtonClicked() }

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id) {
                R.id.graphFragment -> {
                    presenter.onGraphPageSelected()
                }
                R.id.analyticsListFragment -> {
                    presenter.onListPageSelected()
                }
                R.id.piggyBankFragment -> {
                    presenter.onPiggyBankSelected()
                }
            }
        }
    }

    companion object {
        private lateinit var toolbar:MCToolbar

        fun setupTitleText(title: String){
            toolbar.setupTitleText(title)
        }

        fun backClick(){
            toolbar.backClick()
        }

        fun start(navController: NavController) {
            navController.navigate(R.id.action_to_analytics)
        }
    }
}
