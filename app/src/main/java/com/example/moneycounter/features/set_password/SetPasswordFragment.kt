package com.example.moneycounter.features.set_password

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentSetPasswordBinding
import com.example.moneycounter.features.home.HomeFragment
import com.example.moneycounter.ui.custom.NumberButton

class SetPasswordFragment: BaseFragment<FragmentSetPasswordBinding>(), SetPasswordContract {

    private val presenter by lazy { SetPasswordPresenter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSetPasswordBinding {
        return FragmentSetPasswordBinding.bind(inflater.inflate(R.layout.fragment_set_password, container, false))
    }



    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun incorrectPassword() {
        binding.progressbarSetPassword.incorrectPassword()
    }

    override fun setCompletedLinesOnProgressbar(completedLines: Int){
        binding.progressbarSetPassword.setCompletedLinesNum(completedLines)
    }

    override fun openHomeFragment() {
        HomeFragment.start(findNavController())
    }

    override fun setTitle(@StringRes title: Int) {
        binding.tvSetPassword.setText(title)
    }

    override fun getFragment(): Fragment = this

    override fun getFingerprintButton(): NumberButton = fingerprintButton

    private lateinit var fingerprintButton: NumberButton
    override fun setupTable(){
        val table: TableLayout = binding.tableSetPasswordNumbers

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

        val row = TableRow(requireContext())
        row.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
        for(buttonNum in 0 until 3){
            val button = NumberButton(requireContext())
            when(buttonNum) {
                0 -> {
                    button.isEnabled = false
                    fingerprintButton = button
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
        fun start(navController: NavController) {
            navController.navigate(R.id.action_to_set_password)
        }
    }

}