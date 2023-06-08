package com.example.moneycounter.base

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.moneycounter.R

abstract class BaseFragment<
        VB : ViewBinding
> : Fragment(), BaseContract {

    private var viewBinding: VB? = null

    val binding : VB
        get() = viewBinding ?: throw Exception("Binding is not created")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createViewBinding(inflater,container).also { viewBinding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachToPresenter()
        initWindow()
        updateStatusBarColor(getIsLightStatusBar())
        initView()
    }

    protected abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    open fun initWindow() {
        ViewCompat.setOnApplyWindowInsetsListener(requireView()) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            updateViewPaddings(insets.left, insets.top, insets.right, insets.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    open fun getIsLightStatusBar(): Boolean = true

    override fun updateStatusBarColor(isLight: Boolean) {
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView)
            .isAppearanceLightStatusBars = isLight
    }

    open fun updateViewPaddings(left: Int, top: Int, right: Int, bottom: Int) {
        binding.root.updatePadding(left, top, right, bottom)
    }

    open fun initView() {}

    open fun attachToPresenter() {}

    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun showToast(@StringRes text: Int) {
        showToast(getString(text))
    }
}