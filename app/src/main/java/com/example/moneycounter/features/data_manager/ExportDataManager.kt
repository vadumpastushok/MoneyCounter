package com.example.moneycounter.features.data_manager

import com.example.moneycounter.app.Config
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Finance
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File
import java.util.*
import javax.inject.Inject

class ExportDataManager @Inject constructor(
    private val databaseManager: DatabaseManager
    ){

    suspend fun convertDB(path: String){
        val data: MutableList<MutableList<String>> = mutableListOf()
        data.add(Config.tableHeader)

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