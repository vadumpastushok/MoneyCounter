package com.example.moneycounter.features.calculate

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class CalculatePresenterTest {

    @Test
    fun testFormatTime() {
        val date = Date(1686213778)
        val locale = Locale("en")
        assertEquals("last update 20 January 1970 year at 14:23:33", CalculatePresenter.formatTime(locale, "last update", "year", "at", date))
    }
}