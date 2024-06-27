package com.example.myapplicationkotlin.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplicationkotlin.R
import com.example.myapplicationkotlin.data.TabulatedFunctionRepository
import com.example.myapplicationkotlin.domain.ArrayTabulatedFunction
import com.example.myapplicationkotlin.domain.FunctionPoint
import com.example.myapplicationkotlin.domain.TabulatedFunctionUseCase

class TabulatedFunctionFragmentViewModel : ViewModel() {
    val makeToastLiveData: MutableLiveData<Int> = MutableLiveData()
    val arrayTabulatedFunctionMutableLiveData: MutableLiveData<ArrayTabulatedFunction> =
        MutableLiveData()
    val openDialogFragmentLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val myRepository: TabulatedFunctionRepository = TabulatedFunctionRepository()
    val useCase: TabulatedFunctionUseCase = TabulatedFunctionUseCase(myRepository)

    open fun setArrayTabulatedFunction(arrayTabulatedFunction: ArrayTabulatedFunction) {
        arrayTabulatedFunctionMutableLiveData.setValue(arrayTabulatedFunction)
    }

    open fun createToast() {
        makeToastLiveData.setValue(R.string.saved)
    }

    open fun buttonDatabasePressed(list: ArrayList<FunctionPoint>) {
        useCase.getButtonDatabasePressed(list)
        createToast()
    }

    open fun addButtonPressed(bool: Boolean) {
        openDialogFragmentLiveData.setValue(bool)
    }

    open fun positiveButtonPressed(x: Double, y: Double) {
        var functionPoint = FunctionPoint(x, y)
        var array: ArrayTabulatedFunction = arrayTabulatedFunctionMutableLiveData.getValue()!!//
        val functionPoints = Array<FunctionPoint?>(array.getPointsCount() + 1) { FunctionPoint() }
        for (i in 0..array.getPointsCount() - 1) {
            functionPoints[i] = array.getPoint(i)!!
        }
        functionPoints[functionPoints.size - 1] = functionPoint
        array = ArrayTabulatedFunction(functionPoints)
        arrayTabulatedFunctionMutableLiveData.setValue(array)
    }
}