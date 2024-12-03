package com.github.matto1matteo

import kotlin.math.abs

class Record(private val levels: List<Int>)
{
    fun isSafe() : Boolean {
        if (levels != levels.sorted() && levels != levels.sortedDescending()) {
            return false
        }

        for ((index, value) in levels.withIndex()) {
            if ((index + 1) == levels.count() ) {
                return true
            }

            val diff = abs(value - levels[index+1])
            if (diff == 0 || diff > 3) {
                return false
            }
        }
        return true
    }

    fun isSafe2() : Boolean {
        if (isSafe()) {
            return true
        }

        for (i in levels.indices) {
            val newLevels = levels.withIndex().filter { (idx, v) -> idx != i} .map { (idx, v) -> v }
            if (Record(newLevels).isSafe()) {
                return true
            }
        }
        return false
    }
}

class Day02(override var fileName: String) : Problem {
    override fun firstSolution(): String {
        val input = this.javaClass.getResourceAsStream("/$fileName")?.bufferedReader()?.readLines()
        val records = input?.map { l -> Record(l.split(" ").map { it.toInt() })}

        return records?.count { r -> r.isSafe() }.toString()
    }

    override fun secondSolution(): String {
        val input = this.javaClass.getResourceAsStream("/$fileName")?.bufferedReader()?.readLines()
        val records = input?.map { l -> Record(l.split(" ").map { it.toInt() })}

        return records?.count { r -> r.isSafe2() }.toString()
    }
}