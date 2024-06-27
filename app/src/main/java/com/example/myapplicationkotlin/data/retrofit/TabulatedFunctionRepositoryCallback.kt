package com.example.myapplicationkotlin.data.retrofit

import com.example.myapplicationkotlin.domain.ArrayTabulatedFunction

interface TabulatedFunctionRepositoryCallback {
    fun onSuccess(arrayTabulatedFunction: ArrayTabulatedFunction?)
    fun onFailure(throwable: Throwable?)
}