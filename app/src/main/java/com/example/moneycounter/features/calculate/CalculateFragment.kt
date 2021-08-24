package com.example.moneycounter.features.calculate


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentCalculateBinding
import com.example.moneycounter.model.entity.db.Currency


class CalculateFragment: BaseFragment<FragmentCalculateBinding>(), CalculateContract {
    private val presenter by lazy { CalculatePresenter() }
    private lateinit var dialog: BottomSheetDialog
    private var ignoreChanges = false

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCalculateBinding {
        return FragmentCalculateBinding.bind(inflater.inflate(R.layout.fragment_calculate, container, false))
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        dialog = BottomSheetDialog()
        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onFragmentDestroyed()
    }

    /**
     * Contract
     */

    override fun hideKeyboard() {
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun openLastFragment() {
        findNavController().popBackStack()
    }

    override fun setupRecycleView(data: MutableList<Currency>) {
        dialog.setData(data)
    }

    override fun showMessage(message: String) {
        binding.tvMessageCalculate.text = message
        binding.tvMessageCalculate.isVisible = true
    }

    override fun showDialog() {
       dialog.show(parentFragmentManager, "1")
    }

    override fun closeDialog(){
        dialog.dismiss()
    }

    override fun setCurrencyValue(isFirstCurrency: Boolean, value: String) {
        ignoreChanges = true
        if(isFirstCurrency){
            binding.editFirstCurrency.setText(value)
        }else{
            binding.editSecondCurrency.setText(value)
        }
        ignoreChanges = false
    }

    override fun setupFirstCurrency(flag: String, symbol: String) {
        binding.ivFirstCurrency.setImageResource(
            App.context.resources.getIdentifier(
                flag,
                App.context.getString(R.string.drawable_folder),
                App.context.packageName
            )
        )
        binding.tvFirstCurrency.text = symbol
    }

    override fun setupSecondCurrency(flag: String, symbol: String) {
        binding.ivSecondCurrency.setImageResource(
            App.context.resources.getIdentifier(
                flag,
                App.context.getString(R.string.drawable_folder),
                App.context.packageName
            )
        )
        binding.tvSecondCurrency.text = symbol
    }

    /**
     * Help fun-s
     */

    private fun initListeners(){
        binding.toolbarCalculate.setBackButtonClickListener {
            presenter.onBackClicked()
        }
        binding.layoutFirstCurrency.setOnClickListener {
            presenter.onFirstCurrencyClicked()
        }
        binding.layoutSecondCurrency.setOnClickListener {
            presenter.onSecondCurrencyClicked()
        }
        dialog.setOnItemSelectedListener {
            presenter.onCurrencyChosen(it)
        }
        binding.editFirstCurrency.doAfterTextChanged {
            if(!ignoreChanges) {
                presenter.onValueChanged(true, it.toString())
            }
        }
        binding.editSecondCurrency.doAfterTextChanged {
            if(!ignoreChanges) {
                presenter.onValueChanged(false, it.toString())
            }
        }

    }

    companion object{
        fun start(navController: NavController){
            val direction = NavGraphDirections.actionToCalculate()
            navController.navigate(direction)
        }
    }

}