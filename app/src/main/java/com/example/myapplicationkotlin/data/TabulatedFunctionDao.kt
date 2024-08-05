package com.example.myapplicationkotlin.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.example.myapplicationkotlin.domain.entity.TabulatedFunctionEntity

@Dao
interface TabulatedFunctionDao {
    @Query("SELECT * FROM tabulated_function_table_name")
    fun getAll(): List<TabulatedFunctionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: TabulatedFunctionEntity)

    @Update
    fun update(tabulatedFunctionEntity: TabulatedFunctionEntity)

    @Delete
    fun delete(tabulatedFunctionEntity: TabulatedFunctionEntity)
}