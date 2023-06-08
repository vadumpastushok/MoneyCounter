package com.example.moneycounter.features.financial_place_add

import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.DialogCategoryAddBinding
import com.example.moneycounter.databinding.FragmentCategoryAddBinding
import com.example.moneycounter.databinding.FragmentFinancialPlaceAddBinding
import com.example.moneycounter.features.category_add.CategoryAddContract
import com.example.moneycounter.features.category_add.CategoryAddFragmentArgs
import com.example.moneycounter.features.home.HomeFragment
import com.example.moneycounter.features.icon_picker.IconPickerFragment
import com.example.moneycounter.model.entity.ui.MoneyType
import com.skydoves.colorpickerview.listeners.ColorListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FinancialPlaceAddFragment: BaseFragment<FragmentFinancialPlaceAddBinding>(), FinancialPlaceAddContract {

    @Inject
    lateinit var presenter: FinancialPlaceAddPresenter
    private val args: FinancialPlaceAddFragmentArgs by navArgs()
    private lateinit var alertDialog: AlertDialog
    private lateinit var dialogBinding: DialogCategoryAddBinding

    private var moneyType: MoneyType = MoneyType.INCOME
    private var icon: String = ""
    private var color: Int = 0

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFinancialPlaceAddBinding {
        return FragmentFinancialPlaceAddBinding.inflate(inflater, container, false)
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        setupData()
        setupTransferData()
        setupDialog()
        initListeners()
        setupView()
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }

    /**
     * Contract
     */

    override fun openLastFragment(){
        findNavController().popBackStack()
    }

    override fun openHomeFragment(){
        HomeFragment.start(findNavController())
    }

    override fun openIconPickerFragment(){
        IconPickerFragment.start(findNavController(), getMoneyType(), color)
    }

    override fun hideKeyboard() {
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun setColor(color: Int){
        this.color = color
        binding.financialPlaceAddTitlebar.setupBottomLine(color)
        binding.financialPlaceAddTitlebar.setupLeftButton(color)
        binding.btnIconFinancialPlaceAdd.setColor(color)
        binding.btnColorPick.setTextColor(color)
        binding.editFinancialPlaceTitle.backgroundTintList = ColorStateList.valueOf(color)
        setButtonEnabled(null)
    }

    override fun setInDialogColor(dialogColor: Int){
        dialogBinding.categoryViewDialog.setColor(dialogColor)
        dialogBinding.btnDialog.backgroundTintList = ColorStateList.valueOf(dialogColor)
    }

    override fun showDialog(){
        dialogBinding.categoryViewDialog.setIcon(icon)
        dialogBinding.categoryViewDialog.setTitle(binding.editFinancialPlaceTitle.text.toString())
        alertDialog.show()
    }

    override fun closeDialog(){
        alertDialog.dismiss()
    }

    override fun getTitle(): String {
        return binding.editFinancialPlaceTitle.text.toString()
    }

    override fun getColor(): Int = color

    override fun getIcon(): String = icon

    override fun getMoneyType(): MoneyType = moneyType

    override fun setIcon(selectedIcon: String){
        binding.btnIconFinancialPlaceAdd.setIcon(icon)
    }

    override fun setButtonEnabled(isEnabled: Boolean?) {
        if (isEnabled != null) binding.btnFinancialPlaceAdd.isEnabled = isEnabled
        if (binding.btnFinancialPlaceAdd.isEnabled) {
            binding.btnFinancialPlaceAdd.backgroundTintList = ColorStateList.valueOf(color)
        } else {
            binding.btnFinancialPlaceAdd.backgroundTintList = ColorStateList.valueOf(
                ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.gray,
                    null
                )
            )
        }
    }

    /**
     * Help fun-s
     */

    private fun setupView(){
        setButtonEnabled(false)
        binding.financialPlaceAddTitlebar.setupTitleText(args.title)
        setColor(color)
    }

    private fun setupData(){
        icon = args.icon
        if(color == 0){
            color = requireContext().getColor(R.color.red)
        }
    }

    private fun setupTransferData(){
        parentFragmentManager.setFragmentResultListener(Config.REQUEST_KEY_ICON_SELECT, this
        ) { _, bundle ->
            val moneyTypeResult =
                bundle.getString(requireContext().getString(R.string.header_money_type))
            moneyType = if (moneyTypeResult == MoneyType.INCOME.toString()) {
                MoneyType.INCOME
            } else {
                MoneyType.COSTS
            }
            val iconResult = bundle.getString(requireContext().getString(R.string.header_icon))
            iconResult?.let {
                icon = iconResult
                presenter.onIconSelected(iconResult)
            }
            setColor(color)
        }
    }

    private fun setupDialog(){
        val viewGroup: ViewGroup = binding.root
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_category_add, viewGroup, false)
        dialogBinding = DialogCategoryAddBinding.bind(dialogView)

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        alertDialog = builder.create()

        dialogBinding.colorPickerDialog.setInitialColor(color)
    }

    private fun initListeners() {
        binding.financialPlaceAddTitlebar.setBackButtonClickListener {
            presenter.onBackClicked()
        }

        binding.btnIconFinancialPlaceAdd.setOnClickListener {
            presenter.onIconPickerClicked()
        }

        binding.btnColorPick.setOnClickListener {
            presenter.onSelectColorClicked()
        }
        binding.btnFinancialPlaceAdd.setOnClickListener {
            presenter.onCreateFinancePlace()
        }
        binding.editFinancialPlaceTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable?) {
                presenter.onTextChanged(editable.toString())
            }
        })
        dialogBinding.colorPickerDialog.setColorListener(object : ColorListener {
            override fun onColorSelected(color: Int, fromUser: Boolean) {
                presenter.onColorSelected(color)
            }
        })
        dialogBinding.btnDialog.setOnClickListener {
            presenter.onApplyColor(dialogBinding.colorPickerDialog.color)
        }
    }


    companion object{
        fun start(
            navController: NavController,
            icon: String = App.context.getString(R.string.default_icon),
            title: String = App.context.getString(R.string.title_create_category)
        ) {
            val direction = NavGraphDirections.actionToFinancialPlaceAdd(icon, title)
            navController.navigate(direction)
        }
    }
}