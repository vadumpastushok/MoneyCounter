package com.example.moneycounter.model.db

object DBConfig {

    const val DB_VERSION = 2
    const val DB_NAME = "money_counter_db"

    object Category {
        const val TABLE_NAME = "categories"

        object Columns {
            const val TITLE = "title"
            const val ICON = "icon"
            const val COLOR = "color"
            const val TYPE = "type"
            const val ORDER = "order"
            const val ID = "id"
        }
    }

    object Finance {
        const val TABLE_NAME = "finances"

        object Columns {
            const val CATEGORY_ID = "category_id"
            const val DATE = "date"
            const val AMOUNT = "amount"
            const val PLACE = "place"
            const val ID = "id"
        }
    }

    object FinancialPlace {
        const val TABLE_NAME = "financial_places"

        object Columns {
            const val TITLE = "title"
            const val ICON = "icon"
            const val ID = "id"
        }
    }

    object Currency {
        const val TABLE_NAME = "currencies"

        object Columns {
            const val NAME = "name"
            const val SYMBOL = "symbol"
            const val FLAG = "flag"
            const val FIRST_RATE = "firstRate"
            const val SECOND_RATE = "secondRate"
            const val TYPE = "type"
            const val ID = "id"
        }
    }
}