package com.example.myapplicationkotlin.domain

import com.example.myapplicationkotlin.domain.Constants.CLOSE_ANOTHER
import com.example.myapplicationkotlin.domain.Constants.COMMA
import com.example.myapplicationkotlin.domain.Constants.OPEN_ANOTHER
import java.io.Serializable

class LinkedListTabulatedFunction : Serializable {

    private var head = FunctionNode()
    private var lastIndex = -1
    private var leftX = 0.0
    private var rightX = 0.0
    private var values: Array<Double>? = null

    constructor(array: Array<FunctionPoint>) {
        val pointsCount = array.size
        var buffer = head
        for (i in 0 until pointsCount) {
            buffer.item.x = array[i].x
            buffer.item.y = array[i].y
            buffer.next = FunctionNode()
            buffer = buffer.next ?: throw IllegalArgumentException("Next node is null")
            if (i != pointsCount - 1 && array[i].x > array[i + 1].x) {
                throw IllegalArgumentException("Points are not sorted")
            }
        }
        if (pointsCount < 2) {
            throw IllegalArgumentException("Not enough points")
        }
    }

    constructor(leftX: Double, rightX: Double, pointsCount: Int) {
        if (leftX >= rightX || pointsCount < 2) {
            throw IllegalArgumentException("Invalid input parameters")
        }
        this.leftX = leftX
        this.rightX = rightX
        var allLength = head
        val del = (rightX - leftX) / pointsCount
        for (i in 0 until pointsCount) {
            allLength.item = FunctionPoint()
            allLength.next = FunctionNode(allLength, FunctionPoint(), null, i)
            if (i == 0) {
                allLength.item.x = leftX
            } else {
                allLength.item.x = allLength.prev?.item?.x?.plus(del)
                    ?: throw IllegalArgumentException("Previous node is null")
            }
            allLength = allLength.next ?: throw IllegalArgumentException("Next node is null")
        }
    }

    constructor(leftX: Double, rightX: Double, values: Array<Double>) {
        if (leftX >= rightX || values.size < 2) {
            throw IllegalArgumentException("Invalid input parameters")
        }
        this.leftX = leftX
        this.rightX = rightX
        this.values = values
        head = FunctionNode()
        var allLength = head
        val pointsCount = values.size
        val del = (rightX - leftX) / pointsCount
        for (i in 0 until pointsCount) {
            allLength.item = FunctionPoint()
            if (i == 0) {
                allLength.item.x = leftX
            } else {
                allLength.item.x = del + (allLength.prev?.item?.x ?: throw IllegalArgumentException(
                    "Previous node is null"
                ))
            }
            allLength.item.y = values[i]
            allLength.next = FunctionNode()
            allLength.next?.prev = allLength
            allLength = allLength.next ?: throw IllegalArgumentException("Next node is null")
        }
    }

    fun returnNumb(x: Double): Array<Double>? {
        var allLength = head
        while (allLength.next != null) {
            val prevItemX = allLength.prev?.item?.x ?: return null
            if (x < allLength.item.x && x > prevItemX) {
                return arrayOf(
                    allLength.item.x,
                    prevItemX,
                    allLength.item.y,
                    allLength.prev?.item?.y ?: return null
                )
            }
            allLength = allLength.next ?: return null
        }
        return null
    }

    private fun getPointsCount(): Int {
        var allLength = head
        var i = 0
        while (allLength.next != null) {
            i++
            allLength = allLength.next ?: return i
        }
        return i
    }

    private fun getPoint(index: Int): FunctionPoint {
        var allLength = head
        var i = 0

        while (i < index) {
            i++
            allLength = allLength.next ?: throw IndexOutOfBoundsException("Index out of bounds")
        }
        return allLength.item
    }

    fun getPointX(index: Int) = this.getPoint(index).x

    fun getPointY(index: Int) = this.getPoint(index).y

    fun addPoint(point: FunctionPoint) {
        var i = 0
        var allLength = head
        while (allLength.item.x <= point.x) {
            i++
            allLength = allLength.next ?: break
        }
        addNodeByIndex(i, point)
    }

    /**********************************************************/
    class FunctionNode(
        var prev: FunctionNode? = null,
        var item: FunctionPoint = FunctionPoint(),
        var next: FunctionNode? = null,
        var index: Int = 0
    ) : Serializable

    /**********************************************************/

    private fun getNodeByIndex(index: Int): FunctionNode {
        var currentElement = head.next ?: throw IndexOutOfBoundsException("No such index")
        while (index != currentElement.index) {
            currentElement = currentElement.next ?: throw IndexOutOfBoundsException("No such index")
        }
        return currentElement
    }

    fun addNodeToTail(functionPoint: FunctionPoint): FunctionNode {
        lastIndex += 1
        var tail = head
        while (tail.next != null) {
            tail = tail.next ?: throw IllegalArgumentException("Next node is null")
        }
        tail.item = functionPoint
        tail.index = lastIndex
        return tail
    }

    private fun addNodeByIndex(index: Int, functionPoint: FunctionPoint): FunctionNode {
        val prevIndex = if (index == 0) {
            head
        } else {
            getNodeByIndex(index - 1)
        }
        val newIndex = getNodeByIndex(index)
        val newObject = FunctionNode(prevIndex, functionPoint, newIndex, index)
        newIndex.prev = newObject
        prevIndex.next = newObject
        var objectForNext = newIndex
        while (objectForNext.next != null) {
            objectForNext.index += 1
            objectForNext =
                objectForNext.next ?: throw IllegalArgumentException("Next node is null")
        }
        return newObject
    }

    override fun toString(): String {
        var str = OPEN_ANOTHER
        var tail = head
        while (tail.next != null) {
            str += if (tail.next?.next != null) {
                "${tail.item}$COMMA"
            } else {
                tail.item.toString()
            }
            tail = tail.next ?: break
        }
        tail.item = FunctionPoint()
        return "$str$CLOSE_ANOTHER"
    }

    override fun hashCode(): Int {
        var result = 1
        var tail = head
        while (tail.next != null) {
            result += tail.item.hashCode()
            tail = tail.next ?: break
        }
        result += leftX.toBits().toInt().takeIf { leftX != 0.0 } ?: 55555
        result += rightX.toBits().toInt().takeIf { rightX != 0.0 } ?: 55555
        return result
    }

    fun clone(): Any {
        val array = Array(getPointsCount()) { FunctionPoint() }
        var tail = head
        var i = 0
        if (leftX == 0.0 && rightX == 0.0) {
            while (tail.next != null) {
                array[i] = tail.item
                tail = tail.next ?: break
                i++
            }
            return LinkedListTabulatedFunction(array)
        }
        return values?.let { LinkedListTabulatedFunction(leftX, rightX, it) }
            ?: LinkedListTabulatedFunction(leftX, rightX, getPointsCount())
    }
}
