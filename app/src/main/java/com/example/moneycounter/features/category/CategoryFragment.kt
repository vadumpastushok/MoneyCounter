package com.example.moneycounter.features.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentCategoryBinding
import com.example.moneycounter.features.category_add.CategoryAddFragment
import com.example.moneycounter.features.input_amount.InputAmountFragment
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.ui.MoneyType


class CategoryFragment: BaseFragment<FragmentCategoryBinding>(), CategoryContract {
    private val adapter: CategoryAdapter by lazy { CategoryAdapter() }
    private val args: CategoryFragmentArgs by navArgs()
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

    override fun openLastFragment(){
        findNavController().popBackStack()
    }

    override fun openAddCategoryFragment(){
        CategoryAddFragment.start(findNavController(), getMoneyType())
    }

    override fun openInputAmountFragment(id: Long){
        InputAmountFragment.start(findNavController(), id, getMoneyType())
    }

    override fun setTitleText(@StringRes text: Int){
        binding.categoryTitlebar.setupTitleText(text)
    }

    override fun setData(list: MutableList<Category>) {
        adapter.setData(list)
    }

    override fun getMoneyType(): MoneyType = args.type

    /**
     * Help fun-s
     */

    private fun initListeners(){
        binding.categoryTitlebar.setBackButtonClickListener {
            presenter.onBackClicked()
        }
        adapter.setCategoryClickedListener { order ->
            presenter.onCategorySelected(order)
        }
    }

    private fun setupPager(){
        binding.rvCategory.layoutManager = GridLayoutManager(context,3)
        binding.rvCategory.adapter = adapter

        val callback: ItemTouchHelper.Callback = CategoryTouchCallback(adapter)
        { presenter.onCategoryPositionChanged(adapter.getData()) }
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.rvCategory)
    }

    companion object {
        fun start(navController: NavController, moneyType: MoneyType) {
            val direction = NavGraphDirections.actionToCategory(moneyType)
            navController.navigate(direction)
        }
    }
}