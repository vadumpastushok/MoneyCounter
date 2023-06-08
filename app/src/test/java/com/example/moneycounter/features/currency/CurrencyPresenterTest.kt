package com.example.moneycounter.features.currency

import org.junit.Assert.*
import org.junit.Test

class CurrencyPresenterTest {

    @Test
    fun checkForLowerTimeDurationTest() {
        assertTrue(CurrencyPresenter.checkForLowerTimeDuration(1686214740, 1686213740, 300000))
    }

    @Test
    fun checkForLowerTimeDurationTest2() {
        assertFalse(CurrencyPresenter.checkForLowerTimeDuration(1686214740, 1685214740, 300000))
    }

    @Test
    fun checkForLowerTimeDurationTest3() {
        assertTrue(CurrencyPresenter.checkForLowerTimeDuration(1686214740, 1686213740, 3000))
    }
}