package com.example.moneycounter.features.set_password

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentSetPasswordBinding
import com.example.moneycounter.ui.custom.NumberButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetPasswordFragment: BaseFragment<FragmentSetPasswordBinding>(), SetPasswordContract {

    @Inject
    lateinit var presenter: SetPasswordPresenter
    private val args: SetPasswordFragmentArgs by navArgs()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSetPasswordBinding {
        return FragmentSetPasswordBinding.bind(inflater.inflate(R.layout.fragment_set_password, container, false))
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        setupTable()
    }

    /**
     * Contract
     */

    override fun incorrectPassword() {
        binding.progressbarSetPassword.incorrectPassword()
    }

    override fun setCompletedLinesOnProgressbar(completedLines: Int){
        binding.progressbarSetPassword.setCompletedLinesNum(completedLines)
    }

    override fun openLastFragment() {
        findNavController().popBackStack()
    }

    override fun setTitle(@StringRes title: Int) {
        binding.tvSetPassword.setText(title)
    }

    override fun getFragment(): Fragment = this

    override fun getAction(): String = args.action

    override fun fragmentManager(): FragmentManager = parentFragmentManager

    /**
     * Help fun-s
     */

    private fun setupTable(){
        val table: TableLayout = binding.tableSetPasswordNumbers

        /**
         * Number buttons
         */
        var order = 1
        for(rowNum in 0 until 3){
            val row = TableRow(requireContext())
            row.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
            for(buttonNum in 0 until 3){
                val button = NumberButton(requireContext())
                button.setupText(order)
                button.setOnClickListener { presenter.onNumberClicked(button.getOrder()) }
                order++


                row.addView(button, buttonNum)
            }

            table.addView(row, rowNum)
        }

        /**
         * Bottom buttons
         */
        val row = TableRow(requireContext())
        row.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
        for(buttonNum in 0 until 3){
            val button = NumberButton(requireContext())
            when(buttonNum) {
                0 -> {
                    button.isEnabled = false
                }
                1 -> {
                    button.setupText(0)
                    button.setOnClickListener { presenter.onNumberClicked("0") }
                }
                2 -> {
                    button.setupClearButton()
                    button.setOnClickListener { presenter.onClearClicked() }
                }
            }
            order++
            row.addView(button, buttonNum)
        }

        table.addView(row, 3)
    }

    companion object {
        fun start(navController: NavController, action: String) {
            val direction = NavGraphDirections.actionToSetPassword(action)
            navController.navigate(direction)
        }
    }
}