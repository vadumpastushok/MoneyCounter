package com.example.moneycounter.features.analytics_pager

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moneycounter.R
import com.example.moneycounter.databinding.ItemAnalyticsBinding
import com.example.moneycounter.model.entity.Analytics
import com.example.moneycounter.model.entity.ui.MoneyType

class AnalyticsListAdapter(private val moneyType: MoneyType, private val context: Context) : RecyclerView.Adapter<AnalyticsListAdapter.AnalyticsListViewHolder>() {

    private var data: MutableList<Analytics> = mutableListOf()

    fun setData(newData : MutableList<Analytics>){
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnalyticsListViewHolder {
        return AnalyticsListViewHolder(
            ItemAnalyticsBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                R.layout.item_analytics,
                parent,
                false
            )))
    }

    override fun onBindViewHolder(
        holder: AnalyticsListViewHolder,
        position: Int
    ) {
        val binding = ItemAnalyticsBinding.bind(holder.itemView)
        val item = data[position]

        binding.ivIconAnalytics.setImageResource(item.icon)
        binding.ivIconAnalytics.imageTintList =
            ContextCompat.getColorStateList(binding.root.context, item.icon_color)

        binding.tvTitleAnalytics.setText(item.title)

        binding.tvAmountAnalytics.text = item.amount.toString()

        
        binding.tvAmountAnalytics.setTextColor(
            when (moneyType) {
                MoneyType.COSTS -> ContextCompat.getColor(context, R.color.red)
                MoneyType.INCOME -> ContextCompat.getColor(context, R.color.light_blue)
            }
        )

    }

    override fun getItemCount(): Int {
        return data.size;
    }

    class AnalyticsListViewHolder(binding: ItemAnalyticsBinding) : RecyclerView.ViewHolder(binding.root)
}