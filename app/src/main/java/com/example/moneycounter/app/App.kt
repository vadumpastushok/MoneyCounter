package com.example.moneycounter.app

import android.app.Application
import com.example.moneycounter.R
import com.example.moneycounter.model.entity.Category

class App: Application() {

    companion object {
        val costsCategories = mutableListOf(
            Category(R.drawable.category_icon_food,R.color.dark_blue,R.string.category_title_food),
            Category(R.drawable.category_icon_utility_services,R.color.red,R.string.category_title_utility_services),
            Category(R.drawable.category_icon_communication,R.color.purple,R.string.category_title_communication),
            Category(R.drawable.category_icon_clothes,R.color.light_blue,R.string.category_title_clothes),
            Category(R.drawable.category_icon_transport,R.color.green,R.string.category_title_transport),
            Category(R.drawable.category_icon_games,R.color.yellow,R.string.category_title_games),
            Category(R.drawable.category_icon_relax,R.color.dark_blue,R.string.category_title_relax),
            Category(R.drawable.category_icon_education,R.color.red,R.string.category_title_education),
            Category(R.drawable.category_icon_kids,R.color.purple,R.string.category_title_kids),
            Category(R.drawable.category_icon_parents,R.color.light_blue,R.string.category_title_parents),
            Category(R.drawable.category_icon_love,R.color.green,R.string.category_title_love),
            Category(R.drawable.category_icon_personal_care,R.color.yellow,R.string.category_title_personal_care),
            Category(R.drawable.category_icon_home_care,R.color.dark_blue,R.string.category_title_home_care),
            Category(R.drawable.category_icon_sport,R.color.red,R.string.category_title_sport),
            Category(R.drawable.category_icon_gift,R.color.purple,R.string.category_title_gift),
            Category(R.drawable.category_icon_pet,R.color.light_blue,R.string.category_title_pet),
            Category(R.drawable.category_icon_hobby,R.color.green,R.string.category_title_hobby),
            Category(R.drawable.category_icon_loans,R.color.yellow,R.string.category_title_loans),
            Category(R.drawable.category_icon_insurance,R.color.dark_blue,R.string.category_title_insurance),
            Category(R.drawable.category_icon_repair,R.color.red,R.string.category_title_repair),
            Category(R.drawable.category_icon_add,R.color.dark_text,R.string.category_title_add),
        )
        val incomeCategories = mutableListOf(
            Category(
                R.drawable.category_icon_salary,
                R.color.dark_blue,
                R.string.category_title_salary
            ),
            Category(
                R.drawable.category_icon_sponsor,
                R.color.red,
                R.string.category_title_sponsor
            ),
            Category(
                R.drawable.category_icon_rollback,
                R.color.purple,
                R.string.category_title_rollback
            ),
            Category(
                R.drawable.category_icon_gift,
                R.color.light_blue,
                R.string.category_title_gift
            ),
            Category(
                R.drawable.category_icon_dividend,
                R.color.green,
                R.string.category_title_dividend
            ),
            Category(
                R.drawable.category_icon_investments,
                R.color.yellow,
                R.string.category_title_investments
            ),
            Category(
                R.drawable.category_icon_business,
                R.color.dark_blue,
                R.string.category_title_business
            ),
            Category(
                R.drawable.category_icon_property,
                R.color.red,
                R.string.category_title_property
            ),
            Category(
                R.drawable.category_icon_insurance,
                R.color.purple,
                R.string.category_title_insurance
            ),
            Category(
                R.drawable.category_icon_stock,
                R.color.light_blue,
                R.string.category_title_stock
            ),
            Category(R.drawable.category_icon_loans, R.color.green, R.string.category_title_loans),
            Category(
                R.drawable.category_icon_unexpected,
                R.color.yellow,
                R.string.category_title_unexpected
            ),
            Category(
                R.drawable.category_icon_state_benefits,
                R.color.purple,
                R.string.category_title_state_benefits
            ),
            Category(R.drawable.category_icon_add, R.color.dark_text, R.string.category_title_add),
        )
    }
}