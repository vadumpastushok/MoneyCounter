package com.example.moneycounter.features.category

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.DialogCategoryBinding
import com.example.moneycounter.databinding.FragmentCategoryBinding
import com.example.moneycounter.features.category_add.CategoryAddFragment
import com.example.moneycounter.features.input_amount.InputAmountFragment
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.ui.MoneyType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment: BaseFragment<FragmentCategoryBinding>(), CategoryContract {

    @Inject
    lateinit var presenter: CategoryPresenter
    private val adapter: CategoryAdapter by lazy { CategoryAdapter(binding.rvCategory) }
    private val args: CategoryFragmentArgs by navArgs()

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
        setupDialog()
        setupOnBackPressed()
        initListeners()
    }

    /**
     * Contract
     */

    override fun openLastFragment(){
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

    override fun setMessageNoCategories(){
        binding.tvNoCategories.isVisible = true
    }

    override fun removeMessageNoCategories(){
        binding.tvNoCategories.isVisible = false
    }

    override fun getMoneyType(): MoneyType = args.type

    override fun setIsEditable(editable: Boolean) {
        callback.setIsMovementEnabled(editable)
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

    override fun showDeletingDialog() {
        dialogBinding.tvDialogTitle.text = requireContext().getString(R.string.delete)
        dialogBinding.tvDialogInfo.text = requireContext().getString(R.string.question_to_delete)
        dialogBinding.btnAcceptDialog.text = requireContext().getString(R.string.accept_delete)
        dialogBinding.btnRefuse.text = requireContext().getString(R.string.refuse_delete)
        alertDialog.show()
    }

    override fun showExitDialog() {
        dialogBinding.tvDialogTitle.text = requireContext().getString(R.string.exit)
        dialogBinding.tvDialogInfo.text = requireContext().getString(R.string.question_to_exit)
        dialogBinding.btnAcceptDialog.text = requireContext().getString(R.string.accept_exit)
        dialogBinding.btnRefuse.text = requireContext().getString(R.string.refuse_exit)
        alertDialog.show()
    }

    override fun closeDialog() {
        alertDialog.cancel()
    }

    /**
     * Help fun-s
     */

    private lateinit var alertDialog: AlertDialog
    private lateinit var dialogBinding: DialogCategoryBinding
    private fun setupDialog(){
        val viewGroup: ViewGroup = binding.root
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_category, viewGroup, false)
        dialogBinding = DialogCategoryBinding.bind(dialogView)

        val builder  = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        alertDialog = builder.create()
    }

    private fun startAnim(fromValue: Float, toValue: Float){
        ValueAnimator.ofFloat(fromValue, toValue).apply {
            duration = 500
            addUpdateListener {
                binding.categoryEditSave.progress = it.animatedValue as Float
            }
            start()
        }
    }

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

        dialogBinding.btnAcceptDialog.setOnClickListener {
            presenter.onAcceptDialog()
        }

        dialogBinding.btnRefuse.setOnClickListener {
            presenter.onRefuseDialog()
        }
    }

    private lateinit var callback: CategoryTouchCallback
    private fun setupPager(){
        binding.rvCategory.layoutManager = GridLayoutManager(context,3)
        binding.rvCategory.adapter = adapter

        callback = CategoryTouchCallback(adapter)
        { presenter.onCategoryPositionChanged(adapter.getData()) }
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.rvCategory)
        callback.setIsMovementEnabled(false)
    }

    private fun setupOnBackPressed(){
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    presenter.onBackClicked()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


    companion object {
        fun start(navController: NavController, moneyType: MoneyType) {
            val direction = NavGraphDirections.actionToCategory(moneyType)
            navController.navigate(direction)
        }
    }
}