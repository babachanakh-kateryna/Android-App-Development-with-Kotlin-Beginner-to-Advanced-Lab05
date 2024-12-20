package com.example.project3.database


import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.techmania.flagquizwithsqlitedemo.DatabaseCopyHelper

class FlagsDao {

    fun getRandomfiveRecords(helper:DatabaseCopyHelper):ArrayList<FlagsModel>{

        val recordList=ArrayList<FlagsModel>()
        val database:SQLiteDatabase=helper.writableDatabase
        val cursor:Cursor=database.rawQuery("SELECT * FROM flags ORDER BY RANDOM() LIMIT 5",null)


        val idIndex = cursor.getColumnIndex("flag_id")
        val countryIndex=cursor.getColumnIndex("country_name")
        val flagindex=cursor.getColumnIndex("flag_name")


        while(cursor.moveToNext()){
            val record=FlagsModel(
                cursor.getInt(idIndex),
                cursor.getString(countryIndex),
                cursor.getString(flagindex))

            recordList.add(record)
        }

        cursor.close()

        return recordList

    }


    fun getRandomThreeRecords(helper:DatabaseCopyHelper,id:Int):ArrayList<FlagsModel>{

        val recordList=ArrayList<FlagsModel>()
        val database:SQLiteDatabase=helper.writableDatabase
        val cursor:Cursor=database.rawQuery(
            "SELECT * FROM flags WHERE flag_id!=? ORDER BY RANDOM() LIMIT 3",
            arrayOf(id.toString()))


        val idIndex = cursor.getColumnIndex("flag_id")
        val countryIndex=cursor.getColumnIndex("country_name")
        val flagindex=cursor.getColumnIndex("flag_name")


        while(cursor.moveToNext()){
            val record=FlagsModel(
                cursor.getInt(idIndex),
                cursor.getString(countryIndex),
                cursor.getString(flagindex))

            recordList.add(record)
        }

        cursor.close()

        return recordList

    }

}