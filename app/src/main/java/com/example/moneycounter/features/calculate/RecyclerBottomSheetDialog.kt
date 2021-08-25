package com.example.moneycounter.features.calculate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.databinding.DialogCurrencyChooseBinding
import com.example.moneycounter.model.entity.db.Currency
import com.example.moneycounter.model.entity.ui.CurrencyType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mynameismidori.currencypicker.ExtendedCurrency
import java.util.*

class RecyclerBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogCurrencyChooseBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_currency_choose, container, false)
        binding = DialogCurrencyChooseBinding.bind(view)
        return view
    }

    private val adapter by lazy { CalculateAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvCurrencyChoose.adapter = adapter
        binding.rvCurrencyChoose.layoutManager = LinearLayoutManager(requireContext())
    }

    fun setData(data: MutableList<Currency>){
        data.removeIf { it.type != CurrencyType.Currency }
        val currency = java.util.Currency.getAvailableCurrencies().find { it.currencyCode == App.context.getString(R.string.UAH) }

        if(currency == null){
            adapter.setData(data)
            return
        }

        val newData = mutableListOf(
            Currency(
                currency.getDisplayName(Locale(App.context.getString(R.string.ru))),
                currency.currencyCode.toString(),
                App.context.resources.getResourceEntryName(ExtendedCurrency.getCurrencyByISO(currency.currencyCode).flag),
                "",
                "",
                CurrencyType.Currency,
                0L
            )
        )

        newData += data
        adapter.setData(newData)
    }

    fun setOnItemSelectedListener(listener: (id: Long) -> Unit){
        adapter.setCurrencyClickedListener { listener(it) }
    }



}