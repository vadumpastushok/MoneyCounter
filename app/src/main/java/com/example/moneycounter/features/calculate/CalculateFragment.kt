package com.example.moneycounter.features.calculate


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentCalculateBinding
import com.example.moneycounter.model.entity.db.Currency
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalculateFragment: BaseFragment<FragmentCalculateBinding>(), CalculateContract {

    @Inject
    lateinit var presenter: CalculatePresenter
    private val args : CalculateFragmentArgs by navArgs()
    private lateinit var dialogCalculate: CalculateBottomSheetDialog
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
        dialogCalculate = CalculateBottomSheetDialog()
        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onFragmentDestroyed()
    }

    /**
     * Contract
     */

    override fun getCurrencyId(): Long = args.currencyId

    override fun hideKeyboard() {
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun openLastFragment() {
        findNavController().popBackStack()
    }

    override fun setData(data: MutableList<Currency>) {
        dialogCalculate.setData(data)
    }

    override fun showMessage(message: String) {
        binding.tvMessageCalculate.text = message
        binding.tvMessageCalculate.isVisible = true
    }

    override fun showDialog() {
       dialogCalculate.show(parentFragmentManager, "1")
    }

    override fun closeDialog(){
        dialogCalculate.dismiss()
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

    override fun setFirstCurrencyValue(value: String){
        ignoreChanges = true
        binding.editFirstCurrency.setText(value)
        ignoreChanges = false
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

    override fun setSecondCurrencyValue(value: String){
        ignoreChanges = true
        binding.editSecondCurrency.setText(value)
        ignoreChanges = false
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
        dialogCalculate.setOnItemSelectedListener {
            presenter.onCurrencyChosen(it)
        }
        binding.editFirstCurrency.doAfterTextChanged {
            if(!ignoreChanges) {
                presenter.onFirstCurrencyValueChanged(it.toString())
            }
        }
        binding.editSecondCurrency.doAfterTextChanged {
            if(!ignoreChanges) {
                presenter.onSecondCurrencyValueChanged(it.toString())
            }
        }

    }

    companion object{
        fun start(navController: NavController, currencyId: Long = 2){
            val direction = NavGraphDirections.actionToCalculate(currencyId)
            navController.navigate(direction)
        }
    }

}