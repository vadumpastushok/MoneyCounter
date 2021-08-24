package com.example.moneycounter.features.category_add

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
import com.example.moneycounter.features.home.HomeFragment
import com.example.moneycounter.features.icon_picker.IconPickerFragment
import com.example.moneycounter.model.entity.ui.MoneyType
import com.skydoves.colorpickerview.listeners.ColorListener


class CategoryAddFragment: BaseFragment<FragmentCategoryAddBinding>(), CategoryAddContract {
    private val args: CategoryAddFragmentArgs by navArgs()
    private val presenter by lazy { CategoryAddPresenter() }
    private lateinit var alertDialog: AlertDialog
    private lateinit var dialogBinding: DialogCategoryAddBinding

    private var moneyType: MoneyType = MoneyType.INCOME
    private var icon: String = ""
    private var color: Int = 0

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCategoryAddBinding {
        return FragmentCategoryAddBinding.bind(inflater.inflate(R.layout.fragment_category_add, container, false))
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
        binding.categoryAddTitlebar.setupBottomLine(color)
        binding.categoryAddTitlebar.setupLeftButton(color)
        binding.btnIconCategoryAdd.setColor(color)
        binding.btnColorPick.setTextColor(color)
        binding.editCategoryTitle.backgroundTintList = ColorStateList.valueOf(color)
        setButtonEnabled(null)
    }

    override fun setInDialogColor(dialogColor: Int){
        dialogBinding.categoryViewDialog.setColor(dialogColor)
        dialogBinding.btnDialog.backgroundTintList = ColorStateList.valueOf(dialogColor)
    }

    override fun showDialog(){
        dialogBinding.categoryViewDialog.setIcon(icon)
        dialogBinding.categoryViewDialog.setTitle(binding.editCategoryTitle.text.toString())
        alertDialog.show()
    }

    override fun closeDialog(){
        alertDialog.dismiss()
    }

    override fun getTitle(): String {
        return binding.editCategoryTitle.text.toString()
    }

    override fun getColor(): Int = color

    override fun getIcon(): String = icon

    override fun getMoneyType(): MoneyType = moneyType

    override fun setIcon(selectedIcon: String){
        binding.btnIconCategoryAdd.setIcon(icon)
    }

    override fun setButtonEnabled(isEnabled: Boolean?) {
        if (isEnabled != null) binding.btnCategoryAdd.isEnabled = isEnabled
        if (binding.btnCategoryAdd.isEnabled) {
            binding.btnCategoryAdd.backgroundTintList = ColorStateList.valueOf(color)
        } else {
            binding.btnCategoryAdd.backgroundTintList = ColorStateList.valueOf(
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
        binding.categoryAddTitlebar.setupTitleText(args.title)
        setColor(color)
    }

    private fun setupData(){
        moneyType = args.type
        icon = args.icon
        if(color == 0){
            color = requireContext().getColor(R.color.red)
        }
    }

    private fun setupTransferData(){
        parentFragmentManager.setFragmentResultListener(Config.REQUEST_KEY_CATEGORY_ADD, this,
            { _, bundle ->
                val moneyTypeResult = bundle.getString(requireContext().getString(R.string.header_money_type))
                moneyType = if(moneyTypeResult == MoneyType.INCOME.toString()) {
                    MoneyType.INCOME
                }else{
                    MoneyType.COSTS
                }
                val iconResult = bundle.getString(requireContext().getString(R.string.header_icon))
                iconResult?.let {
                    icon = iconResult
                    presenter.onIconSelected(iconResult)
                }
                setColor(color)
            }
        )
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
        binding.categoryAddTitlebar.setBackButtonClickListener {
            presenter.onBackClicked()
        }

        binding.btnIconCategoryAdd.setOnClickListener {
            presenter.onIconPickerClicked()
        }

        binding.btnColorPick.setOnClickListener {
            presenter.onSelectColorClicked()
        }
        binding.btnCategoryAdd.setOnClickListener {
            presenter.onCreateCategory()
        }
        binding.editCategoryTitle.addTextChangedListener(object : TextWatcher {
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
            moneyType: MoneyType,
            icon: String = App.context.getString(R.string.default_icon),
            title: String = App.context.getString(R.string.title_create_category)
        ) {
            val direction = NavGraphDirections.actionToCategoryAdd(moneyType, icon, title)
            navController.navigate(direction)
        }
    }
}