package com.example.moneycounter.features.icon_picker

import androidx.lifecycle.viewModelScope
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.DefaultData
import kotlinx.coroutines.launch
import javax.inject.Inject

class IconPickerPresenter @Inject constructor(): BasePresenter<IconPickerContract>() {

    override fun onViewAttached() {
        setupData()
    }

    private fun setupData(){
        viewModelScope.launch {
            val data = DefaultData.icons
            val color = rootView?.getColor() ?: 0

            rootView?.setData(data, color)
        }
    }

    fun onIconSelected(icon: String){
        rootView?.openCategoryAddFragment(icon)
    }

    fun onBackClicked(){
        rootView?.openLastFragment()
    }

}