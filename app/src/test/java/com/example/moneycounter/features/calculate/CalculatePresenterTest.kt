package com.example.moneycounter.features.calculate

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class CalculatePresenterTest {

    @Test
    fun testFormatTime() {
        val date = Date()
        date.time = 1686215770000
        val locale = Locale("en")
        assertEquals("last update 08 June 2023 year at 12:16:10", CalculatePresenter.formatTime(locale, "last update", "year", "at", date))
    }
}