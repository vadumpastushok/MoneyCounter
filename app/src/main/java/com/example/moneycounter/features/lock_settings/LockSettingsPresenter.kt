package com.example.moneycounter.features.lock_settings

import android.content.Context
import androidx.biometric.BiometricManager
import com.example.moneycounter.app.App
import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BasePresenter

class LockSettingsPresenter: BasePresenter<LockSettingsContract>() {

    private val preferences by lazy { App.context.getSharedPreferences(Config.PREFERENCES_NAME, Context.MODE_PRIVATE) }
    override fun onViewAttached() {
        getPasswordSettings()
    }


    private fun getPasswordSettings() {
        val password = preferences.getString(Config.PREF_PASSWORD, "") ?: ""
        val isFingerPrintEnabled = preferences.getBoolean(Config.PREF_IS_FINGERPRINT_ENABLED, false)
        rootView?.setBiometricChecked(isFingerPrintEnabled)

        if(password == ""){
            rootView?.setupNotProtectedByPassword()
            rootView?.setChangePasswordViewsInvisible()
            rootView?.setBiometricViewInvisible()
        }else{
            checkIsBiometricExist()
            rootView?.setupProtectedByPassword()
            rootView?.setChangePasswordViewsVisible()
        }
    }

    fun onSwitchPasswordChecked(isChecked: Boolean, isByUser: Boolean){
        if(!isByUser){
            if(isChecked){
                rootView?.setupNotProtectedByPassword()
            }else{
                rootView?.setupProtectedByPassword()
            }
            return
        }
        if(isChecked){
            rootView?.setupCreatePassword()
        }else{
            rootView?.setupRemovePassword()
        }
    }

    fun onChangePasswordClicked(){
        rootView?.setupChangePassword()
    }

    private fun checkIsBiometricExist(){
        val biometricManager = BiometricManager.from(App.context)
        val authenticator = BiometricManager.Authenticators.BIOMETRIC_WEAK

        if(biometricManager.canAuthenticate(authenticator) == BiometricManager.BIOMETRIC_SUCCESS) {
            rootView?.setBiometricViewVisible()
        }else{
            rootView?.setBiometricViewInvisible()
        }
    }

    fun onSwitchBiometricChecked(isChecked: Boolean, isByUser: Boolean){
        if(isByUser){
            preferences.edit().putBoolean(Config.PREF_IS_FINGERPRINT_ENABLED, isChecked).apply()
        }else{
            rootView?.setBiometricChecked(!isChecked)
        }
    }

    fun onBackClicked(){
        rootView?.openLastFragment()
    }
}