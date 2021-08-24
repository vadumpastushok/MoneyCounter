package com.example.moneycounter.features.set_password

import android.content.Context
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BasePresenter

class SetPasswordPresenter: BasePresenter<SetPasswordContract>() {

    private val preferences by lazy { App.context.getSharedPreferences(Config.PREFERENCES_NAME, Context.MODE_PRIVATE) }
    private var currentPassword: String = ""
    private var requiredPassword: String = ""

    private var isVerifying: Boolean = false
    private var isInventing: Boolean = false
    private var isRepeating: Boolean = false

    override fun onViewAttached() {
        getPassword()
        when(rootView?.getAction()) {
            App.context.getString(R.string.action_create) -> {
                isInventing = true
                 rootView?.setTitle(R.string.invent_password)
            }
            App.context.getString(R.string.action_remove) -> {
                isRepeating = true
                rootView?.setTitle(R.string.enter_current_password)
            }
            App.context.getString(R.string.action_change) -> {
                isVerifying = true
                rootView?.setTitle(R.string.enter_previous_password)
            }
        }
    }




    private fun getPassword(){
        val password = preferences.getString(Config.PREF_PASSWORD, "")
        requiredPassword = password.toString()
    }

    private fun applyChanges(){
        if(rootView?.getAction() == App.context.getString(R.string.action_remove)){
            preferences.edit().putString(Config.PREF_PASSWORD, "").apply()
        }else {
            preferences.edit().putString(Config.PREF_PASSWORD, currentPassword).apply()
        }
    }




    private fun passwordReady(){
        if(isVerifying){
            if(currentPassword == requiredPassword){
                isVerifying = false
                isInventing = true
                currentPassword = ""
                rootView?.setTitle(R.string.invent_password)
            }else{
                rootView?.incorrectPassword()
                currentPassword = ""
            }
        }else if(isInventing){
            requiredPassword = currentPassword
            currentPassword = ""
            rootView?.setTitle(R.string.repeat_password)
            isInventing = false
            isRepeating = true
        }else if(isRepeating){
            if(currentPassword == requiredPassword){
                applyChanges()
                rootView?.openLastFragment()
            }else{
                rootView?.incorrectPassword()
                currentPassword = ""
            }
        }
    }
    fun onNumberClicked(order: String){
        currentPassword += order
        rootView?.setCompletedLinesOnProgressbar(currentPassword.length)
        if(currentPassword.length == 4){
            passwordReady()
        }
    }


    fun onClearClicked(){
        if(currentPassword.isEmpty())return
        currentPassword = currentPassword.substring(0, currentPassword.lastIndex)
        rootView?.setCompletedLinesOnProgressbar(currentPassword.length)
    }
}