package com.example.moneycounter.ui.custom

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.AppCompatImageView
import com.example.moneycounter.R
import com.example.moneycounter.databinding.ElGraphMarkerBinding
import com.example.moneycounter.model.entity.ui.MoneyType
import com.example.moneycounter.utils.ViewExtensions.px
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class GraphMarker(context: Context, layoutResource: Int, val moneyType: MoneyType) : MarkerView(context, layoutResource) {

    var binding: ElGraphMarkerBinding? = null

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        child ?: return
        binding = ElGraphMarkerBinding.bind(child)
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if(moneyType == MoneyType.INCOME){
            binding?.graphAmountView?.setTextColor(context.getColor(R.color.dark_blue))
            binding?.graphDotImage?.setImageDrawable(getDrawable(context, R.drawable.graph_selected_income_dot))
        }
        else{

            binding?.graphAmountView?.setTextColor(context.getColor(R.color.red))
            binding?.graphDotImage?.setImageDrawable(getDrawable(context, R.drawable.graph_selected_costs_dot))
        }

        binding?.graphAmountView?.text = e?.y?.toInt().toString()
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-((height + 24.px) / 2)).toFloat())
    }


}