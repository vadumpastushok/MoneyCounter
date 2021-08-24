package com.example.moneycounter.features.set_password

import android.content.Context
import android.widget.Toast
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BasePresenter

class SetPasswordPresenter: BasePresenter<SetPasswordContract>() {

    private val preferences by lazy { App.context.getSharedPreferences(Config.PREFERENCES_NAME, Context.MODE_PRIVATE) }
    private var previousPassword: String = ""
    private var currentPassword: String = ""
    private var repeatedPassword: String = ""

    private var isVerifying: Boolean = true
    private var isInventing: Boolean = false
    private var isRepeating: Boolean = false

    override fun onViewAttached() {
        rootView?.setupTable()
        getPassword()
    }


    private fun getPassword(){
        val password = preferences.getString(Config.PREF_PASSWORD, null)
        if(password == null){
            isVerifying = false
            isInventing = true
            rootView?.setTitle(R.string.invent_password)
        }else{
            previousPassword = password
        }
    }

    private fun savePassword(){
        preferences?.edit()?.putString(Config.PREF_PASSWORD, currentPassword)?.apply()
    }

    private fun saveIsFingerprintEnabled(){
        preferences?.edit()?.putBoolean(Config.PREF_IS_FINGERPRINT_ENABLED, isFingerprintEnabled)?.apply()
    }

    private fun passwordReady(){
        if(isVerifying){
            if(currentPassword == previousPassword){
                isVerifying = false
                isInventing = true
                currentPassword = ""
                rootView?.setTitle(R.string.invent_password)
                setupFingerprintButton()
            }else{
                rootView?.incorrectPassword()
                currentPassword = ""
            }
        }else if(isInventing){
            repeatedPassword = currentPassword
            currentPassword = ""
            rootView?.setTitle(R.string.repeat_password)
            isInventing = false
            isRepeating = true
        }else if(isRepeating){
            if(currentPassword == repeatedPassword){
                savePassword()
                saveIsFingerprintEnabled()
                rootView?.openHomeFragment()
            }else{
                rootView?.incorrectPassword()
                currentPassword = ""
            }
        }
    }

    private fun setupFingerprintButton(){
        rootView?.getFingerprintButton()?.setupFingerPrint()
        rootView?.getFingerprintButton()?.setOnClickListener { onFingerPrintClicked() }
        rootView?.getFingerprintButton()?.isEnabled = true
        rootView?.getFingerprintButton()?.setActive(isFingerprintEnabled)
    }

    fun onNumberClicked(order: String){
        currentPassword += order
        rootView?.setCompletedLinesOnProgressbar(currentPassword.length)
        if(currentPassword.length == 4){
            passwordReady()
        }
    }

    private var isFingerprintEnabled: Boolean =  preferences?.getBoolean(Config.PREF_IS_FINGERPRINT_ENABLED, false) ?: false
    private var toast: Toast? = null
    private fun onFingerPrintClicked(){
        isFingerprintEnabled = !isFingerprintEnabled
        if(isFingerprintEnabled){
            rootView?.getFingerprintButton()?.setActive(isFingerprintEnabled)
            toast?.cancel()
            toast = Toast.makeText(App.context, App.context.getString(R.string.fingerprint_enabled), Toast.LENGTH_SHORT)
            toast?.show()
        }else{
            rootView?.getFingerprintButton()?.setActive(isFingerprintEnabled)
            toast?.cancel()
            toast = Toast.makeText(App.context, App.context.getString(R.string.fingerprint_disabled), Toast.LENGTH_SHORT)
            toast?.show()
        }
    }


    fun onClearClicked(){
        if(currentPassword.isEmpty())return
        currentPassword = currentPassword.substring(0, currentPassword.lastIndex)
        rootView?.setCompletedLinesOnProgressbar(currentPassword.length)
    }




}