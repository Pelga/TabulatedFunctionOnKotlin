package com.example.myapplicationkotlin.domain

interface TabulatedFunctionUseCaseCallback {
    fun onSuccess(arrayTabulatedFunction: ArrayTabulatedFunction?)
    fun onFailure(throwable: Throwable?)
}