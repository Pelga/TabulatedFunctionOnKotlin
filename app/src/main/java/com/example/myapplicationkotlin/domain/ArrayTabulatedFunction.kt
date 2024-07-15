package com.example.myapplicationkotlin.domain

import com.example.myapplicationkotlin.domain.Constants.Companion.CLOSE
import com.example.myapplicationkotlin.domain.Constants.Companion.COMMA
import com.example.myapplicationkotlin.domain.Constants.Companion.OPEN
import java.io.Serializable

@Suppress("UNREACHABLE_CODE")
class ArrayTabulatedFunction : Serializable {
    var arrayFPX: Array<FunctionPoint?>
    var leftX: Double = 0.0
    var rightX: Double = 0.0


    constructor(leftX: Double, rightX: Double, pointsCount: Int) {
        if (leftX >= rightX && pointsCount < 2) {
            throw IllegalArgumentException()
        }
        this.leftX = leftX
        this.rightX = rightX
        arrayFPX = arrayOfNulls(pointsCount)
        val del = (rightX - leftX) / pointsCount
        for (i in 0 until pointsCount) {
            arrayFPX[i] = FunctionPoint()
            if (i == 0) {
                arrayFPX[i]!!.x = leftX
            } else {
                arrayFPX[i]!!.x = del + arrayFPX[i - 1]!!.x
            }
        }
    }

    constructor(arrayFPX: Array<FunctionPoint?>) {
        this.arrayFPX = arrayFPX
    }

    fun getPoint(index: Int): FunctionPoint? {
        return arrayFPX[index]
    }

    fun getPointsCount(): Int {
        return arrayFPX.size
    }

    override fun toString(): String {
        var str: String = OPEN
        val pointsCount = arrayFPX.size
        for (i in 0 until pointsCount) {
            if (i == pointsCount - 1) {
                if (arrayFPX[i] != null) {
                    str = str + arrayFPX[i].toString()
                }
            } else {
                if (arrayFPX[i] != null) {
                    str = str + arrayFPX[i].toString() + COMMA
                }
            }
        }
        return str + CLOSE
    }
}