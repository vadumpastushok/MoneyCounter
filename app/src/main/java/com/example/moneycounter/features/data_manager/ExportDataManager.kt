package com.example.moneycounter.features.data_manager

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.app.Config
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Finance
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import ir.androidexception.filepicker.dialog.DirectoryPickerDialog
import ir.androidexception.filepicker.utility.Util
import kotlinx.coroutines.launch
import java.io.File
import java.util.*


class ExportDataManager(val fragment: Fragment): ViewModel() {

    private var databaseManager: DatabaseManager

    init{
        val db = Room.databaseBuilder(
            App.context,
            AppDatabase::class.java, DBConfig.DB_NAME
        ).build()
        databaseManager = DatabaseManager(db.categoryDao(), db.financeDao())
    }


    fun exportData(){
        if (Util.permissionGranted(fragment.activity)) {
            val directoryPickerDialog = DirectoryPickerDialog (fragment.requireContext(),
                {
                },
                { files: Array<File> ->
                    convertDB(files[0].path)
                    Toast.makeText(
                        fragment.activity,
                        App.context.getString(R.string.saved),
                        Toast.LENGTH_SHORT
                    ).show()
                })
            directoryPickerDialog.show()
        } else {
            Util.requestPermission(fragment.activity)
            if(Util.permissionGranted(fragment.activity)){
                exportData()
            }
        }
    }

    private fun convertDB(path: String){
        val data: MutableList<MutableList<String>> = mutableListOf()
        data.add(Config.tableHeader)

        viewModelScope.launch {
            val dbData = databaseManager.getCategoryWithFinances()

            for(it in dbData){

                val finances: MutableList<Finance> = it.finances.toMutableList()
                var i =0
                do{
                    val row = mutableListOf<String>()

                    row.add(it.category.id.toString())
                    row.add(it.category.title)
                    row.add(it.category.type.toString())
                    row.add(it.category.icon)
                    row.add(it.category.color.toString())
                    row.add(it.category.order.toString())


                    if(finances.isNotEmpty()){
                        val finance = finances[i]
                        row.add(finance.id.toString())
                        row.add(finance.date.toString())
                        row.add(finance.amount.toString())
                    }else{
                        row.add("")
                        row.add("")
                        row.add("")
                    }
                    data.add(row)

                    i++
                }while(i < finances.size)
            }
            createFile(path, data)
        }
    }

    private fun createFile(path: String, data: MutableList<MutableList<String>>){
        val calendar = Calendar.getInstance()
        var name = ""
        name += calendar[Calendar.DAY_OF_MONTH].toString()
        name += "-"
        name += calendar[Calendar.MONTH].toString()
        name += "-"
        name += calendar[Calendar.YEAR].toString()
        name += "-"
        name += calendar[Calendar.HOUR_OF_DAY].toString()
        name += "-"
        name += calendar[Calendar.MINUTE].toString()
        name += ".csv"
        val file = File(path, name)
        if(file.exists())file.delete()


        csvWriter().writeAll(data, file, false)
    }
}