package com.example.moneycounter.features.graph

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.res.ResourcesCompat
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentGraphBinding
import com.example.moneycounter.model.entity.db.GraphEntity
import com.example.moneycounter.model.entity.ui.MoneyType
import com.example.moneycounter.ui.custom.GraphMarker
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import java.text.SimpleDateFormat
import java.util.*


class GraphFragment : BaseFragment<FragmentGraphBinding>(), GraphContract {
    private val presenter by lazy { GraphPresenter() }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGraphBinding {
        return FragmentGraphBinding.inflate(inflater, container, false)
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }


    override fun setupLineChart(moneyType: MoneyType, data: MutableMap<Long, GraphEntity?>){

        val lineData: LineData
        val entryList: MutableList<Entry> = mutableListOf()
        val lineChart: LineChart = if (moneyType == MoneyType.INCOME) {
            binding.incomeLineChart
        }
        else {
            binding.costsLineChart
        }
        lineChart.clear()

        val labels = mutableListOf<String>()
        var index = 0
        for (item in data) {
            labels.add(SimpleDateFormat("dd.MM", Locale.getDefault()).format(item.key))
            val amount = item.value?.amount ?: 0
            entryList.add(Entry(index.toFloat(), amount.toFloat()))
            index++
        }
        val valueFormatter = object: ValueFormatter() {

            override fun getFormattedValue(value: Float): String {
                return labels[value.toInt()]
            }
        }

        lineChart.xAxis.valueFormatter = valueFormatter


        val lineDataSet = LineDataSet(entryList, "")
        if(moneyType == MoneyType.INCOME){
            lineDataSet.setColors(requireContext().getColor(R.color.dark_blue))
            lineDataSet.setCircleColor(requireContext().getColor(R.color.dark_blue))
            lineDataSet.fillDrawable = getDrawable(App.context, R.drawable.graph_gradient_income)
            lineDataSet.valueTextColor = requireContext().getColor(R.color.dark_blue)
        }
        else{
            lineDataSet.setColors(requireContext().getColor(R.color.red))
            lineDataSet.setCircleColor(requireContext().getColor(R.color.red))
            lineDataSet.fillDrawable = getDrawable(App.context, R.drawable.graph_gradient_costs)
            lineDataSet.valueTextColor = requireContext().getColor(R.color.red)
        }

        lineDataSet.valueTextSize = 12f
        lineDataSet.valueTypeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_medium)
        lineDataSet.fillAlpha = 255
        lineDataSet.lineWidth = 2f
        lineDataSet.circleRadius = 4f
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.highLightColor = Color.TRANSPARENT
        lineDataSet.setDrawFilled(true)
        lineDataSet.setDrawCircles(true)
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.setDrawValues(false)







        lineData = LineData(lineDataSet)

        lineChart.xAxis.setDrawAxisLine(true)
        lineChart.xAxis.isGranularityEnabled = true
        lineChart.xAxis.textColor = requireContext().getColor(R.color.hint_text)
        lineChart.xAxis.typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_regular)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.textSize = 12f


        lineChart.xAxis.axisLineColor = requireContext().getColor(R.color.transparent)
        lineChart.xAxis.gridColor = requireContext().getColor(R.color.black_65)

        lineChart.axisLeft.axisLineColor = requireContext().getColor(R.color.black_65)
        lineChart.axisLeft.textColor = requireContext().getColor(R.color.hint_text)
        lineChart.axisLeft.typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_medium)
        lineChart.axisLeft.textSize = 12f
        lineChart.axisLeft.setDrawGridLines(false)



        lineChart.axisRight.setDrawGridLines(false)
        lineChart.axisRight.isEnabled = false

        lineChart.isHighlightPerTapEnabled = true
        lineChart.isHighlightPerDragEnabled = true



        lineChart.setDrawBorders(false)
        lineChart.isScaleXEnabled = true
        lineChart.isScaleYEnabled = false
        lineChart.isDragEnabled = true
        lineChart.legend.isEnabled = false
        lineChart.description.isEnabled = false


        lineChart.xAxis.spaceMax = 0.1f
        lineChart.xAxis.spaceMin = 0.1f

        lineChart.setNoDataText("Недостаточно данных") // TEMP
        lineChart.setNoDataTextColor(context?.getColor(R.color.dark_blue) ?: return)
        lineChart.setNoDataTextTypeface(ResourcesCompat.getFont(requireContext(), R.font.roboto_medium))

        if(data.size >= 2){
            lineChart.data = lineData
            val lastEntry: Entry = entryList[entryList.size - 1]
            val highlight = Highlight(lastEntry.x, lastEntry.y, 0)
            highlight.dataIndex = 0

            lineChart.highlightValue(highlight)
        }


        lineChart.setVisibleXRangeMaximum(7f)
        lineChart.setVisibleXRangeMinimum(0f)
        lineChart.moveViewToX(Float.MAX_VALUE)

        lineChart.setDrawMarkers(true)
        val elevationMarker = GraphMarker(requireContext(), R.layout.el_graph_marker, moneyType)
        lineChart.marker = elevationMarker
        lineChart.invalidate()



    }


    /*companion object {
        fun start(navController: NavController) {
            navController.navigate(R.id.action_to_graph)
        }
    }*/

}