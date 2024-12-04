package com.github.matto1matteo.problems

import kotlin.math.abs

class Day01(override var fileName: String) : Problem {

    override fun firstSolution(): String {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()
        val input = this.javaClass.getResourceAsStream("/$fileName")?.bufferedReader()?.readLines()

        if (input != null)
        {
            for (line in input) {
                val splitted = line.split("   ")
                firstList.add(splitted[0].toInt())
                secondList.add(splitted[1].toInt())
            }
        }

        firstList.sort()
        secondList.sort()

        var solution = 0
        for (i in firstList.indices)
        {
            solution += abs(firstList[i] - secondList[i])
        }

        return solution.toString()
    }

    override fun secondSolution(): String {
        val firstList: MutableList<Int> = mutableListOf()
        val secondList: MutableList<Int> = mutableListOf()
        val input = this.javaClass.getResourceAsStream("/$fileName")?.bufferedReader()?.readLines()

        if (input != null)
        {
            for (line in input) {
                val splitted = line.split("   ")
                firstList.add(splitted[0].toInt())
                secondList.add(splitted[1].toInt())
            }
        }

        val counter = mutableMapOf<Int, Int>()
        for (item in secondList) {
            val value = counter.getOrDefault(item, null)
            if (value == null) {
                counter[item] = 1
            } else {
                counter[item] = value + 1
            }
        }

        var solution = 0
        for (item in firstList) {
            val value = counter.getOrDefault(item, 0)
            solution += item * value
        }

        return solution.toString()
    }
}