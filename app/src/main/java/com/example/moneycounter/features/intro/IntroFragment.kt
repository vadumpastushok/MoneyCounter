package com.example.moneycounter.features.intro

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.isInvisible
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.moneycounter.R
import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentIntroBinding
import com.example.moneycounter.features.home.HomeFragment
import com.example.moneycounter.features.start_screen.StartScreenFragment
import com.example.moneycounter.model.entity.Intro

class IntroFragment: BaseFragment<FragmentIntroBinding>(), IntroContract {

    private val presenter: IntroPresenter by lazy { IntroPresenter() }
    private val adapter: IntroPagerAdapter by lazy { IntroPagerAdapter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIntroBinding {
        return FragmentIntroBinding.inflate(inflater, container, false)
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        setupViewPager()
        initListeners()
    }

    override fun setData(data: MutableList<Intro>) {
        adapter.setData(data)
    }

    override fun scrollToLast() {
        val lastPosition = binding.introViewPager.adapter?.itemCount?.minus(1) ?: return
        binding.introViewPager.currentItem = lastPosition
    }

    override fun scrollToNext() {
        val nextPosition = binding.introViewPager.currentItem.plus(1)
        binding.introViewPager.currentItem = nextPosition
    }

    override fun getCurrentViewPagerPosition(): Int {
        return binding.introViewPager.currentItem
    }

    override fun setNextButtonEnable(isEnabled: Boolean) {
        binding.buttonIntroNext.isEnabled = isEnabled
    }

    override fun acceptPolicyTerms(){
        val preferences = context?.getSharedPreferences(Config.PREFERENCES_NAME, Context.MODE_PRIVATE) ?: return
        preferences.edit()?.putBoolean(Config.PREF_IS_POLICY_CONFIRMED, true)?.apply()
    }

    override fun openHome(){
        HomeFragment.start(findNavController())
    }

    override fun setButtonNextText(@StringRes text: Int){
        binding.buttonIntroNext.text = getString(text)
    }

    override fun setButtonSkipInvisible(isInvisible: Boolean){
        isInvisible.also { binding.buttonIntroSkip.isInvisible = it }
    }

    /**
     * Help fun-s
     */

    private fun initListeners() {
        binding.buttonIntroNext.setOnClickListener {
            presenter.onNextPageClicked()
        }
        binding.buttonIntroSkip.setOnClickListener {
            presenter.onCancelIntroClicked()
        }
        adapter.radioButtonListener = {
            presenter.acceptPolicyTerms()
        }
    }

    private fun setupViewPager() {
        binding.introViewPager.adapter = adapter

        val dotsIndicator = binding.introDotsIndicator
        dotsIndicator.setViewPager2(binding.introViewPager)

        binding.introViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                presenter.onPageSelected()
            }
        })

    }
    companion object {
        fun start(navController: NavController) {
            navController.navigate(R.id.action_to_intro)
        }
    }
}