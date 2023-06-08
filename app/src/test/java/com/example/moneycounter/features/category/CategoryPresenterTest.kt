package com.example.moneycounter.features.category

import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.ui.MoneyType
import org.junit.Assert.*
import org.junit.Test

class CategoryPresenterTest {

    @Test
    fun testPrepareList() {
        val data = listOf(
            Category("title", "icon", 35, MoneyType.INCOME, 5, 16),
            Category("title2", "icon2", 20, MoneyType.COSTS, 2, 3),
            Category("last_title", "last_icon", 200, MoneyType.COSTS, 0, 9),
            Category("add_btn", "add_icon", 0, MoneyType.INCOME, 999999, 0),
        )

        val expected = listOf(
            Category("title", "icon", 35, MoneyType.INCOME, 1, 16),
            Category("title2", "icon2", 20, MoneyType.COSTS, 2, 3),
            Category("last_title", "last_icon", 200, MoneyType.COSTS, 3, 9),
        )

        assertEquals(expected, CategoryPresenter.prepareList(data))
    }
}