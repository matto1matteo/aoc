package com.github.matto1matteo.problems

import java.io.BufferedReader

interface Problem {
    var fileName: String;
    fun firstSolution(): String;
    fun secondSolution(): String;
}

fun readResource(fileName: String) : BufferedReader? {
    return {}.javaClass.getResourceAsStream("/$fileName")?.bufferedReader()
}