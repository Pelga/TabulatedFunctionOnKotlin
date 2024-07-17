package com.example.myapplicationkotlin.domain

import com.example.myapplicationkotlin.domain.Constants.CLOSE_ANOTHER
import com.example.myapplicationkotlin.domain.Constants.COMMA
import com.example.myapplicationkotlin.domain.Constants.OPEN_ANOTHER
import java.io.Serializable

class LinkedListTabulatedFunction : Serializable {

    private var head: FunctionNode = FunctionNode()
    private var lastIndex: Int = -1
    private var leftX: Double = 0.0
    private var rightX: Double = 0.0
    private var values: Array<Double>? = null

    constructor(array: Array<FunctionPoint>) {
        val pointsCount: Int = array.size
        var buffer: FunctionNode = head
        for (i in 0 until pointsCount) {
            buffer.item.x = array[i].x
            buffer.item.y = array[i].y
            buffer.next = FunctionNode()
            buffer = buffer.next!!
            if (i != pointsCount - 1 && array[i].x > array[i + 1].x) {
                throw IllegalArgumentException()
            }
        }
        if (pointsCount < 2) {
            throw IllegalArgumentException()
        }
    }

    constructor(leftX: Double, rightX: Double, pointsCount: Int) {
        if (leftX >= rightX && pointsCount < 2) {
            throw IllegalArgumentException()
        }
        this.leftX = leftX
        this.rightX = rightX
        var allLength: FunctionNode = head
        var i = 0
        val del: Double = (rightX - leftX) / pointsCount
        while (i < pointsCount) {
            allLength.item = FunctionPoint()
            allLength.next = FunctionNode(allLength, FunctionPoint(), null, i)
            if (i == 0) {
                allLength.item.x = leftX
            } else {
                allLength.item.x = allLength.prev!!.item.x + del
            }
            i++
            allLength = allLength.next!!
        }
    }

    constructor(leftX: Double, rightX: Double, values: Array<Double>) {
        if (leftX >= rightX && values.size < 2) {
            throw IllegalArgumentException()
        }
        this.leftX = leftX
        this.rightX = rightX
        this.values = values
        head = FunctionNode()
        var allLength: FunctionNode = head
        var i = 0
        val pointsCount: Int = values.size
        val del: Double = (rightX - leftX) / pointsCount
        while (i < pointsCount) {
            allLength.item = FunctionPoint()
            if (i == 0) {
                leftX.also { allLength.item.x = it }
            } else {
                allLength.item.x = del + allLength.prev!!.item.x
            }
            allLength.item.x = values[i]
            allLength.next = FunctionNode()
            allLength.next!!.prev = allLength
            allLength = allLength.next!!
            i++
        }
    }

    fun returnNumb(x: Double): Array<Double>? {
        var allLength: FunctionNode = head
        while (allLength.next != null) {
            if (x < allLength.item.x && x > allLength.prev!!.item.x) {
                return arrayOf(
                    allLength.item.x,
                    allLength.prev!!.item.x,
                    allLength.item.y,
                    allLength.prev!!.item.y
                )
            }
            allLength = allLength.next!!
        }
        return null
    }

    private fun getPointsCount(): Int {
        var allLength: FunctionNode = head
        var i: Int = 0
        while (allLength.next != null) {
            i++
            allLength = allLength.next!!
        }
        return i
    }

    private fun getPoint(index: Int): FunctionPoint {
        var allLength: FunctionNode = head
        var i: Int = 0

        while (i < index) {
            i++
            allLength = allLength.next!!
        }
        return allLength.item
    }

    fun getPointX(index: Int): Double {
        return this.getPoint(index).x
    }

    fun getPointY(index: Int): Double {
        return this.getPoint(index).y
    }

    fun addPoint(point: FunctionPoint) {
        var i = 0
        var allLength: FunctionNode = head
        while (allLength.item.x <= point.x) {
            i++
            allLength = allLength.next!!
        }
        addNodeByIndex(i, point)
    }

    /*******************************************************************************************/

    class FunctionNode : Serializable {
        var item: FunctionPoint
        var next: FunctionNode?
        var prev: FunctionNode?
        var index: Int


        constructor() {
            this.index = 0
            this.item = FunctionPoint()
            this.next = null
            this.prev = null
        }

        constructor(prev: FunctionNode, item: FunctionPoint, next: FunctionNode?, index: Int) {
            this.index = index
            this.item = item
            this.next = next
            this.prev = prev
        }
    }

    /*******************************************************************************************/

    fun getNodeByIndex(index: Int): FunctionNode {
        val currentElement: FunctionNode = head.next!!
        while (index != currentElement.index) {
            currentElement.next
        }
        return currentElement
    }

    fun addNodeToTail(functionPoint: FunctionPoint): FunctionNode {
        lastIndex += 1
        var tail: FunctionNode = head
        while (tail.next != null) {
            tail = tail.next!!
        }
        tail.item = functionPoint
        tail.index = lastIndex
        return tail
    }

    private fun addNodeByIndex(index: Int, functionPoint: FunctionPoint): FunctionNode {
        val prevIndex: FunctionNode = if (index == 0) {
            head
        } else {
            getNodeByIndex(index - 1)
        }
        val newIndex: FunctionNode = getNodeByIndex(index)
        val newObject = FunctionNode(prevIndex, functionPoint, newIndex, index)
        newIndex.prev = newObject
        prevIndex.next = newObject
        var objectForNext: FunctionNode = newIndex
        while (objectForNext.next != null) {
            objectForNext.index = objectForNext.index + 1
            objectForNext = objectForNext.next!!
        }
        return newObject
    }

    override fun toString(): String {
        var str: String = OPEN_ANOTHER
        var tail: FunctionNode = head
        while (tail.next != null) {
            if (tail.next!!.next != null) {
                str = str + tail.item.toString() + COMMA
                tail = tail.next!!
            } else {
                str += tail.item.toString()
                tail = tail.next!!
            }
        }
        tail.item = FunctionPoint()
        return str + CLOSE_ANOTHER
    }

    override fun hashCode(): Int {
        var result: Int = 1
        var tail: FunctionNode = head
        while (tail.next != null) {
            if (tail.item.hashCode() != 0) {
                result += tail.item.hashCode()
            }
            tail = tail.next!!
        }
        if (leftX.toInt() != 0) {
            result += leftX.toBits().toInt()
        } else {
            result += 55555
        }
        if (rightX != 0.0) {
            result += rightX.toBits().toInt()
        } else {
            result += 55555
        }
        return result
    }

    fun clone(): Any {
        var tail = head
        var i = 0
        val array = Array(getPointsCount()) { FunctionPoint() }
        if (leftX == 0.0 && rightX == 0.0) {
            while (tail.next != null) {
                array[i] = tail.item
                tail = tail.next!!
                i++
            }
            return LinkedListTabulatedFunction(array)
        }
        return values?.let { LinkedListTabulatedFunction(leftX, rightX, it) }
            ?: LinkedListTabulatedFunction(leftX, rightX, getPointsCount())
    }
}

