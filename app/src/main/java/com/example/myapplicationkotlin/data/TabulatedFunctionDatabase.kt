package com.example.myapplicationkotlin.data

import androidx.room.Database
import androidx.room.RoomDatabase

import com.example.myapplicationkotlin.domain.entity.TabulatedFunctionEntity

@Database(entities = [TabulatedFunctionEntity::class], version = 1)
open abstract class TabulatedFunctionDatabase : RoomDatabase() {
    abstract fun tabulatedFunctionDao(): TabulatedFunctionDao?
}