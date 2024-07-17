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
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService: TabulatedFunctionAPI = retrofit.create(TabulatedFunctionAPI::class.java)
        val call: Call<TabulatedFunctionDateNumbers> = apiService.getMyData()
        call.enqueue(object : Callback<TabulatedFunctionDateNumbers?> {
            override fun onResponse(
                call: Call<TabulatedFunctionDateNumbers?>,
                response: Response<TabulatedFunctionDateNumbers?>
            ) {
                val arrayTabulatedFunction: ArrayTabulatedFunction
                if (response.isSuccessful) {
                    data = response.body()
                    assert(data != null)
                    arrayTabulatedFunction = ArrayTabulatedFunction(data!!.numbers)
                    myRepositoryCallback.onSuccess(arrayTabulatedFunction)
                } else {
                    myRepositoryCallback.onFailure(Throwable("error response"))
                }
            }

            override fun onFailure(call: Call<TabulatedFunctionDateNumbers?>, t: Throwable) {
                call.cancel()
                myRepositoryCallback.onFailure(t)
            }
        })
    }

    fun createTabulatedFunctionByDatabase(): ArrayTabulatedFunction {
        val db: TabulatedFunctionDatabase = App.instance.database
        val dao = db.tabulatedFunctionDao()
        val entity: List<TabulatedFunctionEntity> = dao!!.getAll()
        val arrayTabulatedFunction = ArrayTabulatedFunction(toFunctionArray(entity))
        for (i in entity.indices) {
            dao.delete(entity[i])
        }
        return arrayTabulatedFunction
    }

    private fun toFunctionArray(entity: List<TabulatedFunctionEntity?>?): Array<FunctionPoint?> {
        val array: Array<FunctionPoint?> = arrayOfNulls(entity!!.size)
        for (i in entity.indices) {
            array[i] = entity[i]?.let { toFunctionPoint(it) }
        }
        return array
    }

    private fun toFunctionPoint(entity: TabulatedFunctionEntity): FunctionPoint {
        val fp = FunctionPoint()
        fp.x = entity.x
        fp.y = entity.y
        return fp
    }

    fun buttonDatabasePressed(list: ArrayList<FunctionPoint>) {
        val database: TabulatedFunctionDatabase = App.instance.database
        val dao = database.tabulatedFunctionDao()
        for (i in list.indices) {
            val entity = TabulatedFunctionEntity(list[i].x, list[i].y)
            dao!!.insert(entity)
        }
    }
}