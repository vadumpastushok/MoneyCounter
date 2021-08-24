package com.example.moneycounter.app

import android.graphics.Color
import com.example.moneycounter.R
import com.example.moneycounter.model.entity.ui.Intro
import com.example.moneycounter.model.entity.ui.IntroType
import com.ssynhtn.waveview.WaveView

object Config {

    val PREFERENCES_NAME = "preferences"
    val PREF_IS_POLICY_CONFIRMED = "isPolicyConfirmed"
    val PREF_IS_DATABASE_INITALIZED = "isDatabaseInitialized"

    val introData = mutableListOf(
        Intro(
            R.string.intro_title_mission,
            R.string.intro_text_mission,
            R.drawable.intro_mission
        ),
        Intro(
            R.string.intro_title_costs,
            R.string.intro_text_costs,
            R.drawable.intro_costs
        ),
        Intro(
            R.string.intro_title_analytics,
            R.string.intro_text_analytics,
            R.drawable.intro_analytics
        ),
        Intro(
            R.string.intro_title_independence,
            R.string.intro_text_independence,
            R.drawable.intro_independence
        ),
        Intro(
            R.string.intro_title_work_hour,
            R.string.intro_text_work_hour,
            R.drawable.intro_work_hour
        ),
        Intro(
            R.string.intro_title_budget,
            R.string.intro_text_budget,
            R.drawable.intro_budget
        ),
        Intro(
            R.string.intro_title_offline,
            R.string.intro_text_offline,
            R.drawable.intro_offline
        ),
        Intro(
            R.string.intro_title_offline,
            R.string.intro_text_offline,
            R.drawable.intro_offline,
            IntroType.POLICY
        )
    )

    val wavesData = mutableListOf(
        WaveView.WaveData(
            1500f,
            133f,
            180f,
            1125f,
            Color.WHITE,
            Color.WHITE,
            0.4f,
            10000,
            false
        ),
        WaveView.WaveData(
            1500f,
            133f,
            120f,
            750f,
            Color.WHITE,
            Color.WHITE,
            0.5f,
            10000,
            true
        ),
        WaveView.WaveData(
            1500f,
            133f,
            60f,
            375f,
            Color.WHITE,
            Color.WHITE,
            0.6f,
            10000,
            false
        ),
        WaveView.WaveData(
            1500f,
            133f,
            0f,
            0f,
            Color.WHITE,
            Color.WHITE,
            1f,
            10000,
            true
        )
    )
}