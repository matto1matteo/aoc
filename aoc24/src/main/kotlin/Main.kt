package com.github.matto1matteo

fun main() {
    println("Hello")

    val problems: Array<Problem?> = Array<Problem?>(25){
        null
    }
    problems[0] = Day01("day01.txt")
    problems[1] = Day02("day02.txt")

    for (i in problems.indices)
    {
        if (problems[i] != null) {
            println("Solution first problem ${problems[i]!!.firstSolution()}")
            println("Solution second problem ${problems[i]!!.secondSolution()}")
        }
    }

}

