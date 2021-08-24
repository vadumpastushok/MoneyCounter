package com.example.moneycounter.features.piggy_bank

import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import com.example.moneycounter.AnalyticsNavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentPiggyBankBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.slider.Slider
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


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

        setupChart(binding.piggyBankDateChart)
        setupChart(binding.piggyBankSavedChart)
        initListeners()
        setupSlider()
    }

    /**
     * First Group
     */

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

    override fun enableInvestButton() {
        binding.btnInvestPiggyBank.isEnabled = true
        binding.btnInvestPiggyBank.backgroundTintList = null
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

    override fun openEditText(){
        binding.layoutShowRulesPiggyBank.isVisible = false
        binding.editInvestPiggyBank.isVisible = true
    }

    override fun closeEditText() {
        binding.editInvestPiggyBank.text.clear()
        binding.layoutShowRulesPiggyBank.isVisible = true
        binding.editInvestPiggyBank.isVisible = false
    }

    override fun showKeyboard(){
        if(binding.editInvestPiggyBank.requestFocus()) {
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }
    }

    override fun hideKeyboard() {
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun getAmountFromEdit(): Int {
        val text = binding.editInvestPiggyBank.text.toString()
        return if(text.isEmpty()) {
            0
        }else{
            text.toInt()
        }
    }

    /**
     * Second Group
     */

    override fun setTitleAlreadyIndependent(){
        binding.tvIndependenceTimePiggyBank.text = requireContext().getString(R.string.already_independent)
    }

    override fun setTitlePiggyBankEmpty(){
        binding.tvIndependenceTimePiggyBank.text = requireContext().getString(R.string.piggy_bank_empty)
    }

    override fun setTimeIndependence(years: Float) {
        val calendar = Calendar.getInstance()
        val days = years * 365.25f
        calendar[Calendar.DAY_OF_MONTH] += days.toInt()

        var date = ""
        date += calendar[Calendar.DAY_OF_MONTH].toString() + "."
        date += calendar[Calendar.MONTH].plus(1).toString() + "."
        date += calendar[Calendar.YEAR].toString()

        binding.tvIndependenceTimePiggyBank.text = date
    }

    override fun hideSecondGroup(){
        binding.layoutSecondGroupPiggyBank.isVisible = false
    }

    override fun getCurrentPercent(): Float {
        return binding.sliderPiggyBank.value
    }

    override fun setSavedChartData(savedPercent: Int){
        val percent = savedPercent.coerceAtMost(100)

        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "label"

        val pieChartDataMap: MutableMap<String, Int> = HashMap()
        pieChartDataMap[" "] = percent
        pieChartDataMap["  "] = (100 - percent)

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

        binding.piggyBankSavedChart.data = pieData
        binding.piggyBankSavedChart.invalidate()

        var savedString = percent.toString()
        savedString += "%"
        binding.tvPercentSavedPiggyBank.text = savedString
    }

    /**
     * Third Group
     */

    override fun setCostOfHour(costsOfHour: Float) {
        var cost: String = String.format("%.2f", costsOfHour)
        cost += "/"
        cost += requireContext().getString(R.string.hour)
        binding.tvCostsOfHour.text = cost
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    private fun setupChart(chart: PieChart){
        chart.description.isEnabled=false
        chart.legend.isEnabled = false
        chart.isRotationEnabled = false
        chart.isHighlightPerTapEnabled = false

        chart.setHoleColor(requireContext().getColor(R.color.transparent))
        chart.holeRadius = 90f

        chart.renderer.paintRender.setShadowLayer(
            1f,
            0f,
            16f,
            ContextCompat.getColor(requireContext(),
                R.color.shadow)
        )
    }

    private fun setupSlider(){
        binding.sliderPiggyBank.setLabelFormatter { value ->
            value.toInt().toString() + "%"
        }
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
        binding.sliderPiggyBank.addOnChangeListener(
            Slider.OnChangeListener {
                    _, value, _ ->
                presenter.onPercentChanged(value.toInt())
            }
        )
    }

    companion object{
        fun start(navController: NavController){
            val direction = AnalyticsNavGraphDirections.actionToPiggyBank()
            navController.navigate(direction)
        }
    }
}