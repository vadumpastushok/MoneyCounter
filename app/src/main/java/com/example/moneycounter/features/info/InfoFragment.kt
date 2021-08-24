package com.example.moneycounter.features.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentInfoBinding

class InfoFragment: BaseFragment<FragmentInfoBinding>(), InfoContract {

    private val presenter: InfoPresenter by lazy { InfoPresenter() }

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



    override fun openLastFragment(){
        findNavController().popBackStack()
    }




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