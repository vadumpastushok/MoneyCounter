package com.example.moneycounter.features.category

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.R
import com.example.moneycounter.app.App.Companion.context
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.db.Finance
import com.example.moneycounter.model.entity.ui.MoneyType
import kotlinx.coroutines.launch

class CategoryPresenter: BasePresenter<CategoryContract>() {

    private var categories: MutableList<Category> = mutableListOf()
    private var financesToDelete: MutableList<Finance> = mutableListOf()
    private var isEditable: Boolean = false


    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, DBConfig.DB_NAME
    ).build()
    private val dbManager = DatabaseManager(db.categoryDao(), db.financeDao(), db.currencyDao())

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
                categories.remove(categories.find { it.title == context.getString(R.string.title_piggy_bank) })
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

    private fun prepareList(): MutableList<Category>{
        val list: MutableList<Category> = categories.toMutableList()
        list.removeAt(list.lastIndex)

        var lastOrder = 0
        for(i in 0 until list.size){
            val item = list[i]
            item.order = lastOrder + 1
            lastOrder = item.order
        }
        return list
    }

    private fun applyChanges(){
        viewModelScope.launch {
            val categories =
                dbManager.getCategoryByType(rootView?.getMoneyType() ?: MoneyType.INCOME)
            categories.forEach {
                if (it.title != context.getString(R.string.title_piggy_bank)) {
                    dbManager.deleteCategory(it)
                }
                dbManager.insertCategory(prepareList())
            }

            Toast.makeText(context, financesToDelete.toString(), Toast.LENGTH_SHORT).show()
            dbManager.deleteFinance(financesToDelete)
        }
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
    }

    private fun checkIsElseCategories(){
        if(categories.size == 0){
            rootView?.setMessageNoCategories()
        }
    }

    private var isCategoryDeleting = false
    private var categoryDeleting = 0
    fun onDeleteCategoryClicked(index: Int){
        if(index == -1)return
        isCategoryDeleting = true
        categoryDeleting = index
        viewModelScope.launch {
            val id = categories[index].id
            val financeList = dbManager.getFinancesByCategoryId(id)
            if(financeList.size > 0) {
                rootView?.showDeletingDialog()
                financesToDelete += financeList
            }else{
                deleteCategory()
            }
        }
    }

    private fun deleteCategory(){
        categories.removeAt(categoryDeleting)
        rootView?.notifyItemRemoved(categoryDeleting)
        checkIsElseCategories()
    }

    fun onBackClicked(){
        if(isEditable){
            isCategoryDeleting = false
            rootView?.showExitDialog()
        }else {
            rootView?.openLastFragment()
        }
    }

    fun onRightClicked(){
        isEditable = !isEditable
        rootView?.setIsEditable(isEditable)

        if(isEditable){
            val index = categories.lastIndex
            categories.removeAt(index)
            rootView?.notifyItemRemoved(index)
            checkIsElseCategories()
        }else{
            insertAddButton(rootView?.getMoneyType() ?: MoneyType.INCOME)
            rootView?.notifyItemInserted(categories.lastIndex)
            applyChanges()
            rootView?.removeMessageNoCategories()
        }
    }

    fun onAcceptDialog(){
        if(isCategoryDeleting) {
            deleteCategory()
        }else {
            rootView?.openLastFragment()
        }
        rootView?.closeDialog()
    }

    fun onRefuseDialog(){
        rootView?.closeDialog()
    }
}