package com.example.myapplicationkotlin.ui

import android.view.View
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplicationkotlin.R
import com.example.myapplicationkotlin.data.TabulatedFunctionRepository
import com.example.myapplicationkotlin.domain.ArrayTabulatedFunction
import com.example.myapplicationkotlin.domain.Constants.ARRAY
import com.example.myapplicationkotlin.domain.Constants.NULL
import com.example.myapplicationkotlin.domain.TabulatedFunctionUseCase
import com.example.myapplicationkotlin.domain.TabulatedFunctionUseCaseCallback
import com.example.myapplicationkotlin.ui.TabulatedFunctionString.toStr


class MainActivityViewModel : ViewModel() {

    val arrayTabulatedFunctionLiveData = MutableLiveData<ArrayTabulatedFunction>()
    val makeErrorToastLiveData = MutableLiveData<Int>()
    val closeKeyboardLiveData = MutableLiveData<View>()
    val visibilityLiveData = MutableLiveData<Int>()
    val closeCardViewLiveData = MutableLiveData<Boolean>()

    private val myRepository = TabulatedFunctionRepository()
    private val useCase = TabulatedFunctionUseCase(myRepository)

    private fun generatedArrayFunction(x: Double, y: Double, p: Int) {
        arrayTabulatedFunctionLiveData.value = ArrayTabulatedFunction(x, y, p)
    }

    private fun closeKeyboardAndMakeInvisible(view: View, inter: Int) {
        closeKeyboardLiveData.value = view
        visibilityLiveData.value = inter
    }

    fun materialButtonGeneratePressed(view: View, inter: Int, closeCardView: Boolean) {
        this.closeKeyboardAndMakeInvisible(view, inter)
        closeCardViewLiveData.value = closeCardView
        createTabulatedFunctionByRequest()
    }

    fun materialButtonDownloadPressed(view: View, inter: Int, closeCardView: Boolean) {
        closeKeyboardAndMakeInvisible(view, inter)
        closeCardViewLiveData.value = closeCardView
        createTabulatedFunctionByDatabase()
    }

    fun materialButtonCreatePressed(
        r: EditText,
        l: EditText,
        p: EditText,
        view: View,
        integer: Int
    ) {
        if (toStr(r).trim() == NULL || toStr(l) == NULL || toStr(p) == ARRAY) {
            createErrorToast()
        } else {
            closeKeyboardAndMakeInvisible(view, integer)
            closeCardViewLiveData.value = true
            val left = toStr(l).toDouble()
            val right = toStr(r).toDouble()
            val pointsCount = toStr(p).toInt()
            generatedArrayFunction(left, right, pointsCount)
        }
    }

    private fun createErrorToast() {
        makeErrorToastLiveData.value = R.string.error
    }

    fun createFailureToast() {
        makeErrorToastLiveData.value = R.string.failure
    }

    private fun createTabulatedFunctionByRequest() {
        useCase.getArrayTabulatedFunction(object :
            TabulatedFunctionUseCaseCallback {
            override fun onSuccess(arrayTabulatedFunction: ArrayTabulatedFunction?) {
                arrayTabulatedFunctionLiveData.value = arrayTabulatedFunction
            }

            override fun onFailure(throwable: Throwable?) {
                createFailureToast()
            }
        })
    }

    private fun createTabulatedFunctionByDatabase() {
        arrayTabulatedFunctionLiveData.value = useCase.getArrayTabulatedFunctionDataBase()
    }
}
