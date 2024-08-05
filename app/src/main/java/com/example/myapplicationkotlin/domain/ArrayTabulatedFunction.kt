package com.example.myapplicationkotlin.domain

import com.example.myapplicationkotlin.domain.Constants.CLOSE
import com.example.myapplicationkotlin.domain.Constants.COMMA
import com.example.myapplicationkotlin.domain.Constants.OPEN
import java.io.Serializable

@Suppress("UNREACHABLE_CODE")
class ArrayTabulatedFunction : Serializable {
    private var arrayFPX: Array<FunctionPoint?>
    private var leftX: Double = 0.0
    private var rightX: Double = 0.0


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
                arrayFPX[i]?.x = leftX
            } else {
                arrayFPX[i]?.x = del + (arrayFPX[i - 1]?.x ?: 0.0)
            }
        }
    }

    constructor(arrayFPX: Array<FunctionPoint?>) {
        this.arrayFPX = arrayFPX
    }

    fun getPoint(index: Int) = arrayFPX[index]


    fun getPointsCount() = arrayFPX.size


    override fun toString(): String {
        var str = OPEN
        val pointsCount = arrayFPX.size
        for (i in 0 until pointsCount) {
            if (i == pointsCount - 1) {
                str += arrayFPX[i].toString()
            } else {
                str = str + arrayFPX[i].toString() + COMMA
            }
        }
        return str + CLOSE
    }
}