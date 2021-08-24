package com.example.moneycounter.features.category_add

import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.app.App.Companion.context
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Category
import kotlinx.coroutines.launch

class CategoryAddPresenter: BasePresenter<CategoryAddContract>() {

    private lateinit var databaseManager: DatabaseManager

    override fun onViewAttached() {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, DBConfig.DB_NAME
        ).build()
        databaseManager = DatabaseManager(db.categoryDao(), db.financeDao())
    }

    fun onBackClicked(){
        rootView?.openLastFragment()
    }

    fun onIconPickerClicked(){
        rootView?.openIconPickerFragment()
    }

    fun onSelectColorClicked(){
        rootView?.showDialog()
    }

    fun onColorSelected(color: Int){
        rootView?.setInDialogColor(color)
    }

    fun onApplyColor(color: Int){
        rootView?.closeDialog()
        rootView?.setColor(color)
    }

    private fun checkAllData(): Boolean{
        val root = rootView ?: return false
        return when {
            root.getIcon() == "icon_add" -> {
                root.showToast("иконка не вибрана")
                false
            }
            root.getColor() == 0 -> {
                root.showToast("цвет не вибран")
                false
            }
            root.getTitle().isEmpty() -> {
                root.showToast("название не вибрано")
                false
            }
            else -> {
                true
            }
        }

    }

    fun onCreateCategory(){
        if(!checkAllData())return
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
        rootView?.openLastFragment()
    }


}