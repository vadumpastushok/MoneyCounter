package com.example.moneycounter.features.financial_place

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentFinancialPlaceBinding
import com.example.moneycounter.features.input_amount.InputAmountFragment
import com.example.moneycounter.features.input_amount.InputAmountFragmentArgs
import com.example.moneycounter.model.entity.ui.MoneyType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FinancialPlaceFragment: BaseFragment<FragmentFinancialPlaceBinding>(), FinancialPlaceContract {

    @Inject
    lateinit var presenter: FinancialPlacePresenter
    private val args: InputAmountFragmentArgs by navArgs()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFinancialPlaceBinding {
        return FragmentFinancialPlaceBinding.inflate(inflater, container, false)
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        lifecycleScope.launch {
            delay(3000L)
            InputAmountFragment.start(findNavController(), args.id, args.type)
        }
    }

    companion object{
        fun start(navController: NavController, id: Long, type: MoneyType){
            val direction = NavGraphDirections.actionToFinancialPlace(id, type)
            navController.navigate(direction)
        }
    }
}