package com.example.moneycounter.features.data_manager

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.features.home.HomeFragment
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.db.Finance
import com.example.moneycounter.model.entity.ui.MoneyType
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import ir.androidexception.filepicker.dialog.SingleFilePickerDialog
import ir.androidexception.filepicker.utility.Util
import kotlinx.coroutines.launch
import java.io.File


open class ImportDataManager(val fragment: Fragment): ViewModel() {

    private var databaseManager: DatabaseManager

    init{
        val db = Room.databaseBuilder(
            App.context,
            AppDatabase::class.java, DBConfig.DB_NAME
        ).build()
        databaseManager = DatabaseManager(db.categoryDao(), db.financeDao())
    }


    fun importData(){
        if (Util.permissionGranted(fragment.activity)) {
            val singleFilePickerDialog = SingleFilePickerDialog(fragment.requireContext(),
                {
                },
                { files: Array<File> ->
                    openFile(files[0].path)
                })
            singleFilePickerDialog.show()
        } else {
            Util.requestPermission(fragment.activity)
            if(Util.permissionGranted(fragment.activity)){
                importData()
            }
        }
    }

    private fun openFile(path: String){
        val file = File(path)
        if(file.extension != "csv"){
            Toast.makeText(App.context, App.context.getString(R.string.csv_required), Toast.LENGTH_SHORT).show()
            return
        }
        changeDatabase(file)
    }

    private fun changeDatabase(file: File){

        val rows: List<Map<String, String>> = csvReader().readAllWithHeader(file)

        viewModelScope.launch {
            databaseManager.deleteAll()

            for(row in rows){
                val moneyTypeString: String = row[App.context.getString(R.string.header_money_type)] ?: ""

                val moneyType = if(moneyTypeString == MoneyType.INCOME.toString()) {
                    MoneyType.INCOME
                }else{
                    MoneyType.COSTS
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

                if(row[App.context.getString(R.string.header_finance_id)].isNullOrEmpty())continue


                databaseManager.insertFinance(
                    Finance(
                        row[App.context.getString(R.string.header_category_id)]?.toLong() ?: 0,
                        row[App.context.getString(R.string.header_amount)]?.toInt() ?: 0,
                        row[App.context.getString(R.string.header_date)]?.toLong() ?: 0,
                        row[App.context.getString(R.string.header_finance_id)]?.toLong() ?: 0,
                    )
                )


            }
            HomeFragment.start(fragment.findNavController())
        }
    }

}