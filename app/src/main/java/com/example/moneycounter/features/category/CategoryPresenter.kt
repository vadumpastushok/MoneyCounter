package com.example.moneycounter.features.category

import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.app.App.Companion.context
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.ui.MoneyType
import kotlinx.coroutines.launch

class CategoryPresenter: BasePresenter<CategoryContract>() {

    private val db = Room.databaseBuilder(
        App.context,
        AppDatabase::class.java, DBConfig.DB_NAME
    ).build()
    private val dbManager = DatabaseManager(db.categoryDao(), db.financeDao())
    private var categories: MutableList<Category> = mutableListOf()

    override fun onViewAttached() {
        if(rootView?.getMoneyType() == MoneyType.INCOME){
            rootView?.setTitleText(R.string.title_income)

            viewModelScope.launch {
                categories = dbManager.getCategoryByType(MoneyType.INCOME)
                categories.add(
                    Category(
                        R.string.category_title_add,
                        context.resources.getResourceEntryName(R.drawable.category_icon_add),
                        context.getColor(R.color.dark_text),
                        MoneyType.COSTS,
                        Int.MAX_VALUE
                    )
                )
                categories.sortBy { it.order }
                rootView?.setData(categories)
            }
        }
        else {
            rootView?.setTitleText(R.string.title_costs)
            viewModelScope.launch {
                categories = dbManager.getCategoryByType(MoneyType.COSTS)
                categories.add(
                    Category(
                        R.string.category_title_add,
                        context.resources.getResourceEntryName(R.drawable.category_icon_add),
                        context.getColor(R.color.dark_text),
                        MoneyType.COSTS,
                        Int.MAX_VALUE
                    )
                )
                categories.sortBy { it.order }
                rootView?.setData(categories)
            }
        }
    }

    fun onCategorySelected(order: Int){
        val item = categories[order]
        if(item.title == R.string.category_title_add) {
            rootView?.openAddCategoryFragment()
        } else {
            rootView?.openInputAmountFragment(item.id)
        }
    }

    fun onCategoryPositionChanged(data: MutableList<Category>){
        categories = data
        categories.sortBy { it.order }
        viewModelScope.launch {
            dbManager.updateCategory(categories)
        }
    }


    fun onBackClicked(){
        rootView?.openLastFragment()
    }

}