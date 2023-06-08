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
import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentIconPickerBinding
import com.example.moneycounter.model.entity.ui.MoneyType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IconPickerFragment: BaseFragment<FragmentIconPickerBinding>(), IconPickerContract {

    @Inject
    lateinit var presenter: IconPickerPresenter
    private val args: IconPickerFragmentArgs by navArgs()
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
        setupToolbar()
        setupPager()
        initListeners()
    }

    /**
     * Contract
     */

    override fun openCategoryAddFragment(icon: String) {
        val bundle = Bundle()
        bundle.putString(requireContext().getString(R.string.header_money_type), args.type.toString())
        bundle.putString(requireContext().getString(R.string.header_icon), icon)
        parentFragmentManager.setFragmentResult(Config.REQUEST_KEY_ICON_SELECT, bundle)

        findNavController().popBackStack()
    }

    override fun openLastFragment() {
        findNavController().popBackStack()
    }

    override fun setData(data: MutableList<String>, color: Int){
        adapter.setData(data, color)
    }

    override fun getColor(): Int = args.color

    /**
     * Help fun-s
     */

    private fun initListeners(){
        binding.toolbarIconPicker.setBackButtonClickListener {
            presenter.onBackClicked()
        }

        adapter.setCategoryClickedListener {
            presenter.onIconSelected(it)
        }
    }

    private fun setupToolbar(){
        binding.toolbarIconPicker.setupLeftButton(getColor())
        binding.toolbarIconPicker.setupBottomLine(getColor())
    }

    private fun setupPager(){
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