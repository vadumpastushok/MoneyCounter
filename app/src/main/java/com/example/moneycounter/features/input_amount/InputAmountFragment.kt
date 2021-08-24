package com.example.moneycounter.features.input_amount

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentInputAmountBinding
import com.example.moneycounter.features.category.CategoryPresenter
import com.example.moneycounter.features.home.HomeFragment
import com.example.moneycounter.model.entity.Category
import com.example.moneycounter.model.entity.MoneyType

class InputAmountFragment: BaseFragment<FragmentInputAmountBinding>(), InputAmountContract {
    private val args: InputAmountFragmentArgs by navArgs()
    private val presenter: InputAmountPresenter by lazy { InputAmountPresenter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentInputAmountBinding {
        return FragmentInputAmountBinding.inflate(inflater, container, false)
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        val item: Category = when(args.type){
            MoneyType.INCOME -> App.incomeCategories.firstOrNull { it.id == args.id }!!
            MoneyType.COSTS -> App.costsCategories.firstOrNull { it.id == args.id }!!
        }
        setup(item)

        initListeners()
        showKeyboard()
    }

    override fun showKeyboard(){
        if(binding.editInputAmount.requestFocus()) {
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }
    }

    override fun hideKeyboard() {
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun openHomeFragment(){
        HomeFragment.start(findNavController())
    }

    override fun openLastFragment(){
        findNavController().popBackStack()
    }

    /**
     * Help fun-s
     */

    private fun setup(item : Category){
        binding.MCToolbar.setupLeftButton(item.color)
        binding.MCToolbar.setupTitleText(item.title)

        binding.imageInputAmount.setIcon(item.icon)
        binding.imageInputAmount.setColor(item.color)

        binding.btnInputAmount.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), item.color)
    }

    private fun initListeners(){
        binding.MCToolbar.setBackButtonClickListener {
            presenter.onBackButtonClicked()
        }

        binding.btnInputAmount.setOnClickListener {
            presenter.onInputAmountButtonClicked()
        }
    }



    companion object{
        fun start(navController : NavController, id : Int, type : MoneyType){
            val direction = NavGraphDirections.actionToInputAmount(id, type)
            navController.navigate(direction)
        }
    }
}