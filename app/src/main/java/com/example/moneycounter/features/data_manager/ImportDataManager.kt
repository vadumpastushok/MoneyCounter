package com.example.moneycounter.features.data_manager

import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.db.Finance
import com.example.moneycounter.model.entity.ui.MoneyType
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File
import javax.inject.Inject


class ImportDataManager @Inject constructor(
    private val databaseManager: DatabaseManager
) {

    var incomeAmount = 0
    var costsAmount = 0

    suspend fun importDataFromFile(file: File){
        val rows: List<Map<String, String>> = csvReader().readAllWithHeader(file)

        databaseManager.deleteAllFinances()
        databaseManager.deleteAllCategories()

        for (row in rows) {
            val moneyTypeString: String =
                row[App.context.getString(R.string.header_money_type)] ?: ""

            var moneyType: MoneyType
            if (moneyTypeString == MoneyType.INCOME.toString()) {
                moneyType = MoneyType.INCOME
                val amountString = row[App.context.getString(R.string.header_amount)]
                if (!amountString.isNullOrEmpty()) {
                    incomeAmount += amountString.toInt()
                }
            } else {
                moneyType = MoneyType.COSTS
                val amountString = row[App.context.getString(R.string.header_amount)]
                if (!amountString.isNullOrEmpty()) {
                    costsAmount += amountString.toInt()
                }
            }

            databaseManager.insertCategory(
                Category(
                    row[App.context.getString(R.string.header_name)] ?: "",
                    row[App.context.getString(R.string.header_icon)] ?: "",
                    row[App.context.getString(R.string.header_color)]?.toInt() ?: 0,
                    moneyType,
                    row[App.context.getString(R.string.header_order)]?.toInt() ?: 0,
                    row[App.context.getString(R.string.header_category_id)]?.toLong() ?: 0,
                )
            )

            if (row[App.context.getString(R.string.header_finance_id)].isNullOrEmpty()) continue

            databaseManager.insertFinance(
                Finance(
                    row[App.context.getString(R.string.header_category_id)]?.toLong() ?: 0,
                    row[App.context.getString(R.string.header_amount)]?.toInt() ?: 0,
                    row[App.context.getString(R.string.header_date)]?.toLong() ?: 0,
                    row[App.context.getString(R.string.header_finance_id)]?.toLong() ?: 0,
                )
            )
        }
    }
}