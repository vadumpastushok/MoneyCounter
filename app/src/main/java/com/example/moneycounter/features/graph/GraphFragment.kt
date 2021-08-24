package com.example.moneycounter.features.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentGraphBinding

class GraphFragment : BaseFragment<FragmentGraphBinding>() {
    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGraphBinding {
        return FragmentGraphBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {
        fun start(navController: NavController) {
            navController.navigate(R.id.action_to_graph)
        }
    }

}