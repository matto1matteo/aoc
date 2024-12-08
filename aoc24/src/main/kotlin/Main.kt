package com.github.matto1matteo

import com.github.matto1matteo.problems.*

fun main() {
    val problems: Array<Problem?> = arrayOfNulls(25)
    problems[0] = Day01("day01.txt")
    problems[1] = Day02("day02.txt")
    problems[2] = Day03("day03.txt")
    problems[3] = Day04("day04.txt")
    problems[4] = Day05("day05.txt")
    problems[5] = Day06("day06.txt")

    for ((i, problem) in problems.withIndex())
    {
        if (problem == null) {
            continue
        }
        println("-----------------------------------------------------")
        println("Day ${i + 1}")
        println("Solution first problem ${problem.firstSolution()}")
        println("Solution second problem ${problem.secondSolution()}")
        println("-----------------------------------------------------")
        println()
    }

}

