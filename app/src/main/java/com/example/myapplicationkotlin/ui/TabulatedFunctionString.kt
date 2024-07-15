package com.example.myapplicationkotlin.ui

import android.widget.EditText

open class TabulatedFunctionString {
    companion object {
        fun toStr(editText: EditText): String {
            return editText.text.toString()
        }
    }
}