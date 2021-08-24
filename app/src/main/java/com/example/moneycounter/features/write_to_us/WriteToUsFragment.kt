package com.example.moneycounter.features.write_to_us

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentWriteToUsBinding
import com.example.moneycounter.features.home.HomeFragment

class WriteToUsFragment: BaseFragment<FragmentWriteToUsBinding>(), WriteToUsContract {

    private val presenter by lazy { WriteToUsPresenter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWriteToUsBinding {
        return FragmentWriteToUsBinding.bind(inflater.inflate(R.layout.fragment_write_to_us, container, false))
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        initListeners()
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }

    /**
     * Contract fun-s
     */

    override fun openLastFragment(){
        findNavController().popBackStack()
    }

    override fun openHomeFragment() {
        HomeFragment.start(findNavController())
    }

    override fun hideKeyboard() {
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun setEditEnabled(isEnabled: Boolean) {
        if(isEnabled){
            binding.writeToUsEdit.setLines(6)
            binding.writeToUsEdit.background = ResourcesCompat.getDrawable(requireContext().resources, R.drawable.tv_active_transparent_with_borders, null)
        }else{
            binding.writeToUsEdit.setLines(1)
            binding.writeToUsEdit.background = ResourcesCompat.getDrawable(requireContext().resources, R.drawable.tv_transparent_with_borders, null)
        }
    }

    /**
     * Help fun-s
     */

    private fun initListeners(){
        binding.writeToUsTitlebar.setBackButtonClickListener {
            presenter.onBackClicked()
        }
        binding.writeToUsButton.setOnClickListener {
            presenter.onNextButtonClicked()
        }
        binding.writeToUsEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                presenter.editIsEmpty(s?.isEmpty() == true)
            }
        })
    }

    companion object{
        fun start(navController: NavController){
            navController.navigate(R.id.action_to_write_to_us)
        }
    }
}