package com.example.moneycounter.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.example.moneycounter.base.BaseActivity
import com.example.moneycounter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun createViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater,null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
        super.onCreate(savedInstanceState)
    }

}