package com.example.myapplicationkotlin.domain

import com.example.myapplicationkotlin.domain.Constants.Companion.CLOSE_ANOTHER
import com.example.myapplicationkotlin.domain.Constants.Companion.OPEN_ANOTHER
import com.example.myapplicationkotlin.domain.Constants.Companion.SEMICOLON
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class FunctionPoint : Serializable {

    @SerializedName("x")
    var x: Double

    @SerializedName("y")
    var y: Double

    constructor(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    constructor(point: FunctionPoint) {
        this.x = point.x
        this.y = point.y
    }

    constructor() {
        this.x = 0.0
        this.y = 0.0
    }

    override fun toString(): String {
        val str: String = OPEN_ANOTHER + x + SEMICOLON + y + CLOSE_ANOTHER
        return str
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) {
            return true
        }
        if (o !== this) {
            return false
        }
        val c = o as FunctionPoint
        return (java.lang.Double.compare(x, c.x) === 0
                && java.lang.Double.compare(y, c.y) === 0)
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
    fun clone(): Any? {
        return FunctionPoint(x, y)
    }
}