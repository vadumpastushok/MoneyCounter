package com.example.moneycounter.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.example.moneycounter.R

abstract class BaseActivity<
        VB : ViewBinding
        > : AppCompatActivity() {

    private var viewBinding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=createViewBinding(LayoutInflater.from(this)).also {
            setContentView(it.root)
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
    }

    protected abstract fun createViewBinding(inflater: LayoutInflater): VB




}