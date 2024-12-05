package com.github.matto1matteo

import com.github.matto1matteo.problems.*

fun main() {
    println("Hello")

    val problems: Array<Problem?> = Array<Problem?>(25){
        null
    }
    problems[0] = Day01("day01.txt")
    problems[1] = Day02("day02.txt")
    problems[2] = Day03("day03.txt")
    problems[3] = Day04("day04.txt")

    for (i in problems.indices)
    {
        if (problems[i] != null) {
            println("Solution first problem ${problems[i]!!.firstSolution()}")
            println("Solution second problem ${problems[i]!!.secondSolution()}")
        }
    }

}

