package com.example.moneycounter.features.category

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentCategoryBinding
import com.example.moneycounter.features.category_add.CategoryAddFragment
import com.example.moneycounter.features.input_amount.InputAmountFragment
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.ui.MoneyType


class CategoryFragment: BaseFragment<FragmentCategoryBinding>(), CategoryContract {
    private val adapter: CategoryAdapter by lazy { CategoryAdapter(binding.rvCategory) }
    private val args: CategoryFragmentArgs by navArgs()
    private val presenter: CategoryPresenter by lazy { CategoryPresenter() }


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCategoryBinding {
        return FragmentCategoryBinding.inflate(inflater, container, false)
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        setupPager()
        initListeners()
    }

    override fun openHomeFragment(){
        findNavController().popBackStack()
    }

    override fun openAddCategoryFragment(){
        CategoryAddFragment.start(findNavController(), getMoneyType())
    }

    override fun openInputAmountFragment(id: Long){
        InputAmountFragment.start(findNavController(), id, getMoneyType())
    }

    override fun setTitleText(text: String){
        binding.categoryTitlebar.setupTitleText(text)
    }

    override fun setData(list: MutableList<Category>) {
        adapter.setData(list)
    }

    override fun getMoneyType(): MoneyType = args.type


    private fun startAnim(fromValue: Float, toValue: Float){
        ValueAnimator.ofFloat(fromValue, toValue).apply {
            duration = 500
            addUpdateListener {
                binding.categoryEditSave.progress = it.animatedValue as Float
            }
            start()
        }
    }

    override fun setIsEditable(editable: Boolean) {
        CategoryTouchCallback.setIsMovementEnabled(editable)
        adapter.setIsEditable(editable)

        if(editable){
            startAnim(0f, 1f)
        }else{
            startAnim(1f, 0f)
        }
    }

    override fun notifyItemRemoved(position: Int) {
        adapter.notifyItemRemoved(position)
    }
    override fun notifyItemInserted(position: Int) {
        adapter.notifyItemInserted(position)
    }

    /**
     * Help fun-s
     */

    private fun initListeners(){
        binding.categoryTitlebar.setBackButtonClickListener {
            presenter.onBackClicked()
        }

        binding.categoryEditSave.setOnClickListener {
            presenter.onRightClicked()
        }

        adapter.setCategoryClickedListener { order ->
            presenter.onCategorySelected(order)
        }

        adapter.setDeleteCategoryClickedListener { index ->
            presenter.onDeleteCategoryClicked(index)
        }
    }

    private fun setupPager(){
        binding.rvCategory.layoutManager = GridLayoutManager(context,3)
        binding.rvCategory.adapter = adapter


        val callback: ItemTouchHelper.Callback = CategoryTouchCallback(adapter)
        { presenter.onCategoryPositionChanged(adapter.getData()) }
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.rvCategory)
        CategoryTouchCallback.setIsMovementEnabled(false)
    }

    companion object {
        fun start(navController: NavController, moneyType: MoneyType) {
            val direction = NavGraphDirections.actionToCategory(moneyType)
            navController.navigate(direction)
        }
    }
}