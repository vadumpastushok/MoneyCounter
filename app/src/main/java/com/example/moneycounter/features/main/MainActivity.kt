package com.example.moneycounter.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseActivity
import com.example.moneycounter.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun createViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater,null, false)
    }




}