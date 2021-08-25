package com.example.moneycounter.features.lock_settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.R
import com.example.moneycounter.base.BaseFragment
import com.example.moneycounter.databinding.FragmentLockSettingsBinding
import com.example.moneycounter.features.set_password.SetPasswordFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LockSettingsFragment: BaseFragment<FragmentLockSettingsBinding>(), LockSettingsContract {

    @Inject
    lateinit var presenter: LockSettingsPresenter

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLockSettingsBinding {
        return FragmentLockSettingsBinding.bind(inflater.inflate(R.layout.fragment_lock_settings, container, false))
    }

    override fun attachToPresenter() {
        presenter.attachView(this)
    }

    override fun initView() {
        initListeners()
    }



    override fun setupProtectedByPassword() {
        binding.switchPassword.isChecked = true
    }

    override fun setChangePasswordViewsVisible(){
        binding.tvChangePassword.alpha = 1f
        binding.tvChangePassword.isEnabled = true
    }

    override fun setBiometricViewVisible() {
        binding.biometricLayout.alpha = 1f
        binding.switchBiometric.isClickable = true
    }

    override fun setupNotProtectedByPassword() {
        binding.switchPassword.isChecked = false
    }

    override fun setChangePasswordViewsInvisible(){
        binding.tvChangePassword.alpha = 0.3f
        binding.tvChangePassword.isEnabled = false
    }

    override fun setBiometricViewInvisible() {
        binding.biometricLayout.alpha = 0.3f
        binding.switchBiometric.isChecked = false
        binding.switchBiometric.isClickable = false
    }

    override fun setBiometricChecked(isChecked: Boolean){
        binding.switchBiometric.isChecked = isChecked
    }



    override fun setupCreatePassword() {
        SetPasswordFragment.start(findNavController(), requireContext().getString(R.string.action_create))
    }

    override fun setupRemovePassword() {
        SetPasswordFragment.start(findNavController(), requireContext().getString(R.string.action_remove))
    }

    override fun setupChangePassword() {
        SetPasswordFragment.start(findNavController(), requireContext().getString(R.string.action_change))
    }

    override fun openLastFragment(){
        findNavController().popBackStack()
    }

    override fun fragmentManager(): FragmentManager = parentFragmentManager

    override fun lifecycleOwner(): LifecycleOwner = viewLifecycleOwner


    private fun initListeners(){
        binding.switchPassword.setOnCheckedChangeListener {
                _, isChecked ->
            presenter.onSwitchPasswordChecked(isChecked, binding.switchPassword.isPressed)
        }

        binding.tvChangePassword.setOnClickListener {
            presenter.onChangePasswordClicked()
        }

        binding.switchBiometric.setOnCheckedChangeListener {
                _, isChecked ->
            presenter.onSwitchBiometricChecked(isChecked, binding.switchBiometric.isPressed)
        }

        binding.lockSettingsTitlebar.setBackButtonClickListener {
            presenter.onBackClicked()
        }
    }


     companion object{
        fun start(navController: NavController){
            val direction = NavGraphDirections.actionToLockSettings()
            navController.navigate(direction)
        }
    }
}