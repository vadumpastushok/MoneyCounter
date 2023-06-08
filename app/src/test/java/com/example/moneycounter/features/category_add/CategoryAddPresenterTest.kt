package com.example.moneycounter.features.category_add

import org.junit.Assert.*
import org.junit.Test

class CategoryAddPresenterTest {

    @Test
    fun testDataFullness() {
        assertTrue(CategoryAddPresenter.checkDataFullness("icon", 50, "title", "icon_add"))
    }

    @Test
    fun testDataFullness2() {
        assertFalse(CategoryAddPresenter.checkDataFullness("icon", 0, "title", "icon_add"))
    }

    @Test
    fun testDataFullness3() {
        assertTrue(CategoryAddPresenter.checkDataFullness("icon_add", 15, "title", ""))
    }

    @Test
    fun testDataFullness4() {
        assertFalse(CategoryAddPresenter.checkDataFullness("icon", 0, "", "icon_add"))
    }
}