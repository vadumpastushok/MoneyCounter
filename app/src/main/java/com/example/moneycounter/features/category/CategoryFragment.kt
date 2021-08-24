package com.example.moneycounter.features.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentCategoryBinding
import com.example.moneycounter.features.home.HomePresenter
import com.example.moneycounter.features.input_amount.InputAmountFragment
import com.example.moneycounter.model.entity.MoneyType


class CategoryFragment: BaseFragment<FragmentCategoryBinding>(), CategoryContract {
    private val adapter: CategoryAdapter by lazy { CategoryAdapter() }
    val args: CategoryFragmentArgs by navArgs()
    private val presenter: CategoryPresenter by lazy { CategoryPresenter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCategoryBinding {
        return FragmentCategoryBinding.inflate(inflater, container, false)
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        setupPager()
        initListeners()
    }

    override fun getMoneyType(): MoneyType = args.type

    override fun openLastFragment(){
        findNavController().popBackStack()
    }

    override fun openInputAmountFragment(id: Int){
        InputAmountFragment.start(findNavController(), id, args.type)
    }


    /**
     * Help fun-s
     */

    override fun setupIncome(){
        binding.categoryTitlebar.setupTitleText(R.string.title_income)
        adapter.setData(App.incomeCategories)
    }

    override fun setupCosts(){
        binding.categoryTitlebar.setupTitleText(R.string.title_costs)
        adapter.setData(App.costsCategories)
    }

    private fun initListeners(){
        binding.categoryTitlebar.setBackButtonClickListener {
            presenter.onCancel()
        }
        adapter.setCategoryListener { position ->
            val item = adapter.getData().getOrNull(position) ?: return@setCategoryListener
            presenter.onCategorySelected(item.id)
        }
    }

    override fun setupPager(){
        binding.rvCategory.layoutManager = GridLayoutManager(context,3)
        binding.rvCategory.adapter = adapter
    }

    companion object {
        fun start(navController: NavController, moneyType: MoneyType) {
            val direction = NavGraphDirections.actionToCategory(moneyType)
            navController.navigate(direction)
        }
    }
}