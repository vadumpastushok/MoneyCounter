package com.example.moneycounter.features.financial_place

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.DialogCategoryBinding
import com.example.moneycounter.databinding.FragmentFinancialPlaceBinding
import com.example.moneycounter.features.category_add.CategoryAddFragment
import com.example.moneycounter.features.financial_place_add.FinancialPlaceAddFragment
import com.example.moneycounter.features.input_amount.InputAmountFragment
import com.example.moneycounter.features.input_amount.InputAmountFragmentArgs
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.db.FinancialPlace
import com.example.moneycounter.model.entity.ui.MoneyType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FinancialPlaceFragment: BaseFragment<FragmentFinancialPlaceBinding>(), FinancialPlaceContract {

    @Inject
    lateinit var presenter: FinancialPlacePresenter
    private val adapter: FinancialPlaceAdapter by lazy { FinancialPlaceAdapter() }
    private val args: FinancialPlaceFragmentArgs by navArgs()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFinancialPlaceBinding {
        return FragmentFinancialPlaceBinding.inflate(inflater, container, false)
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        setupRecycler()
        setupDialog()
        setupOnBackPressed()
        initListeners()
    }

    override fun setData(list: MutableList<FinancialPlace>) {
        adapter.setData(list)
    }

    override fun setMessageNoCategories(){
        binding.tvNoFinancialPlaces.isVisible = true
    }

    override fun removeMessageNoCategories(){
        binding.tvNoFinancialPlaces.isVisible = false
    }

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

    override fun openLastFragment() {
        findNavController().popBackStack()
    }

    override fun openAddFinancePlaceFragment(){
        FinancialPlaceAddFragment.start(findNavController())
    }

    override fun openInputAmountFragment(financePlaceId: Long) {
        InputAmountFragment.start(findNavController(), args.id, args.type, financePlaceId)
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
                binding.financialPlaceEditSave.progress = it.animatedValue as Float
            }
            start()
        }
    }

    private fun initListeners(){
        binding.financialPlacesTitlebar.setBackButtonClickListener {
            presenter.onBackClicked()
        }

        binding.financialPlaceEditSave.setOnClickListener {
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

    private lateinit var callback: FinancialPlaceTouchCallback
    private fun setupRecycler(){
        binding.rvFinancialPlaces.layoutManager = GridLayoutManager(context,3)
        binding.rvFinancialPlaces.adapter = adapter

        callback = FinancialPlaceTouchCallback(adapter)
        { presenter.onCategoryPositionChanged(adapter.getData()) }
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.rvFinancialPlaces)
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


    companion object{
        fun start(navController: NavController, id: Long, type: MoneyType){
            val direction = NavGraphDirections.actionToFinancialPlace(id, type)
            navController.navigate(direction)
        }
    }
}