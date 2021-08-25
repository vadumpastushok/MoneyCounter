package com.example.moneycounter.features.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentCurrencyBinding
import com.example.moneycounter.features.calculate.CalculateFragment
import com.example.moneycounter.model.entity.db.Currency
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CurrencyFragment: BaseFragment<FragmentCurrencyBinding>(), CurrencyContract {

    @Inject
    lateinit var presenter: CurrencyPresenter
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCurrencyBinding {
        return FragmentCurrencyBinding.bind(inflater.inflate(R.layout.fragment_currency, container, false))
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onFragmentDestroyed()
    }

    /**
     * Contract
     */



    override fun openLastFragment() {
        findNavController().popBackStack()
    }

    override fun openCalculateFragment(id: Long) {
        CalculateFragment.start(findNavController(), id)
    }

    override fun setupRecycleView(data: MutableList<Currency>){
        val adapter = CurrencyAdapter()
        adapter.setData(data)
        adapter.setOnCurrencyClicked { presenter.onCurrencyClicked(it) }
        binding.rvCurrency.adapter = adapter
        binding.rvCurrency.layoutManager = LinearLayoutManager(requireContext())

        val dividerItemDecoration = com.example.moneycounter.ui.adapter.
        DividerItemDecoration(binding.rvCurrency.context, 72, 0, 0, -1)
        binding.rvCurrency.addItemDecoration(dividerItemDecoration)
    }


    override fun getRecycleView() : RecyclerView{
        return binding.rvCurrency
    }

    override fun showMessage(message: String){
        binding.tvMessageCurrencies.text = message
        binding.tvMessageCurrencies.isVisible = true
    }

    /**
     * Help fun-s
     */

    private fun initListeners(){
        binding.toolbarCurrency.setBackButtonClickListener {
            presenter.onBackClicked()
        }
    }

    companion object{
        fun start(navController: NavController){
            val direction = NavGraphDirections.actionToCurrency()
            navController.navigate(direction)
        }
    }
}