package com.example.moneycounter.features.analytics

import com.example.moneycounter.analytics_list.AnalyticsListPresenter
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.db.CategoryWithFinances
import com.example.moneycounter.model.entity.db.Finance
import com.example.moneycounter.model.entity.ui.Analytics
import com.example.moneycounter.model.entity.ui.MoneyType
import org.junit.Assert.*
import org.junit.Test

class AnalyticsListPresenterTest {

    @Test
    fun formatDataToAnalyticsListTest() {
        val data = listOf(
            CategoryWithFinances(
                Category("category", "icon", 0, MoneyType.INCOME, 5, 15),
                listOf(
                    Finance(15, 500, 1686212588, 0, 3),
                    Finance(15, 360, 1686212588, 1, 7),
                    Finance(15, 15, 1686212588, 0, 12),
                )
            ),
            CategoryWithFinances(
                Category("category2", "icon2", 0, MoneyType.INCOME, 1, 23),
                listOf(
                    Finance(23, 100, 1686212588, 2, 1),
                    Finance(23, 85, 1686212588, 1, 3),
                    Finance(23, 90, 1686212588, 1, 6),
                )
            ),
        )
        val expected = listOf(
            Analytics(
                "icon",
                0,
                "category",
                875,
            ),
            Analytics(
                "icon2",
                0,
                "category2",
                275,
            )
        )

        assertEquals(expected, AnalyticsListPresenter.formatDataToAnalyticsList(data))
    }
}