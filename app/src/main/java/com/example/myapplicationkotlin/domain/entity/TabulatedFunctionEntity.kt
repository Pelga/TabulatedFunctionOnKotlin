package com.example.myapplicationkotlin.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabulated_function_table_name")
data class TabulatedFunctionEntity (@PrimaryKey() var x: Double, var y: Double)