package com.example.moneycounter.features.start_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentStartScreenBinding
import com.example.moneycounter.features.home.HomeFragment
import com.example.moneycounter.features.intro.IntroFragment
import com.example.moneycounter.ui.custom.NumberButton

class StartScreenFragment : BaseFragment<FragmentStartScreenBinding>(), StartScreenContract {

    private val presenter by lazy { StartScreenPresenter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStartScreenBinding {
        return FragmentStartScreenBinding.bind(LayoutInflater.from(context).inflate(R.layout.fragment_start_screen,container,false))
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun openHomeScreen(){
        HomeFragment.start(findNavController())
    }

    override fun openIntro(){
        IntroFragment.start(findNavController())
    }

    override fun incorrectPassword() {
        binding.progressbarStartScreen.incorrectPassword()
    }

    override fun setCompletedLinesOnProgressbar(completedLines: Int){
        binding.progressbarStartScreen.setCompletedLinesNum(completedLines)
    }

    override fun getFragment(): Fragment = this

    override fun getFingerprintButton() = fingerprintButton

    private lateinit var fingerprintButton: NumberButton
    override fun setupTable(){
        val table: TableLayout = binding.tableStartScreenNumbers

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
                    if(presenter.checkIsFingerprintEnabled()) {
                        button.setupFingerPrint()
                        button.setOnClickListener { presenter.onFingerPrintClicked() }
                        fingerprintButton = button
                    }else {
                        button.isEnabled = false
                    }
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
}