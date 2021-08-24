package com.example.moneycounter.features.start_screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moneycounter.R
import com.example.moneycounter.app.Config.PREFERENCES_NAME
import com.example.moneycounter.app.Config.PREF_IS_POLICY_CONFIRMED
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentStartScreenBinding
import com.example.moneycounter.features.home.HomeFragment
import com.example.moneycounter.features.intro.IntroFragment

class StartScreenFragment : BaseFragment<FragmentStartScreenBinding>() {

    private var preferences: SharedPreferences? = null

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStartScreenBinding {
        return FragmentStartScreenBinding.bind(LayoutInflater.from(context).inflate(R.layout.fragment_start_screen,container,false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPreferences()

        if(getIsPolicyConfirmed()) {
            openHomeScreen()
        } else if(!getIsPolicyConfirmed()){
            openIntro()
        }
    }

    private fun initPreferences(){
        preferences = context?.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE) ?: return
    }

    private fun getIsPolicyConfirmed(): Boolean = preferences?.getBoolean(PREF_IS_POLICY_CONFIRMED, false) ?: false

    private fun openHomeScreen(){
        HomeFragment.start(findNavController())
    }

    private fun openIntro(){
        IntroFragment.start(findNavController())
    }

}