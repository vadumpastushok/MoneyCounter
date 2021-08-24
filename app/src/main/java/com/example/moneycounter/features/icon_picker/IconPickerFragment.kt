package com.example.moneycounter.features.icon_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentIconPickerBinding
import com.example.moneycounter.model.entity.ui.MoneyType


class IconPickerFragment: BaseFragment<FragmentIconPickerBinding>(), IconPickerContract {

    private val args: IconPickerFragmentArgs by navArgs()
    private val presenter: IconPickerPresenter by lazy { IconPickerPresenter() }
    private val adapter: IconPickerAdapter by lazy { IconPickerAdapter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIconPickerBinding {
        return FragmentIconPickerBinding.bind(inflater.inflate(R.layout.fragment_icon_picker, container, false))
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        setupPager()
        initListeners()
    }


    override fun openCategoryAddFragment(icon: String) {
        val bundle = Bundle()
        bundle.putString("moneyType", getMoneyType().toString())
        bundle.putString("icon", icon)
        parentFragmentManager.setFragmentResult("requestKey", bundle)

        findNavController().popBackStack()
    }

    override fun openLastFragment() {
        findNavController().popBackStack()
    }

    override fun setData(data: MutableList<String>, color: Int){
        adapter.setData(data, color)
    }


    private fun getMoneyType(): MoneyType = args.type

    override fun getColor(): Int = args.color

    private fun initListeners(){
        binding.toolbarIconPicker.setBackButtonClickListener {
            presenter.onBackClicked()
        }

        adapter.setCategoryClickedListener {
            presenter.onIconSelected(it)
        }
    }

    private fun setupPager(){
        binding.toolbarIconPicker.setupLeftButton(getColor())
        binding.rvIconPicker.layoutManager = GridLayoutManager(context,3)
        binding.rvIconPicker.adapter = adapter
    }



    companion object{
        fun start(navController: NavController, moneyType: MoneyType, color: Int){
            val direction = NavGraphDirections.actionToIconPicker(moneyType, color)
            navController.navigate(direction)
        }
    }
}