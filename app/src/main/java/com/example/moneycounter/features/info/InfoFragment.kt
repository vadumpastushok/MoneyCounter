package com.example.moneycounter.features.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InfoFragment: BaseFragment<FragmentInfoBinding>(), InfoContract {

    @Inject
    lateinit var presenter: InfoPresenter

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentInfoBinding {
        return FragmentInfoBinding.bind(inflater.inflate(R.layout.fragment_info, container, false))
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        initListeners()
    }

    /**
     * Contract
     */

    override fun openLastFragment(){
        findNavController().popBackStack()
    }

    /**
     * Help fun-s
     */

    private fun initListeners(){
        binding.infoToolbar.setBackButtonClickListener {
            presenter.onBackPressed()
        }
    }

    companion object {
        fun start(navController: NavController) {
            navController.navigate(R.id.action_to_info)
        }
    }

}