package com.example.myapplicationkotlin.app

import android.app.Application
import androidx.room.Room
import com.example.myapplicationkotlin.data.TabulatedFunctionDatabase
import com.example.myapplicationkotlin.domain.Constants.Companion.DATABASE

class App : Application() {
    lateinit var database: TabulatedFunctionDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, TabulatedFunctionDatabase::class.java, DATABASE)
            .allowMainThreadQueries().build()
    }

    companion object {
        @get:Synchronized
        lateinit var instance: App
            private set
    }
}