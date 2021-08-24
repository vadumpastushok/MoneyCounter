package com.example.moneycounter.features.start_screen

import android.content.Context
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.R
import com.example.moneycounter.app.App.Companion.context
import com.example.moneycounter.app.Config
import com.example.moneycounter.app.notification.AlarmHandler
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.db.DefaultData
import kotlinx.coroutines.launch

class StartScreenPresenter: BasePresenter<StartScreenContract>() {

    private val preferences by lazy { context.getSharedPreferences(Config.PREFERENCES_NAME, Context.MODE_PRIVATE) }

    override fun onViewAttached() {
        if(checkIsPasswordEnabled()){
            rootView?.setupTable()
        }else {
            if(checkIsPolicyConfirmed()){
                rootView?.openHomeScreen()
            } else {
                rootView?.openIntro()
            }
        }

        if(!checkIsDatabaseInitialized()){
            initializeDatabase()
        }

        if(checkIsNotificationEnabled()) {
            val alarmHandler = AlarmHandler()
            alarmHandler.setupAlarm()
        }
    }

    private fun checkIsPasswordEnabled(): Boolean{
        val password = preferences?.getString(Config.PREF_PASSWORD, "") ?: ""
        return if(password != "") {
            rightPassword = password
            true
        }else{
            false
        }
    }

    private fun checkIsPolicyConfirmed() =
        preferences?.getBoolean(Config.PREF_IS_POLICY_CONFIRMED, false) ?: false

    private fun checkIsDatabaseInitialized() =
        preferences?.getBoolean(Config.PREF_IS_DATABASE_INITIALIZED, false) ?: false

    private fun checkIsNotificationEnabled() =
        preferences?.getBoolean(Config.PREF_IS_NOTIFICATION_ENABLED, true) ?: true

    fun checkIsFingerprintEnabled() =
        preferences?.getBoolean(Config.PREF_IS_FINGERPRINT_ENABLED, false) ?: false

    private fun initializeDatabase(){
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, DBConfig.DB_NAME
        ).build()

        val databaseManager = DatabaseManager(db.categoryDao(), db.financeDao())
        viewModelScope.launch {
            databaseManager.insertCategory(
                DefaultData.categories
            )
        }

        preferences?.edit()?.putBoolean(Config.PREF_IS_DATABASE_INITIALIZED, true)?.apply()

    }


    private fun disableFingerPrint(){
        preferences?.edit()?.putBoolean(Config.PREF_IS_FINGERPRINT_ENABLED, false)?.apply()
        rootView?.getFingerprintButton()?.disableFingerPrint()
        rootView?.getFingerprintButton()?.isEnabled = false
    }

    private var rightPassword = ""
    private var password: String = ""
    fun onNumberClicked(order: String){
        password += order
        rootView?.setCompletedLinesOnProgressbar(password.length)
        if(password.length == 4 && password == rightPassword){
            rootView?.openHomeScreen()
        }else if(password.length == 4 && password != rightPassword){
            password = ""
            rootView?.incorrectPassword()
        }
    }

    fun onFingerPrintClicked(){
        if(!checkIsFingerprintEnabled()) return

        val fragment: Fragment = rootView?.getFragment() ?: return

        val  executor = ContextCompat.getMainExecutor(context)

        val biometricPrompt = BiometricPrompt(
            fragment,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if(errorCode == BiometricPrompt.ERROR_LOCKOUT){
                        Toast.makeText(context, context.getString(R.string.try_again_later), Toast.LENGTH_SHORT).show()
                    }else if(errorCode == BiometricPrompt.ERROR_NO_BIOMETRICS){
                        Toast.makeText(context, context.getString(R.string.biometrics_not_found), Toast.LENGTH_SHORT).show()
                        disableFingerPrint()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    rootView?.openHomeScreen()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.scan_to_enter))
            .setNegativeButtonText(context.getString(R.string.enter_code))
            .build()
        biometricPrompt.authenticate(promptInfo)
    }

    fun onClearClicked(){
        if(password.isEmpty())return
        password = password.substring(0, password.lastIndex)
        rootView?.setCompletedLinesOnProgressbar(password.length)
    }

}