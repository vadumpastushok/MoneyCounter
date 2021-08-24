package com.example.moneycounter.features.category_add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentCategoryAddBinding
import com.example.moneycounter.features.category.CategoryFragmentArgs
import com.example.moneycounter.model.entity.ui.MoneyType

class CategoryAddFragment: BaseFragment<FragmentCategoryAddBinding>(), CategoryAddContract {
    private val args: CategoryFragmentArgs by navArgs()
    private val presenter by lazy { CategoryAddPresenter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCategoryAddBinding {
        return FragmentCategoryAddBinding.bind(inflater.inflate(R.layout.fragment_category_add, container, false))
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }





    private fun getMoneyType(): MoneyType = args.type

    companion object{
        fun start( navController: NavController, moneyType: MoneyType) {
            val direction = NavGraphDirections.actionToCategoryAdd(moneyType)
            navController.navigate(direction)
        }
    }


}