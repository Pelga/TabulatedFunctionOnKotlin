package com.example.myapplicationkotlin.ui

import android.view.View
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplicationkotlin.R
import com.example.myapplicationkotlin.data.TabulatedFunctionRepository
import com.example.myapplicationkotlin.domain.ArrayTabulatedFunction
import com.example.myapplicationkotlin.domain.Constants.Companion.ARRAY
import com.example.myapplicationkotlin.domain.Constants.Companion.NULL
import com.example.myapplicationkotlin.domain.TabulatedFunctionUseCase
import com.example.myapplicationkotlin.ui.TabulatedFunctionString.Companion.toStr


class MainActivityViewModel : ViewModel() {

    val arrayTabulatedFunctionLiveData = MutableLiveData<ArrayTabulatedFunction>()
    val makeErrorToastLiveData = MutableLiveData<Int>()
    val closeKeyboardLiveData = MutableLiveData<View>()
    val visibilityLiveData = MutableLiveData<Int>()
    val closeCardViewLiveData = MutableLiveData<Boolean>()

    val myRepository = TabulatedFunctionRepository()
    val useCase = TabulatedFunctionUseCase(myRepository)

    fun generatedArrayFunction(x: Double, y: Double, p: Int) {
        arrayTabulatedFunctionLiveData.setValue(ArrayTabulatedFunction(x, y, p))
    }

    fun closeKeyboardAndMakeInvisible(view: View, inter: Int) {
        closeKeyboardLiveData.setValue(view)
        visibilityLiveData.setValue(inter)
    }


    fun materialButtonGeneratePressed(view: View, inter: Int, closeCardView: Boolean) {
        closeKeyboardAndMakeInvisible(view, inter)
        closeCardViewLiveData.setValue(closeCardView)
        createTabulatedFunctionByRequest()
    }

    fun materialButtonDownloadPressed(view: View, inter: Int, closeCardView: Boolean) {
        closeKeyboardAndMakeInvisible(view, inter)
        closeCardViewLiveData.setValue(closeCardView)
        createTabulatedFunctionByDatabase()
    }

    fun materialButtonCreatePressed(
        r: EditText,
        l: EditText,
        p: EditText,
        view: View,
        integer: Int
    ) {
        if (toStr(r).trim()
                .equals(NULL) || toStr(l).equals(NULL) || toStr(p).equals(ARRAY)
        ) {
            createErrorToast()
        } else {
            closeKeyboardAndMakeInvisible(view, integer)
            closeCardViewLiveData.setValue(true)
            val left: Double = toStr(l).toDouble()
            val right: Double = toStr(r).toDouble()
            val pointsCount: Int = toStr(p).toInt()
            generatedArrayFunction(left, right, pointsCount)
        }
    }

    fun createErrorToast() {
        makeErrorToastLiveData.setValue(R.string.error)
    }

    fun createFailureToast() {
        makeErrorToastLiveData.setValue(R.string.failure)
    }

    fun createTabulatedFunctionByRequest() {
        useCase.getArrayTabulatedFunction(object :
            TabulatedFunctionUseCase.TabulatedFunctionUseCaseCallback {
            override fun onSuccess(arrayTabulatedFunction: ArrayTabulatedFunction?) {
                arrayTabulatedFunctionLiveData.setValue(arrayTabulatedFunction)
            }

            override fun onFailure(throwable: Throwable?) {
                createFailureToast()
            }
        })
    }

    fun createTabulatedFunctionByDatabase() {
        arrayTabulatedFunctionLiveData.setValue(useCase.getArrayTabulatedFunctionDataBase())
    }
}
