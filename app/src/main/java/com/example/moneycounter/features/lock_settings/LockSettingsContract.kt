package com.example.moneycounter.features.lock_settings

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.example.moneycounter.base.BaseContract

interface LockSettingsContract: BaseContract {

    fun setupNotProtectedByPassword()

    fun setupProtectedByPassword()

    fun setChangePasswordViewsVisible()

    fun setBiometricViewVisible()

    fun setChangePasswordViewsInvisible()

    fun setBiometricViewInvisible()

    fun setBiometricChecked(isChecked: Boolean)

    fun openLastFragment()

    fun setupCreatePassword()

    fun setupRemovePassword()

    fun setupChangePassword()

    fun fragmentManager(): FragmentManager

    fun lifecycleOwner(): LifecycleOwner
}