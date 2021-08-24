package com.example.moneycounter.model.db

object DBConfig {

    const val DB_VERSION = 1
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
            const val ID = "id"
        }
    }
}