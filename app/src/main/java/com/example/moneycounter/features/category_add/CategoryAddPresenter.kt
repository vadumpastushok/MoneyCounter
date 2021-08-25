package com.example.moneycounter.features.category_add

import androidx.lifecycle.viewModelScope
import com.example.moneycounter.R
import com.example.moneycounter.app.App.Companion.context
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Category
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryAddPresenter @Inject constructor(): BasePresenter<CategoryAddContract>() {

    @Inject lateinit var databaseManager: DatabaseManager
    private var title = ""
    private var color = 0
    private var icon = ""

    private fun checkAllData(){
        val root = rootView ?: return

        if(root.getIcon() == context.getString(R.string.default_icon)|| root.getColor() == 0 || root.getTitle().isEmpty()){
            root.setButtonEnabled(false)
        }else{
            root.setButtonEnabled(true)
        }
    }

    fun onBackClicked(){
        rootView?.hideKeyboard()
        rootView?.openLastFragment()
    }

    fun onIconPickerClicked(){
        rootView?.hideKeyboard()
        rootView?.openIconPickerFragment()
    }

    fun onSelectColorClicked(){
        rootView?.showDialog()
    }

    fun onColorSelected(selectedColor: Int){
        rootView?.setInDialogColor(selectedColor)
    }

    fun onApplyColor(selectedColor: Int){
        rootView?.closeDialog()
        rootView?.setColor(selectedColor)
        color = selectedColor
        checkAllData()
    }

    fun onTextChanged(selectedTitle: String){
        title = selectedTitle
        checkAllData()
    }

    fun onIconSelected(selectedIcon: String){
        icon = selectedIcon
        rootView?.setIcon(icon)
        checkAllData()
    }

    fun onCreateCategory(){
        val root = rootView ?: return
        viewModelScope.launch {

            val order: Int = databaseManager.getNumberOfCategoriesByMoneyType(root.getMoneyType())

            databaseManager.insertCategory(
                Category(
                    root.getTitle(),
                    root.getIcon(),
                    root.getColor(),
                    root.getMoneyType(),
                    order
                )
            )
        }
        rootView?.openHomeFragment()
    }
}