package com.example.moneycounter.features.financial_place

import androidx.lifecycle.viewModelScope
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.FinancialPlace
import kotlinx.coroutines.launch
import javax.inject.Inject

class FinancialPlacePresenter @Inject constructor(
    private val databaseManager: DatabaseManager,
): BasePresenter<FinancialPlaceContract>() {

    private var financialPlaces: MutableList<FinancialPlace> = mutableListOf()
    private var financesPlacesToDelete: MutableList<FinancialPlace> = mutableListOf()
    private var isEditable: Boolean = false

    override fun onViewAttached() {
        updateData()
    }

    private fun updateData(){
        viewModelScope.launch {
            financialPlaces = databaseManager.getAllFinancialPlaces()
            insertAddButton()
            financialPlaces.sortBy { it.order }
            rootView?.setData(financialPlaces)
        }
    }

    private fun insertAddButton(){
        financialPlaces.add(
            FinancialPlace(
                App.context.getString(R.string.btn_title_add),
                App.context.resources.getResourceEntryName(R.drawable.icon_add),
                App.context.getColor(R.color.dark_text),
                Int.MAX_VALUE,
            )
        )
    }

    private fun prepareList(): MutableList<FinancialPlace>{
        val list: MutableList<FinancialPlace> = financialPlaces.toMutableList()
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
            val financialPlaces =
                databaseManager.getAllFinancialPlaces()
            financialPlaces.forEach { financialPlace ->
                databaseManager.deleteFinancialPlace(financialPlace)
                prepareList().forEach { databaseManager.insertFinancialPlace(it) }
            }
            financesPlacesToDelete.forEach { databaseManager.deleteFinancialPlace(it) }
        }
    }

    fun onCategorySelected(order: Int){
        if(isEditable) return

        val item = financialPlaces[order]
        if(item.title == App.context.getString(R.string.btn_title_add)) {
            rootView?.openAddFinancePlaceFragment()
        } else {
            rootView?.openInputAmountFragment(item.id)
        }
    }

    fun onCategoryPositionChanged(data: MutableList<FinancialPlace>){
        financialPlaces = data
        financialPlaces.sortBy { it.order }
    }

    private fun checkIsElseCategories(){
        if(financialPlaces.size == 0){
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
            val id = financialPlaces[index].id
            val financePlacesList = databaseManager.getAllFinancialPlaces().filter { it.id == id }
            if (financePlacesList.isNotEmpty()) {
                rootView?.showDeletingDialog()
                financesPlacesToDelete += financePlacesList
            } else {
                deleteCategory()
            }
        }
    }

    private fun deleteCategory(){
        financialPlaces.removeAt(categoryDeleting)
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
            val index = financialPlaces.lastIndex
            financialPlaces.removeAt(index)
            rootView?.notifyItemRemoved(index)
            checkIsElseCategories()
        }else{
            insertAddButton()
            rootView?.notifyItemInserted(financialPlaces.lastIndex)
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