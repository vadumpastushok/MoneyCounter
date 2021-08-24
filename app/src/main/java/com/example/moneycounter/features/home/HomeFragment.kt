package com.example.moneycounter.features.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentHomeBinding
import com.example.moneycounter.features.analytics.AnalyticsFragment
import com.example.moneycounter.features.category.CategoryFragment
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Finance
import com.example.moneycounter.model.entity.ui.MoneyType
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.ssynhtn.waveview.WaveView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


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
        setupPieChart()
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

    override fun setGeneral(sum: Int){
        binding.financeTextView.text = context?.getString(R.string.title_general)
        binding.financePercentView.text = sum.toString()
        binding.financeSumView.text = null
    }

    override fun setIncome(percent: Float, sum: Int){
        binding.financeTextView.text = context?.getString(R.string.title_income)
        var percentString = String.format("%.2f", percent)
        percentString += "%"
        binding.financePercentView.text = percentString
        binding.financeSumView.text = sum.toString()
    }

    override fun setCosts(percent: Float, sum: Int) {
        binding.financeTextView.text = context?.getString(R.string.title_costs)
        var percentString = String.format("%.2f", percent)
        percentString += "%"
        binding.financePercentView.text = percentString
        binding.financeSumView.text=sum.toString()
    }

    override fun setChartData(incomeAmount: Float, costsAmount: Float ){
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "label"

        val typeAmountMap: MutableMap<String, Float> = HashMap()
        typeAmountMap[" "] = incomeAmount
        typeAmountMap["  "] = costsAmount

        val colors: ArrayList<Int> = ArrayList()
        colors.add(App.context.getColor(R.color.light_blue))
        colors.add(App.context.getColor(R.color.costs_color))

        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }

        val pieDataSet = PieDataSet(pieEntries, label)
        pieDataSet.colors = colors
        pieDataSet.setDrawValues(false)

        val pieData = PieData(pieDataSet)

        binding.pieChart.data = pieData
        binding.pieChart.invalidate()
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

    private fun setupPieChart(){
        binding.pieChart.description.isEnabled=false
        binding.pieChart.legend.isEnabled = false
        binding.pieChart.isRotationEnabled = false
        binding.pieChart.isHighlightPerTapEnabled = true

        binding.pieChart.setHoleColor(requireContext().getColor(R.color.transparent))
        binding.pieChart.holeRadius = 90f

        binding.pieChart.renderer.paintRender.setShadowLayer(
            1f,
            0f,
            12f,
            ContextCompat.getColor(requireContext(),
                R.color.shadow)
        )
    }


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
        binding.pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener
        {
            override fun onValueSelected(e: Entry, h: Highlight?) {
                //Log.d("PieChart", e.y.toString())
                presenter.onFinanceSelected(e.y)
            }

            override fun onNothingSelected() {
                presenter.onNothingSelected()
            }
        })
    }

    companion object {
        fun start(navController: NavController) {
            navController.navigate(R.id.action_to_home)
        }
    }
}