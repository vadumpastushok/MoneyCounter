package com.example.moneycounter.features.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.databinding.ItemCurrencyBinding
import com.example.moneycounter.databinding.ItemCurrencySeparatorBinding
import com.example.moneycounter.databinding.ItemCurrencyTitleBinding
import com.example.moneycounter.model.entity.db.Currency
import com.example.moneycounter.model.entity.ui.CurrencyType


class CurrencyAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = mutableListOf<Currency>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CurrencyType.Currency.type -> {
                CurrencyVH(
                    ItemCurrencyBinding.bind(
                        LayoutInflater.from(App.context).inflate(R.layout.item_currency, parent, false)
                    )
                )
            }
            CurrencyType.CurrencyTitle.type -> {
                CurrencyTitleVH(
                    ItemCurrencyTitleBinding.bind(
                        LayoutInflater.from(App.context).inflate(R.layout.item_currency_title, parent, false)
                    )
                )

            }
            else -> {
                CurrencySeparatorVH(
                    ItemCurrencySeparatorBinding.bind(
                        LayoutInflater.from(App.context).inflate(R.layout.item_currency_separator, parent, false)
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == CurrencyType.Currency.type){
            val currencyVH = holder as CurrencyVH

            currencyVH.binding.ivCountryFlag.setImageResource(
                App.context.resources.getIdentifier(
                    data[position].flag,
                    App.context.getString(R.string.drawable_folder),
                    App.context.packageName
                )
            )

            if(data[position].symbol == App.context.getString(R.string.PLN))currencyVH.binding.layoutItemCurrency.id = -1

            currencyVH.binding.tvCurrency.text = data[position].symbol
            currencyVH.binding.tvCountry.text = data[position].name


            if (data[position].firstRate == data[position].secondRate) {
                currencyVH.binding.tvFirstRate.isVisible = false
            } else {
                currencyVH.binding.tvFirstRate.isVisible = true
                currencyVH.binding.tvFirstRate.text = data[position].firstRate
            }
            currencyVH.binding.tvSecondRate.text = data[position].secondRate
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position].type) {
            CurrencyType.CurrencyTitle -> {
                CurrencyType.CurrencyTitle.type
            }
            CurrencyType.CurrencySeparator -> {
                CurrencyType.CurrencySeparator.type
            }
            else -> {
                CurrencyType.Currency.type
            }
        }
    }

    fun setData(newData: MutableList<Currency>){
        data = newData
        notifyDataSetChanged()
    }

    class CurrencyVH(val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

    class CurrencyTitleVH(val binding: ItemCurrencyTitleBinding) : RecyclerView.ViewHolder(binding.root)

    class CurrencySeparatorVH(val binding: ItemCurrencySeparatorBinding) : RecyclerView.ViewHolder(binding.root)
}