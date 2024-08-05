package com.example.myapplicationkotlin.domain

import com.example.myapplicationkotlin.domain.Constants.CLOSE_ANOTHER
import com.example.myapplicationkotlin.domain.Constants.OPEN_ANOTHER
import com.example.myapplicationkotlin.domain.Constants.SEMICOLON
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class FunctionPoint(
    @SerializedName("x")
    var x: Double = 0.0,

    @SerializedName("y")
    var y: Double = 0.0
) : Serializable {

    override fun toString() = OPEN_ANOTHER + x + SEMICOLON + y + CLOSE_ANOTHER


    override fun equals(o: Any?): Boolean {
        if (o === this) {
            return true
        }
        val c = o as FunctionPoint
        return (x.compareTo(c.x) === 0
                && y.compareTo(c.y) === 0)

    }

    override fun hashCode(): Int {
        return if (x != 0.0 && y != 0.0) {
            val bits = java.lang.Double.doubleToLongBits(x)
            val bitsY = java.lang.Double.doubleToLongBits(y)
            (bits xor (bits ushr 32)).toInt() + (bitsY xor (bitsY ushr 32)).toInt()
        } else {
            6666666
        }
    }

    @Throws(CloneNotSupportedException::class)
    fun clone(): Any {
        return FunctionPoint(x, y)
    }
}