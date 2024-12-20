package com.example.project4.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.project4.model.MyImages
import com.example.project4.room.MyImagesDao
import com.example.project4.room.MyImagesDatabase

class MyImagesRepository(application: Application) {

    var myImagesDao : MyImagesDao
    var imagesList : LiveData<List<MyImages>>

    init {
        val database = MyImagesDatabase.getDatabaseInstance(application)
        myImagesDao = database.myImagesDao()
        imagesList = myImagesDao.getAllImages()
    }

    suspend fun insert(myImages: MyImages){
        myImagesDao.insert(myImages)
    }
    suspend fun update(myImages: MyImages){
        myImagesDao.update(myImages)
    }
    suspend fun delete(myImages: MyImages){
        myImagesDao.delete(myImages)
    }

    fun getAllImages() : LiveData<List<MyImages>>{
        return imagesList
    }

    suspend fun getItemById(id:Int):MyImages{
        return myImagesDao.getItemById(id)
    }

}