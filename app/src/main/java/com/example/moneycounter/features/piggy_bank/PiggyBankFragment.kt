package com.example.moneycounter.features.piggy_bank

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import com.example.moneycounter.AnalyticsNavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentPiggyBankBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class PiggyBankFragment: BaseFragment<FragmentPiggyBankBinding>(), PiggyBankContract {

    private val presenter by lazy { PiggyBankPresenter() }
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPiggyBankBinding {
        return FragmentPiggyBankBinding.bind(inflater.inflate(R.layout.fragment_piggy_bank, container, false))
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        setupDateChart()
        initListeners()
    }



    override fun setDateChartData(leftDays: Int, passedDays: Int){

        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "label"

        val pieChartDataMap: MutableMap<String, Int> = HashMap()
        pieChartDataMap[" "] = passedDays
        pieChartDataMap["  "] = leftDays

        val colors: ArrayList<Int> = ArrayList()
        colors.add(App.context.getColor(R.color.light_blue))
        colors.add(App.context.getColor(R.color.costs_color))

        for (type in pieChartDataMap.keys) {
            pieEntries.add(PieEntry(pieChartDataMap[type]!!.toFloat(), type))
        }

        val pieDataSet = PieDataSet(pieEntries, label)
        pieDataSet.colors = colors
        pieDataSet.setDrawValues(false)

        val pieData = PieData(pieDataSet)

        binding.piggyBankDateChart.data = pieData
        binding.piggyBankDateChart.invalidate()

        binding.tvLeftDaysPiggyBank.text = leftDays.toString()
    }

    override fun showRules() {
        ValueAnimator.ofFloat(0f, 90f).apply {
            duration = 250
            addUpdateListener {
                binding.ivPiggyBankArrow.rotation = it.animatedValue as Float
            }
            start()
        }
        binding.layoutInstructionsPiggyBank.isVisible = true
    }

    override fun hideRules() {
        ValueAnimator.ofFloat(90f, 0f).apply {
            duration = 250
            addUpdateListener {
                binding.ivPiggyBankArrow.rotation = it.animatedValue as Float
            }
            start()
        }
        binding.layoutInstructionsPiggyBank.isVisible = false
    }

    override fun enableInvestButton() {
        binding.btnInvestPiggyBank.isEnabled = true
        binding.btnInvestPiggyBank.backgroundTintList = null
    }

    override fun openEditText(){
        hideRules()
        binding.layoutShowRulesPiggyBank.isVisible = false
        binding.editInvestPiggyBank.isVisible = true
    }

    override fun setCostOfHour(costsOfHour: Float) {
        var cost: String = String.format("%.2f", costsOfHour)
        cost += "/"
        cost += requireContext().getString(R.string.hour)
        binding.tvCostsOfHour.text = cost
    }

    /**
     * Help fun-s
     */

    private fun setupDateChart(){
        binding.piggyBankDateChart.description.isEnabled=false
        binding.piggyBankDateChart.legend.isEnabled = false
        binding.piggyBankDateChart.isRotationEnabled = false
        binding.piggyBankDateChart.isHighlightPerTapEnabled = false

        binding.piggyBankDateChart.setHoleColor(requireContext().getColor(R.color.transparent))
        binding.piggyBankDateChart.holeRadius = 90f

        binding.piggyBankDateChart.renderer.paintRender.setShadowLayer(
            1f,
            0f,
            16f,
            ContextCompat.getColor(requireContext(),
                R.color.shadow)
        )
    }

    private fun initListeners(){
        binding.tvPiggyBankShowRules.setOnClickListener {
            presenter.onShowRulesClicked()
        }
        binding.ivPiggyBankArrow.setOnClickListener {
            presenter.onShowRulesClicked()
        }
        binding.btnInvestPiggyBank.setOnClickListener {
            presenter.onInvestButtonClicked()
        }
    }

    companion object{
        fun start(navController: NavController){
            val direction = AnalyticsNavGraphDirections.actionToPiggyBank()
            navController.navigate(direction)
        }
    }
}