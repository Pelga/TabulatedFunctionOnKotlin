package com.example.myapplicationkotlin.ui

import android.widget.EditText

class TabulatedFunctionString {
    companion object {
        fun toStr(editText: EditText): String {
            return editText.text.toString()
        }
    }
}