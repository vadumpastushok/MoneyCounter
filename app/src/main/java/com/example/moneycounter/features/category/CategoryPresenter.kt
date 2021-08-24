package com.example.moneycounter.features.category

import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.R
import com.example.moneycounter.app.App.Companion.context
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.ui.MoneyType
import kotlinx.coroutines.launch

class CategoryPresenter: BasePresenter<CategoryContract>() {

    private var categories: MutableList<Category> = mutableListOf()
    private var isEditable: Boolean = false

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, DBConfig.DB_NAME
    ).build()
    private val dbManager = DatabaseManager(db.categoryDao(), db.financeDao())

    override fun onViewAttached() {
        updateData()
    }


    private fun updateData(){
        if(rootView?.getMoneyType() == MoneyType.INCOME){
            rootView?.setTitleText(context.getString(R.string.title_income))

            viewModelScope.launch {
                categories = dbManager.getCategoryByType(MoneyType.INCOME)
                insertAddButton(MoneyType.INCOME)
                categories.sortBy { it.order }
                rootView?.setData(categories)
            }
        }
        else {
            rootView?.setTitleText(context.getString(R.string.title_costs))
            viewModelScope.launch {
                categories = dbManager.getCategoryByType(MoneyType.COSTS)
                insertAddButton(MoneyType.COSTS)
                categories.sortBy { it.order }
                rootView?.setData(categories)
            }
        }

    }




    private fun insertAddButton(moneyType: MoneyType){
        categories.add(
            Category(
                context.getString(R.string.category_title_add),
                context.resources.getResourceEntryName(R.drawable.icon_add),
                context.getColor(R.color.dark_text),
                moneyType,
                Int.MAX_VALUE
            )
        )
    }

    fun onCategorySelected(order: Int){
        if(isEditable) return

        val item = categories[order]
        if(item.title == context.getString(R.string.category_title_add)) {
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

    fun onDeleteCategoryClicked(index: Int){
        if(index == -1)return
        categories.removeAt(index)
        rootView?.notifyItemRemoved(index)
    }

    private fun applyChanges(){
        val list: MutableList<Category> = categories.toMutableList()
        list.removeAt(list.lastIndex)
        viewModelScope.launch {
            dbManager.deleteCategoriesByMoneyType(rootView?.getMoneyType() ?: MoneyType.INCOME)
            dbManager.insertCategory(list)
        }

    }





    fun onBackClicked(){
        rootView?.openHomeFragment()
    }

    fun onRightClicked(){
        isEditable = !isEditable
        rootView?.setIsEditable(isEditable)

        if(isEditable){
            val index = categories.lastIndex
            categories.removeAt(index)
            rootView?.notifyItemRemoved(index)
        }else{
            insertAddButton(rootView?.getMoneyType() ?: MoneyType.INCOME)
            rootView?.notifyItemInserted(categories.lastIndex)
            applyChanges()
        }
    }
}