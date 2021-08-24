package com.example.moneycounter.features.category_add

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.app.App
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
        setupView()
        setupDialog()
        initListeners()
    }



    override fun openLastFragment(){
        HomeFragment.start(findNavController())
    }
    override fun openIconPickerFragment(){
        IconPickerFragment.start(findNavController(), getMoneyType(), color)
    }





    override fun setColor(color: Int){
        this.color = color
        binding.categoryAddTitlebar.setupBottomLine(color)
        binding.categoryAddTitlebar.setupLeftButton(color)
        binding.btnIconCategoryAdd.setColor(color)
        binding.btnColorPick.setTextColor(color)
        binding.btnCategoryAdd.backgroundTintList =
            ColorStateList.valueOf(color)
    }

    override fun setInDialogColor(newColor: Int){
        color = newColor
        dialogBinding.categoryViewDialog.setColor(color)
        dialogBinding.btnDialog.backgroundTintList = ColorStateList.valueOf(color)
    }


    override fun showDialog(){
        dialogBinding.categoryViewDialog.setIcon(icon)
        dialogBinding.categoryViewDialog.setTitle(binding.editCategoryName.text.toString())
        alertDialog.show()
    }

    override fun closeDialog(){
        alertDialog.dismiss()
    }


    override fun getTitle(): String {
        return binding.editCategoryName.text.toString()
    }

    override fun getColor(): Int = color

    override fun getIcon(): String = icon

    override fun getMoneyType(): MoneyType = moneyType





    private fun setupData(){
        moneyType = args.type
        icon = args.icon
        if(color == 0){
            color = requireContext().getColor(R.color.red)
        }
    }

    private fun setupTransferData(){
        parentFragmentManager.setFragmentResultListener("requestKey", this,
            { _, bundle ->
                val moneyTypeResult = bundle.getString("moneyType")
                moneyType = if(moneyTypeResult == MoneyType.INCOME.toString()) {
                    MoneyType.INCOME
                }else{
                    MoneyType.COSTS
                }

                val iconResult = bundle.getString("icon")
                iconResult?.let {
                    icon = iconResult
                }

                setupView()

            }
        )
    }

    private fun setupView(){
        binding.btnIconCategoryAdd.setIcon(icon)
        binding.categoryAddTitlebar.setupTitleText(args.title)
        binding.categoryAddTitlebar.setupLeftButton(color)
        binding.btnIconCategoryAdd.setColor(color)
        binding.btnColorPick.setTextColor(color)
        binding.btnCategoryAdd.backgroundTintList =
            ColorStateList.valueOf(color)
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
        dialogBinding.colorPickerDialog.setColorListener(object : ColorListener {
            override fun onColorSelected(color: Int, fromUser: Boolean) {
                presenter.onColorSelected(color)
            }
        })
        dialogBinding.btnDialog.setOnClickListener {
            presenter.onApplyColor(color)
        }
    }




    companion object{
        fun start( navController: NavController, moneyType: MoneyType, icon: String = "icon_add", title: String = App.context.getString(R.string.title_create_category)) {
            val direction = NavGraphDirections.actionToCategoryAdd(moneyType, icon, title)
            navController.navigate(direction)
        }
    }


}