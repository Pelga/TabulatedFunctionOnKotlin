package com.example.myapplicationkotlin.data

import TabulatedFunctionAPI
import com.example.myapplicationkotlin.app.App
import com.example.myapplicationkotlin.data.retrofit.TabulatedFunctionRepositoryCallback
import com.example.myapplicationkotlin.domain.ArrayTabulatedFunction
import com.example.myapplicationkotlin.domain.FunctionPoint
import com.example.myapplicationkotlin.domain.TabulatedFunctionDateNumbers
import com.example.myapplicationkotlin.domain.entity.TabulatedFunctionEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TabulatedFunctionRepository {

    private val BASE_URL = "https://run.mocky.io/"

    var data: TabulatedFunctionDateNumbers? = null

    fun createTabulatedFunctionByRequest(myRepositoryCallback: TabulatedFunctionRepositoryCallback) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(TabulatedFunctionAPI::class.java)
        val call = apiService.getMyData()
        call.enqueue(object : Callback<TabulatedFunctionDateNumbers?> {
            override fun onResponse(
                call: Call<TabulatedFunctionDateNumbers?>,
                response: Response<TabulatedFunctionDateNumbers?>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val numbers = data?.numbers?.filterNotNull()
                    val arrayTabulatedFunction = numbers?.let { nums ->
                        ArrayTabulatedFunction(nums.toTypedArray())
                    }
                    if (arrayTabulatedFunction != null) {
                        myRepositoryCallback.onSuccess(arrayTabulatedFunction)
                    } else {
                        myRepositoryCallback.onFailure(Throwable("Error creating ArrayTabulatedFunction"))
                    }
                } else {
                    myRepositoryCallback.onFailure(Throwable("Error response"))
                }
            }

            override fun onFailure(call: Call<TabulatedFunctionDateNumbers?>, t: Throwable) {
                myRepositoryCallback.onFailure(t)
            }
        })
    }

    fun createTabulatedFunctionByDatabase(): ArrayTabulatedFunction? {
        val db = App.instance.database
        val dao = db.tabulatedFunctionDao()
        if (dao == null) {
            println("TabulatedFunctionDao is null")
            return null
        }

        val entity = dao.getAll()
        val arrayTabulatedFunction = ArrayTabulatedFunction(toFunctionArray(entity))

        db.runInTransaction {
            for (i in entity.indices) {
                dao.delete(entity[i])
            }
        }
        return arrayTabulatedFunction
    }

    private fun toFunctionArray(entity: List<TabulatedFunctionEntity?>?): Array<FunctionPoint?> {
        if (entity.isNullOrEmpty()) {
            return emptyArray()
        }

        val array: Array<FunctionPoint?> = Array(entity.size) { FunctionPoint() }
        for (i in entity.indices) {
            array[i] = toFunctionPoint(entity[i])
        }
        return array
    }

    private fun toFunctionPoint(entity: TabulatedFunctionEntity?): FunctionPoint {
        val fp = FunctionPoint()
        if (entity != null) {
            fp.x = entity.x
            fp.y = entity.y
        }
        return fp
    }

    fun buttonDatabasePressed(list: ArrayList<FunctionPoint>) {
        val database = App.instance.database
        val dao = database.tabulatedFunctionDao()
        for (i in list.indices) {
            val entity = TabulatedFunctionEntity(list[i].x, list[i].y)
            dao?.insert(entity)
        }
    }
}