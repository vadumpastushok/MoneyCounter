package com.example.moneycounter.features.input_amount

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentInputAmountBinding
import com.example.moneycounter.features.home.HomeFragment
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.ui.MoneyType

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
        initListeners()
        showKeyboard()
    }

    /**
     * Contract
     */

    override fun getFragmentArgs(): InputAmountFragmentArgs = args

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

    override fun setupView(item : Category){
        binding.MCToolbar.setupLeftButton(item.color)
        binding.MCToolbar.setupTitleText(item.title)
        binding.MCToolbar.setupBottomLine(item.color)

        binding.imageInputAmount.setIcon(item.icon)
        binding.imageInputAmount.setColor(item.color)

        binding.btnInputAmount.backgroundTintList =
            ColorStateList.valueOf(item.color)
    }

    override fun getAmount(): String {
        return binding.editInputAmount.text.toString()
    }

    /**
     * Help fun-s
     */

    private fun initListeners(){
        binding.MCToolbar.setBackButtonClickListener {
            presenter.onBackButtonClicked()
        }

        binding.btnInputAmount.setOnClickListener {
            presenter.onInputAmountButtonClicked()
        }
    }

    companion object{
        fun start(navController : NavController, id : Long, type : MoneyType){
            val direction = NavGraphDirections.actionToInputAmount(id, type)
            navController.navigate(direction)
        }
    }
}