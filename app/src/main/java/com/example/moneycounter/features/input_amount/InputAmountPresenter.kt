package com.example.moneycounter.features.input_amount

import androidx.lifecycle.viewModelScope
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.db.Finance
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class InputAmountPresenter @Inject constructor(
    private val databaseManager: DatabaseManager
): BasePresenter<InputAmountContract>() {

    override fun onViewAttached() {
        val root = rootView ?: return
        viewModelScope.launch {
            val item: Category = databaseManager.getCategoryById(root.getFragmentArgs().id)
            root.setupView(item)
        }
    }

    fun onBackButtonClicked(){
        rootView?.hideKeyboard()
        rootView?.openLastFragment()
    }

    fun onInputAmountButtonClicked(){
        val root = rootView ?: return
        if(root.getAmount().isEmpty())return
        if(root.getAmount().toInt() == 0) {
            root.hideKeyboard()
            root.openHomeFragment()
            return
        }

        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        val time = calendar.timeInMillis


        viewModelScope.launch {
            databaseManager.insertFinance(
                Finance(
                    root.getFragmentArgs().id,
                    root.getAmount().toInt(),
                    time,
                    root.getFragmentArgs().financePlaceId,
                )
            )
        }

        root.hideKeyboard()
        root.openHomeFragment()
    }
}