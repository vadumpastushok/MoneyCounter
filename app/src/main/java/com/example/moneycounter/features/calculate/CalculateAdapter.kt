package com.example.moneycounter.features.calculate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.databinding.ItemCurrencyBinding
import com.example.moneycounter.model.entity.db.Currency

class CalculateAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = mutableListOf<Currency>()
    private lateinit var currencyClickedListener: (id: Long) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CurrencyVH(ItemCurrencyBinding.bind(LayoutInflater.from(App.context).inflate(R.layout.item_currency, parent, false)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currencyVH = holder as CurrencyVH

        currencyVH.binding.ivCountryFlag.setImageResource(
            App.context.resources.getIdentifier(
                data[position].flag,
                App.context.getString(R.string.drawable_folder),
                App.context.packageName
            )
        )


        currencyVH.binding.tvCurrency.text = data[position].symbol
        currencyVH.binding.tvCountry.text = data[position].name

        currencyVH.binding.layoutItemCurrency.setOnClickListener { currencyClickedListener(data[position].id) }

        currencyVH.binding.tvFirstRate.isVisible = false
        currencyVH.binding.tvSecondRate.isVisible = false
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData: MutableList<Currency>){
        data = newData
        notifyDataSetChanged()
    }

    fun setCurrencyClickedListener(listener: (id: Long) -> Unit) {
        currencyClickedListener = listener
    }

    class CurrencyVH(val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)
}