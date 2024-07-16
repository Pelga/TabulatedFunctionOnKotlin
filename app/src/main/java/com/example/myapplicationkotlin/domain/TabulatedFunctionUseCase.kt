package com.example.myapplicationkotlin.domain

import com.example.myapplicationkotlin.data.TabulatedFunctionRepository
import com.example.myapplicationkotlin.data.retrofit.TabulatedFunctionRepositoryCallback

class TabulatedFunctionUseCase {

    val myRepository: TabulatedFunctionRepository

    constructor(myRepository: TabulatedFunctionRepository) {
        this.myRepository = myRepository
    }

    fun getArrayTabulatedFunction(myUseCaseCallback: TabulatedFunctionUseCaseCallback) {
        myRepository.createTabulatedFunctionByRequest(object :
            TabulatedFunctionRepositoryCallback {
            override fun onSuccess(arrayTabulatedFunction: ArrayTabulatedFunction?) {
                myUseCaseCallback.onSuccess(arrayTabulatedFunction)
            }

            override fun onFailure(throwable: Throwable?) {
                myUseCaseCallback.onFailure(throwable)
            }
        })
    }

    fun getArrayTabulatedFunctionDataBase(): ArrayTabulatedFunction? {
        return myRepository.createTabulatedFunctionByDatabase()
    }

    fun getButtonDatabasePressed(list: ArrayList<FunctionPoint>) {
        myRepository.buttonDatabasePressed(list)
    }
}